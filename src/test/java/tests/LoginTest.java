package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import pages.LoginPage;

public class LoginTest {
//write business logic - functionality test valid login
    @Test
    public void validLoginTest(){
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.saucedemo.com");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        //assertion
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));

        driver.quit();
    }
}