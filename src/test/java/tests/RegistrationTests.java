package tests;

import config.AppiumConfig;
import models.Auth;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.AuthenticationScreen;
import screens.ContactListScreen;
import screens.SplashScreen;

import java.util.Random;

public class RegistrationTests extends AppiumConfig {

    int i;

    @BeforeMethod
    public void precondition() {
        i = new Random().nextInt(1000) + 1000;
    }


    @Test
    public void registrationPositive() {
                new SplashScreen(driver)
                        .gotoAuthenticationScreen()
                        .fillEmail("abc_" + i + "@def.com")
                        .fillPassword("$Abcdef12345")
                        .submitRegistration()
                        .assertContactListActivityPresent();
    }

    @Test
    public void registrationPositiveModel() {
                new SplashScreen(driver)
                        .gotoAuthenticationScreen()
                        .registration(
                                Auth.builder()
                                        .email("abc_" + i + "@def.com")
                                        .password("$Abcdef12345")
                                        .build()
                        )
                        .isContactListActivityPresent();
    }



    @AfterMethod
    public void postCondition(){
        new ContactListScreen(driver).logout();
        new SplashScreen(driver);
    }
}
