package tests;

import config.AppiumConfig;
import models.Contact;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import screens.ContactListScreen;
import screens.SplashScreen;

import java.util.Random;

public class AddNewContactTests extends AppiumConfig {

    int i;

    @BeforeSuite
    public void precondition(){
        i = new Random().nextInt(1000) + 1000;
        new SplashScreen(driver)
                .gotoAuthenticationScreen()
                .fillEmail("abc@def.com")
                .fillPassword("$Abcdef12345")
                .submitLogin();
    }

    @Test
    public void addNewContactPositive(){
        Contact contact = Contact.builder()
                .name("AddNewContact_" + i)
                .lastName("Positive")
                .email("addNewContact_" + i + "@mail.com")
                .phone("1234567" + i)
                .address("Rehovot")
                .description("NewContact_" + i)
                .build();
        new ContactListScreen(driver)
                .openContactForm()
                .fillContactForm(contact)
                .submitContactForm()
                .isContactAdded(contact);
    }
    @Test
    public void addNewContactNegativeEmptyName(){
        Contact contact = Contact.builder()
//                .name("AddNewContact_" + i)
                .lastName("Negative")
                .email("addNewContact_" + i + "@mail.com")
                .phone("1234567" + i)
                .address("Rehovot")
                .description("NewContact_" + i)
                .build();
        new ContactListScreen(driver)
                .openContactForm()
                .fillContactForm(contact)
                .submitContactFormNegative()
                .isErrorMessageContainsTextInAlert("blank");
    }
    @Test
    public void addNewContactNegativeEmptyPhone(){
        Contact contact = Contact.builder()
                .name("AddNewContact_" + i)
                .lastName("Negative")
                .email("addNewContact_" + i + "@mail.com")
//                .phone("1234567" + i)
                .address("Rehovot")
                .description("NewContact_" + i)
                .build();
        new ContactListScreen(driver)
                .openContactForm()
                .fillContactForm(contact)
                .submitContactFormNegative()
                .isErrorMessageContainsTextInAlert("digits");
    }


}
