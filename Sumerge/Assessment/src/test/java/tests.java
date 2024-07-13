import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class tests {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void ElementsInPage() {
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        Assert.assertTrue(usernameField.isDisplayed());
        Assert.assertTrue(passwordField.isDisplayed());
        Assert.assertTrue(loginButton.isDisplayed());
    }

    @Test
    public void ValidLogin() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("inventory.html"));

        WebElement swagLabsHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("app_logo")));
        Assert.assertTrue(swagLabsHeader.isDisplayed());
    }

    @Test
    public void InvalidLogin() {
        driver.findElement(By.id("user-name")).sendKeys("invalid_user");
        driver.findElement(By.id("password")).sendKeys("invalid_password");
        driver.findElement(By.id("login-button")).click();

        WebElement errorMessage = driver.findElement(By.cssSelector(".error-message-container.error"));
        Assert.assertTrue(errorMessage.isDisplayed());
        Assert.assertEquals(errorMessage.getText(), "Epic sadface: Username and password do not match any user in this service");
    }

    @Test
    public void EmptyUsername() {
        driver.findElement(By.id("user-name")).sendKeys("");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        WebElement errorMessage = driver.findElement(By.cssSelector(".error-message-container.error"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message is not displayed");
        Assert.assertEquals(errorMessage.getText(), "Epic sadface: Username is required");
    }

    @Test
    public void EmptyPassword() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("");
        driver.findElement(By.id("login-button")).click();

        WebElement errorMessage = driver.findElement(By.cssSelector(".error-message-container.error"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message is not displayed");
        Assert.assertEquals(errorMessage.getText(), "Epic sadface: Password is required");
    }
}
