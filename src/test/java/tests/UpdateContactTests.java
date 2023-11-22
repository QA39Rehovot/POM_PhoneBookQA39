package tests;

import config.AppiumConfig;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import screens.ContactListScreen;
import screens.SplashScreen;

import java.util.Random;

public class UpdateContactTests extends AppiumConfig {


    @BeforeClass
    public void precondition(){
        new SplashScreen(driver)
                .gotoAuthenticationScreen()
                .fillEmail("abc@def.com")
                .fillPassword("$Abcdef12345")
                .submitLogin();
    }

    @Test
    public void updateOneContact(){
        int i = new Random().nextInt(1000) + 1000;
        new ContactListScreen(driver)
                .updateOneContact()
                .updateName("Updated name_" + i)
                .submitEditContactForm()
                .isContactContains("Updated name_" + i);
    }

}
