package screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import models.Contact;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class ContactListScreen extends BaseScreen{

    public ContactListScreen(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    String phoneNumber;

    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/action_bar']" +
            "/android.widget.TextView")
    MobileElement activityTextView;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/title']")
    MobileElement logoutButton;
    @FindBy(xpath = "//*[@content-desc='More options']")
    MobileElement moreOption;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/add_contact_btn']")
    MobileElement plusButton;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/rowName']")
    List<MobileElement> nameList;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/rowPhone']")
    List<MobileElement> phoneList;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/rowContainer']")
    List<MobileElement> contacts;
    @FindBy(xpath = "//*[@resource-id='android:id/button1']")
    MobileElement yesButton;
    @FindBy(xpath = "//*[@resource-id='android:id/button2']")
    MobileElement cancelButton;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/rowPhone']")
    MobileElement contactPhone;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/emptyTxt']")
    MobileElement emptyTextView;


    public boolean isContactListActivityPresent(){
        return shouldHave(activityTextView, "Contact list", 5);
    }

    public AuthenticationScreen logout(){
        if(isDisplayedWithException(moreOption)) {
            moreOption.click();
            logoutButton.click();
        }
        return new AuthenticationScreen(driver);
    }

    public ContactListScreen assertContactListActivityPresent(){
        Assert.assertTrue(isContactListActivityPresent());
        return this;
    }

    public AddNewContactScreen openContactForm(){
//        waitElement(plusButton, 5);
        if(isDisplayedWithException(plusButton)) {
            plusButton.click();
        }
        return new AddNewContactScreen(driver);
    }

    public ContactListScreen isContactAdded(Contact contact){
        boolean checkName = isContainsText(nameList, contact.getName()
                        + " " + contact.getLastName());
        boolean checkPhone = isContainsText(phoneList, contact.getPhone());
        Assert.assertTrue(checkName && checkPhone);
        return this;
    }

    public boolean isContainsText(List<MobileElement> list, String text){
        for(MobileElement element : list){
            if(element.getText().contains(text)){
                return true;
            }
        }
        return false;
    }

    public ContactListScreen removeOneContact(){
        waitElement(plusButton, 5);
        MobileElement contact = contacts.get(0);
        phoneNumber = contactPhone.getText();
        System.out.println(phoneNumber);
        Rectangle rect = contact.getRect();

        int xStart = rect.getX() + rect.getWidth()/8;
        int xEnd = xStart + rect.getWidth()*6/8;
        int y = rect.getY() + rect.getHeight() / 2;

        TouchAction<?> touchAction = new TouchAction<>(driver);
        touchAction
                .longPress(PointOption.point(xStart, y))
                .moveTo(PointOption.point(xEnd, y))
                .release()
                .perform();

        waitElement(yesButton, 5);
        yesButton.click();
        return this;
    }

    public ContactListScreen isOneContactRemoved(){
        Assert.assertFalse(phoneList.contains(phoneNumber));
        return this;
    }

    public ContactListScreen removeAllContacts(){
        waitElement(plusButton, 5);
        while (contacts.size() > 0){
            removeOneContact();
        }
        return this;
    }

    public ContactListScreen isNoContacts(){
        Assert.assertTrue(shouldHave(emptyTextView, "No contacts", 5));
        return this;
    }

    public ContactListScreen provideContacts(){
        if(contacts.size() < 3){
            for(int i = 0; i < 3; i++){
                addContact();
            }
        }
        return this;
    }

    public void addContact(){
        int i = new Random().nextInt(1000) + 1000;
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
                .submitContactForm();
    }
}
