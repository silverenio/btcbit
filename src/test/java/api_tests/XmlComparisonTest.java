package api_tests;

import base.BackEndBaseTest;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static settings.SettingsProvider.getWebHost;

public class XmlComparisonTest extends BackEndBaseTest {

    @Test(testName = "Compare XML rates Test", description = "Compare XML rates Test")
    public void xmlComparisonTest() throws Exception {
        RestAssured.baseURI = getWebHost();

        String xmlString1 = getRates("get_xml_kurs_btcbit");
        String xmlString2 = getRates("get_xml_kurs_btcbit_base");

        assertEqualValues(compareXmlStrings(xmlString1, xmlString2));
    }

    @Test(testName = "Compare XML rates with errors Test", description = "Compare XML rates with errors Test")
    public void xmlComparisonWithErrorsTest() throws Exception {
        String xmlString1 = getErrorExample();
        String xmlString2 = getErrorExample();

        assertEqualValues(compareXmlStrings(xmlString1, xmlString2));
    }

    @Step
    private Map<String, String> compareXmlStrings(String xmlString1, String xmlString2) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc1 = builder.parse(new ByteArrayInputStream(xmlString1.getBytes(StandardCharsets.UTF_8)));
        Document doc2 = builder.parse(new ByteArrayInputStream(xmlString2.getBytes(StandardCharsets.UTF_8)));

        Map<String, String> rates1 = extractRates(doc1);
        Map<String, String> rates2 = extractRates(doc2);
        Map<String, String> equalRates = new HashMap<>();

        log.debug("Compare rates:");
        for (Map.Entry<String, String> entry1 : rates1.entrySet()) {
            String key = entry1.getKey();
            String value1 = entry1.getValue();
            String value2 = rates2.get(key);

            if (value1.equals(value2)) {
                log.debug(String.format("→ Error: %s (Both <out> are: '%s')", key, value1));
                equalRates.put(key, value1 + " : " + value2);
            }
        }

        return equalRates;
    }

    @Step
    private Map<String, String> extractRates(Document doc) {
        Map<String, String> rates = new HashMap<>();
        NodeList nodeList = doc.getElementsByTagName("item");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                String from = element.getElementsByTagName("from").item(0).getTextContent();
                String to = element.getElementsByTagName("to").item(0).getTextContent();
                String out = element.getElementsByTagName("out").item(0).getTextContent();

                rates.put(from + to, out);
            }
        }

        return rates;
    }

    @Step
    private String getRates(String uri) {
        return given().get(uri)
                .then().assertThat().statusCode(200)
                .extract().response().asString();
    }

    @Step
    private void assertEqualValues(Map<String, String> equalValues) {
        if (equalValues.isEmpty()) {
            log.debug("Valid: All rates different.");
        } else {
            log.debug(String.format("ERROR: There %s '%s' equal rates!", equalValues.size() > 1 ? "are" : "is",
                    equalValues.size()));
        }

        for (Map.Entry<String, String> entry1 : equalValues.entrySet()) {
            String key = entry1.getKey();
            String value = entry1.getValue();

            log.debug(key + ". " + value);
        }

        Assert.assertTrue(equalValues.isEmpty());
    }

    @Step
    private String getErrorExample() {
        return "<items>\n" +
                "<item>\n" +
                "<from>ADVCUSD</from>\n" +
                "<to>BTC</to>\n" +
                "<in>1</in>\n" +
                "<out>0.00003281383074281</out>\n" +
                "<tofee>0.79 USD</tofee>\n" +
                "<amount>18.626830086056</amount>\n" +
                "<minamount>10 USD</minamount>\n" +
                "<maxamount>1000 USD</maxamount>\n" +
                "</item>\n" +
                "<item>\n" +
                "<from>ETHUSD</from>\n" +
                "<to>BTC</to>\n" +
                "<in>1</in>\n" +
                "<out>0.001</out>\n" +
                "<tofee>0.79 USD</tofee>\n" +
                "<amount>18.626830086056</amount>\n" +
                "<minamount>10 USD</minamount>\n" +
                "<maxamount>1000 USD</maxamount>\n" +
                "</item>\n" +
                "</items>\n";
    }
}
