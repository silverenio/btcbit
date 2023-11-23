package ui_tests;

import base.UIBaseTest;
import factory.PageinstancesFactory;
import helpers.DataGenerator;
import models.dto.UserDto;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pages.MainPage;
import settings.SettingsProvider;

@Test(testName = "Successful Validation Test", description = "Successful Validation Test")
public class ValidationTest extends UIBaseTest {

    private MainPage mainPage;
    private final UserDto userDto = new UserDto(SettingsProvider.getUsername(), SettingsProvider.getPassword());

    @Test
    public void wrongEmailLoginTest() {
        mainPage = PageinstancesFactory.getInstance(MainPage.class);
        mainPage.clickSignInButton()
                .inputEmail(DataGenerator.getRandomMail())
                .inputPassword(userDto.getPassword())
                .clickSignInFormButton();

        assertValidation(mainPage.getValidation());
    }

    @Test
    public void wrongPasswordLoginTest() {
        mainPage = PageinstancesFactory.getInstance(MainPage.class);
        mainPage.clickSignInButton()
                .inputEmail(userDto.getUsername())
                .inputPassword(DataGenerator.getStrongRandomPassword())
                .clickSignInFormButton();

        assertValidation(mainPage.getValidation());
    }

    private void assertValidation(String actualValidation) {
        String requiredValidation = "Invalid email or password. Try clicking 'Forgot Password' if you're having trouble signing in.";
        Assert.assertEquals(actualValidation, requiredValidation, "Incorrect Validation");
    }

    @AfterMethod
    private void goToMainPage() {
        mainPage.clickHeaderLogo();
    }
}
