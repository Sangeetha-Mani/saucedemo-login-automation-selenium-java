package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import pages.LoginPage;
import base.BaseTest;

public class LoginTest extends BaseTest {
//write business logic - functionality test valid login
    @Test(groups = {"smoke","regression"})
    public void validLoginTest(){
       System.out.println("Login Execution" + Thread.currentThread().getName());
        LoginPage loginPage = new LoginPage(getDriver());
        getDriver().get("https://www.saucedemo.com");
        loginPage.login("standard_user", "secret_sauce");
        //assertion
        Assert.assertTrue(getDriver().getCurrentUrl().contains("inventory"));
       
    }

}