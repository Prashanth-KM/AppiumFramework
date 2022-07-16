package pages.Objects;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public class LoginPageObjects {

    @AndroidFindBy(accessibility = "phoneNumber")
    public MobileElement phoneNumber;

    @AndroidFindBy(accessibility = "submitButton")
    public MobileElement submitBtn;

    @AndroidFindBy(accessibility = "pincode")
    public MobileElement pinCode;

    @AndroidFindAll({
            @AndroidBy(xpath = "((//*[contains(@text,'off')])[1]//preceding::android.widget.ImageView)[position()=last()]"),
            @AndroidBy(xpath= "((//*[contains(@text,'OFF')])[1]//preceding::android.widget.ImageView)[position()=last()]")
    })
    public MobileElement prodImage;

    @AndroidFindBy(id = "android:id/alertTitle")
    public MobileElement invalidLoginAlertTitle;

    @AndroidFindBy(id = "android:id/button1")
    public MobileElement invalidLoginAlertOkBtn;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Please enter a valid number')]")
    public MobileElement invalidLoginText;

    @AndroidFindBy(xpath = "//android.widget.EditText[@content-desc='pincode']//following::android.widget.TextView[1]")
    public MobileElement invalidOtpError;


}
