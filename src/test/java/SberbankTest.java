import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class SberbankTest {

    public static WebDriver webDriver = null;

    @Parameterized.Parameter(0)
    public String surname;
    @Parameterized.Parameter(1)
    public String name;
    @Parameterized.Parameter(2)
    public String birthdayDate;
    @Parameterized.Parameter(3)
    public String lastName;
    @Parameterized.Parameter(4)
    public String firstName;
    @Parameterized.Parameter(5)
    public String middleName;
    @Parameterized.Parameter(6)
    public String passportBirthdayDate;
    @Parameterized.Parameter(7)
    public String passportSeries;
    @Parameterized.Parameter(8)
    public String passportNumber;
    @Parameterized.Parameter(9)
    public String documentDate;
    @Parameterized.Parameter(10)
    public String documentIssue;

    @Parameterized.Parameters(name = "{index}: Test with lastName={0} and name={1}")
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{
                {"Greblov", "Sergey", "01011995","Греблов","Сергей","Олегович","01011995", "4614", "817781","15042016","Оуфмс России по Воронежской области" },
                {"Vasilev", "Vasiliy", "27012002","Васильев","Василий","Васильевич","27012002", "4615", "717714","15042016","Киоск Союзпечать" }
                ,{"Ivanov", "Ivan", "01021992","Иванов","Иван","Матвеевич","01021991", "4517", "647342","12052012","Продавец из электрички" }};
        return Arrays.asList(data);
    }


    @BeforeClass
    public static void testUp(){
        System.setProperty("webdriver.chrome.driver", "src/main/drivers/chromedriver");
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
        webDriver.findElement(By.xpath("//div[@class='online-card-program']/h3[contains(text(), 'Минимальная')]")).click();
        WebElement buyButton =  webDriver.findElement(By.xpath("//button[text()='Оформить']"));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", buyButton);
        new WebDriverWait(webDriver,5).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Оформить']")));
        buyButton.click();
        WebElement elementSurname = webDriver.findElement(By.xpath("//input[@id='surname_vzr_ins_0']"));
        WebElement elementName = webDriver.findElement(By.xpath("//input[@id='name_vzr_ins_0']"));
        WebElement elementBirthdayDate = webDriver.findElement(By.xpath("//input[@id='birthDate_vzr_ins_0']"));
        WebElement elementLastName = webDriver.findElement(By.xpath("//input[@id='person_lastName']"));
        WebElement elementFirstName = webDriver.findElement(By.xpath("//input[@id='person_firstName']"));
        WebElement elementMiddleName = webDriver.findElement(By.xpath("//input[@id='person_middleName']"));
        WebElement elementPassportBirthdayDate = webDriver.findElement(By.xpath("//input[@id='person_birthDate']"));
        WebElement elementPassportSeries = webDriver.findElement(By.xpath("//input[@id='passportSeries' and @placeholder='Серия']"));
        WebElement elementPassportNumber = webDriver.findElement(By.xpath("//input[@id='passportNumber']"));
        WebElement elementDocumentDate = webDriver.findElement(By.xpath("//input[@id='documentDate']"));
        WebElement elementDocumentIssue = webDriver.findElement(By.xpath("//input[@id='documentIssue']"));

        elementSurname.sendKeys(surname);
        elementName.sendKeys(name);
        elementBirthdayDate.sendKeys(birthdayDate);
        elementLastName.sendKeys(Keys.CONTROL + "a");
        elementLastName.sendKeys(lastName);
        elementFirstName.sendKeys(firstName);
        elementMiddleName.sendKeys(middleName);
        elementPassportBirthdayDate.sendKeys(passportBirthdayDate);
        elementPassportSeries.sendKeys(Keys.CONTROL + "a");
        elementPassportSeries.sendKeys(passportSeries);
        elementPassportNumber.sendKeys(passportNumber);
        elementDocumentDate.sendKeys(documentDate);
        elementDocumentIssue.sendKeys(Keys.CONTROL + "a");
        elementDocumentIssue.sendKeys(documentIssue);


        Assert.assertEquals(surname, elementSurname.getAttribute("value"));
        Assert.assertEquals(name, elementName.getAttribute("value"));
        Assert.assertEquals(formatDateWithDots(birthdayDate), elementBirthdayDate.getAttribute("value"));
        Assert.assertEquals(lastName, elementLastName.getAttribute("value"));
        Assert.assertEquals(firstName, elementFirstName.getAttribute("value"));
        Assert.assertEquals(middleName, elementMiddleName.getAttribute("value"));
        Assert.assertEquals(formatDateWithDots(passportBirthdayDate), elementPassportBirthdayDate.getAttribute("value"));
        Assert.assertEquals(passportSeries, elementPassportSeries.getAttribute("value"));
        Assert.assertEquals(passportNumber, elementPassportNumber.getAttribute("value"));
        Assert.assertEquals(formatDateWithDots(documentDate), elementDocumentDate.getAttribute("value"));
        Assert.assertEquals(documentIssue, elementDocumentIssue.getAttribute("value"));

        webDriver.findElement(By.xpath("//button[contains(text(), 'Продолжить')]")).click();
        Assert.assertTrue(webDriver.findElement(By.xpath("//div[@class='alert-form alert-form-error']")).isDisplayed());


    }
    public static String formatDateWithDots(String date){
        return date.substring(0,2) + "." + date.substring(2,4) + "." + date.substring(4,8);
    }
}
