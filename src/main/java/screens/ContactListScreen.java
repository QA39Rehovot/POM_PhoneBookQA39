package screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import models.Contact;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class ContactListScreen extends BaseScreen{

    public ContactListScreen(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

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
}
