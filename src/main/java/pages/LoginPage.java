package pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

//POM page object
public class LoginPage {
   
    // declare webdriver
    WebDriver driver;

    By userNameField = By.id("user-name");
    By passwordField = By.id("password");
    By loginButton = By.id("login-button");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    //find elements and fill
    public void enterUserName(String username){
        driver.findElement(userNameField).sendKeys(username);
    }
    public void enterPassword(String password){
        driver.findElement(passwordField).sendKeys(password);
    }
    // action
    public void clickLogin(){
        driver.findElement(loginButton).click();
    }

    public void login(String user, String pass){
        enterUserName(user);
        enterPassword(pass);
        clickLogin();
    }
}