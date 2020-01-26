import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class SberbankTest {
    public static WebDriver webDriver = null;

    @BeforeClass
    public static void testUp(){
        System.setProperty("webdriver.chrome.driver", "src/main/drivers/chromedriver");
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        webDriver.manage().window().maximize();
    }

    @AfterClass
    public static void cleanUp(){
        webDriver.close();
        webDriver.quit();
    }

    @Test
    public void sberbankCheck() throws InterruptedException {

        webDriver.get("https://www.sberbank.ru/ru/person");

        webDriver.findElement(By.xpath("//button[@aria-label='Меню Страхование']/span")).click();
        WebElement webElement = webDriver.findElement(By.xpath("//li/a[contains(text(), 'путешественников') and contains(@class,'lg-menu')]"));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", webElement);
        webElement.click();

        webElement = webDriver.findElement(By.xpath("//title"));
        String title = webElement.getAttribute("text");
        Assert.assertTrue(title.contains("Страхование путешественников"));
        WebElement button = webDriver.findElement(By.xpath("//a[contains(@class,'analytics-button')]"));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", button);
        button.click();
        webDriver.findElement(By.xpath("//div[@class='online-card-program selected']")).click();
        webDriver.findElement(By.xpath("//button[text()='Оформить']")).click();


        WebElement surname = webDriver.findElement(By.xpath("//input[@id='surname_vzr_ins_0']"));
        WebElement name = webDriver.findElement(By.xpath("//input[@id='name_vzr_ins_0']"));
        WebElement bDate = webDriver.findElement(By.xpath("//input[@id='birthDate_vzr_ins_0']"));
        WebElement lName = webDriver.findElement(By.xpath("//input[@id='person_lastName']"));
        WebElement fName = webDriver.findElement(By.xpath("//input[@id='person_firstName']"));
        WebElement mName = webDriver.findElement(By.xpath("//input[@id='person_middleName']"));
        WebElement pbDate = webDriver.findElement(By.xpath("//input[@id='person_birthDate']"));
        WebElement passportSeries = webDriver.findElement(By.xpath("//input[@id='passportSeries' and @placeholder='Серия']"));
        WebElement passportNumber = webDriver.findElement(By.xpath("//input[@id='passportNumber']"));
        WebElement documentDate = webDriver.findElement(By.xpath("//input[@id='documentDate']"));
        WebElement documentIssue = webDriver.findElement(By.xpath("//input[@id='documentIssue']"));

        surname.sendKeys("Testov");
        name.sendKeys("Test");
        bDate.sendKeys("03031990");
        lName.sendKeys(Keys.CONTROL + "a");
        lName.sendKeys("Тестов");
        fName.sendKeys("Тест");
        mName.sendKeys("Тестович");
        pbDate.sendKeys("03031990");
        passportSeries.sendKeys(Keys.CONTROL + "a");
        passportSeries.sendKeys("4516");
        passportNumber.sendKeys("815562");
        documentDate.sendKeys("04042016");
        documentIssue.sendKeys(Keys.CONTROL + "a");
        documentIssue.sendKeys("оуфмс россии по московской области");
        Thread.sleep(4000);

        Assert.assertEquals("Testov", surname.getAttribute("value"));
        Assert.assertEquals("Test", name.getAttribute("value"));
        Assert.assertEquals("03.03.1990", bDate.getAttribute("value"));
        Assert.assertEquals("Тестов", lName.getAttribute("value"));
        Assert.assertEquals("Сергей", fName.getAttribute("value"));
        Assert.assertEquals("Тестович", mName.getAttribute("value"));
        Assert.assertEquals("03.03.1990", pbDate.getAttribute("value"));
        Assert.assertEquals("4516", passportSeries.getAttribute("value"));
        Assert.assertEquals("815562", passportNumber.getAttribute("value"));
        Assert.assertEquals("04.04.2016", documentDate.getAttribute("value"));
        Assert.assertEquals("оуфмс россии по московской области", documentIssue.getAttribute("value"));

        webDriver.findElement(By.xpath("//button[contains(text(), 'Продолжить')]")).click();
        Assert.assertTrue(webDriver.findElement(By.xpath("//div[@class='alert-form alert-form-error']")).isDisplayed());


    }
}
