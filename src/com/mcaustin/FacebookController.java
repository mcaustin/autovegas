package com.mcaustin;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FacebookController {

    private WebDriver driver;
    private static By CLOSE_POP_UP = By.xpath("//*[@id=\"platformDialogForm\"]/div[1]/div/div[1]/button");

    private final String username_string;
    private final String password_string;

    public FacebookController(WebDriver driver, String username, String password) {
        this.driver = driver;
        this.username_string = username;
        this.password_string = password;
    }

    public void logIn() {
        driver.get("https://www.facebook.com/myvegas/");

        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys(username_string);

        WebElement password = driver.findElement(By.id("pass"));
        password.sendKeys(password_string);

        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"u_0_3\"]"));
        loginButton.click();
    }

    public void launchGame() {
        driver.get("https://apps.facebook.com/playmyvegas/");
    }

    public void closePopUp() {
        if (isElementPresent(driver, CLOSE_POP_UP)) {
            driver.findElement(CLOSE_POP_UP).click();
        }
    }

    public void centerGameOnScreen() {
        WebElement element = driver.findElement(By.id("iframe_canvas"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected boolean isElementPresent(WebDriver driver, By by){
        try{
            driver.findElement(by);
            return true;
        }
        catch(NoSuchElementException e){
            return false;
        }
    }
}
