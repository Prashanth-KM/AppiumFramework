package pages.Actions;

import Appium.reports.ExtentLogger;
import Appium.generic.CommonFunctions;
import Appium.utils.Constants;
import Appium.utils.ContextManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import pages.Objects.DashBoardObjects;
import java.util.Set;
import java.util.TreeSet;


public class DashBoardActions implements Constants {

    CommonFunctions commonFunction;
    AppiumDriver driver;
    DashBoardObjects dashBoardObjects = new DashBoardObjects();


    public DashBoardActions() {
        this.driver = ContextManager.getDriver();
        commonFunction = new CommonFunctions(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        PageFactory.initElements(new AppiumFieldDecorator(driver), dashBoardObjects);

    }

    public void validateTeamIndiaPlayers() throws InterruptedException {
        commonFunction.waitAndClick(dashBoardObjects.moreTab, "More Tab");
        commonFunction.waitAndClick(dashBoardObjects.browseTeam, "Browse team");
        commonFunction.waitAndClick(dashBoardObjects.india, "India");
        commonFunction.waitAndClick(dashBoardObjects.news, "News");
        commonFunction.waitInSec(2);
        commonFunction.swipeRight();
        commonFunction.swipeLeft();
        commonFunction.swipeDown();
        commonFunction.swipeUp();
        commonFunction.swipeRight();

        Set<String> text_Set = new TreeSet<>();
        commonFunction.waitInSec(2);
        for(int i=0;i<6;i++){
            for (WebElement name : dashBoardObjects.playersName) {
                text_Set.add(commonFunction.element(name).getText());
            }
            commonFunction.swipeDown();
        }
        ExtentLogger.info(text_Set);

    }

}
