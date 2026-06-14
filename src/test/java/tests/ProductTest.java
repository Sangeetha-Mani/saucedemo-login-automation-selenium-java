package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.By;

import pages.LoginPage;
import pages.ProductPage;


public class ProductTest extends  BaseTest{

   

   @Test(groups = {"smoke"})
   public void verifyPageTitle(){
    System.out.println("Driver :" +   getDriver());
     getDriver().get("https://www.saucedemo.com");

        LoginPage lp = new LoginPage(getDriver());
        lp.login("standard_user", "secret_sauce");

        Assert.assertTrue(
            getDriver().getCurrentUrl().contains("inventory")
        );
     ProductPage pp = new ProductPage(getDriver());
     Assert.assertTrue(pp.pageTitle().equals("Swag Labs"));  
   }
   
    // how to go to inventary page without login
    @Test(groups= {"regression"})
    public void checkProductsCount() {
         getDriver().get("https://www.saucedemo.com");

        LoginPage lp = new LoginPage(getDriver());
        lp.login("standard_user", "secret_sauce");

        Assert.assertTrue(
            getDriver().getCurrentUrl().contains("inventory")
        );
        ProductPage pp = new ProductPage(getDriver());
        int productCount = pp.countProducts();
        Assert.assertTrue(productCount == 6);
    }
}
