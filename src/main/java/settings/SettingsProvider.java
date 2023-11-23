package settings;

import org.apache.log4j.Logger;
import structures.StructureSettings;

import java.io.*;
import java.nio.file.Paths;
import java.util.Properties;

public class SettingsProvider {

    private static Logger log = Logger.getLogger(SettingsProvider.class.getSimpleName());

    private static Properties properties = new Properties();

    public static String getPropertyValue(String key) {
        return getProperty(key);
    }

    public static String getWebHost() {
        String property = getProperty(StructureSettings.WEB_HOST.getStructure().getWebHost());
        if (!property.endsWith("/")) {
            property = property + "/";
        }
        log.debug(String.format("Get Web Host: %s", property));
        return property;
    }

    public static String getUsername() {
        String property = getProperty(StructureSettings.WEB_HOST.getStructure().getUser());
        log.debug(String.format("Get Username: %s", property));
        return property;
    }

    public static String getPassword() {
        String property = getProperty(StructureSettings.WEB_HOST.getStructure().getPassword());
        log.debug(String.format("Get Password: %s", property));
        return property;
    }

    private static String getProperty(String key) {
        if (properties.isEmpty()) {
            File configFile = new File(Paths.get("target", "classes/properties-from-pom.properties").toUri());
            try {
                InputStream input = new FileInputStream(configFile);
                properties.load(input);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties.getProperty(key);
    }
}
