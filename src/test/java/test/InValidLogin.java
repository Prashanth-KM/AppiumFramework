package test;

import Appium.configurations.BaseTest;
import org.testng.annotations.Test;
import pages.Actions.LoginPageActions;

public class InValidLogin extends BaseTest {

    LoginPageActions getLoginPage() { return new LoginPageActions();}

    @Test
    public void validateInValidLogin(){
        getLoginPage().validateInValidLogin();
    }
}
