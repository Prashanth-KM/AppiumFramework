package pages.Objects;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.AndroidFindBy;

import java.util.List;


public class DashBoardObjects {

    @AndroidFindBy(accessibility = "More")
    public MobileElement moreTab;

    @AndroidFindBy(xpath = "//*[@text='Browse Team']")
    public MobileElement browseTeam;

    @AndroidFindBy(id = "com.cricbuzz.android:id/txt_name")
    public MobileElement india;

    @AndroidFindBy(accessibility = "News")
    public MobileElement news;

    @AndroidFindBy(id = "com.cricbuzz.android:id/txt_name")
    public List<MobileElement> playersName;

    @AndroidFindAll({
            @AndroidBy(xpath = "((//*[contains(@text,'off')])[1]//preceding::android.widget.ImageView)[position()=last()]"),
            @AndroidBy(xpath= "((//*[contains(@text,'OFF')])[1]//preceding::android.widget.ImageView)[position()=last()]")
    })
    public MobileElement prodImage;




}
