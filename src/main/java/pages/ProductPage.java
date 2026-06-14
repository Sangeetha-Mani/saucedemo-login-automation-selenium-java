package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

public class ProductPage{

    WebDriver driver;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    By inventoryContainer = By.id("inventory_container");
    By inventoryItem = By.className("inventory_item");
    By headerContainer = By.id("header_container");
    By pageTitleContainer = By.className("header_label");
    By pageTitle = By.className("app_logo");

    public String pageTitle(){
       return driver.findElement(headerContainer)
        .findElement(pageTitleContainer)
        .findElement(pageTitle).getText();
    }

    public int countProducts() {
       return  driver.findElement(inventoryContainer)
                .findElements(inventoryItem).size();
    }
}