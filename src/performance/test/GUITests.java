package performance.test;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.stream.Collectors;

public class GUITests {

    private By txtEmail=By.xpath("//input[@name='email']");
    private By txtPassword=By.xpath("//input[@name='password']");
    private By btnLogin=By.xpath("//div[text()='Login']");
    private By iconSettings=By.xpath("//i[@class='settings icon']");
    private By logout=By.xpath("//span[text()='Log Out']");

    @Test
    public void checkLogin(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\prabhu.palanisamy\\seleniumdrivers\\chromedriver85.exe");
        WebDriver driver=new ChromeDriver();
        driver.get("https://ui.freecrm.com/");
        driver.manage().window().maximize();
        driver.findElement(txtEmail).sendKeys("prabhu.palanisamy83@gmail.com");
        driver.findElement(txtPassword).sendKeys("Test@123");
        driver.findElement(btnLogin).click();
        WebDriverWait wait = new WebDriverWait(driver,120);
        wait.until(ExpectedConditions.elementToBeClickable(iconSettings));
        driver.findElement(iconSettings).click();
        driver.findElement(logout).click();
        driver.quit();

    }

    @Test
    public void navigateToGmail(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\prabhu.palanisamy\\seleniumdrivers\\chromedriver85.exe");
        WebDriver driver=new ChromeDriver();
        driver.get("https://www.gmail.com");
        driver.quit();
    }

}
