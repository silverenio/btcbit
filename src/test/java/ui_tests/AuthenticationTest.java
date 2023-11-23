package ui_tests;

import base.UIBaseTest;
import factory.PageinstancesFactory;
import models.dto.UserDto;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.MainPage;
import settings.SettingsProvider;

@Test(testName = "Successful Authentication test", description = "Successful Authentication test")
public class AuthenticationTest extends UIBaseTest {

    private final UserDto userDto = new UserDto(SettingsProvider.getUsername(), SettingsProvider.getPassword());

    @Test
    public void successfulLoginTest() {
        MainPage mainPage = PageinstancesFactory.getInstance(MainPage.class);
        mainPage
                .clickSignInButton()
                .inputEmail(userDto.getUsername())
                .inputPassword(userDto.getPassword())
                .clickSignInFormButton();

        DashboardPage dashboardPage = PageinstancesFactory.getInstance(DashboardPage.class);

        Assert.assertTrue(dashboardPage.isUserLoggedIn(), "Login failed");
    }
}
