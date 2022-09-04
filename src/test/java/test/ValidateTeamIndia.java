package test;

import Appium.configurations.BaseTest;
import org.testng.annotations.Test;
import pages.Actions.DashBoardActions;

public class ValidateTeamIndia extends BaseTest {

    DashBoardActions getDashBoard() { return new DashBoardActions();}


    @Test
    public void validateIndiaPlayers(){
        getDashBoard().validateTeamIndiaPlayers();

    }
}
