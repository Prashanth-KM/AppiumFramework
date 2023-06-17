package pages.Objects;

import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.util.List;


public class DashBoardObjects {

    @AndroidFindBy(accessibility = "More")
    public WebElement moreTab;

    @AndroidFindBy(xpath = "//*[@text='Browse Team']")
    public WebElement browseTeam;

    @AndroidFindBy(id = "com.cricbuzz.android:id/txt_name")
    public WebElement india;

    @AndroidFindBy(accessibility = "News")
    public WebElement news;

    @AndroidFindBy(id = "com.cricbuzz.android:id/txt_name")
    public List<WebElement> playersName;

    @AndroidFindAll({
            @AndroidBy(xpath = "((//*[contains(@text,'off')])[1]//preceding::android.widget.ImageView)[position()=last()]"),
            @AndroidBy(xpath= "((//*[contains(@text,'OFF')])[1]//preceding::android.widget.ImageView)[position()=last()]")
    })
    public WebElement prodImage;




}
