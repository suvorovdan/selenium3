import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class CommonSteps {
    WebDriver driver;
    Actions actions;

    WebDriver getDriver() {
        return driver;
    }

    String BASE_URL ;

    public void startUp(){
        /**назначение свойств вебдрайверу, который запускает наш тест с параметрами для браузера(в развёрнутом виде
         * и ожиданием загрузки страниц в 40 секунд) и открытие сайта РГС*/
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    public void endTest(){
        driver.quit();
    }


    void waitForVisible(By locator){
        WebDriverWait wait = new WebDriverWait(driver, 8);
        wait.pollingEvery(Duration.ofMillis(300))
                .until(ExpectedConditions
                        .visibilityOfElementLocated(locator));
    }


    void selectByText(WebElement element, String text){
        new Select(element).selectByVisibleText(text);
    }

    WebElement findByXpath(String xpath) {
        return findByLocator(By.xpath(xpath));
    }

    WebElement findByLocator(By locator) {
        return driver.findElement(locator);
    }

    void click(By locator){
        findByLocator(locator).click();
    }

    void click(String xpath){
        findByXpath(xpath).click();
    }

    void checkElementText(WebElement element,String text){
        Assert.assertEquals("Значения текста не соотвествует  ожидаемому",
                text, element.getText());
    }

    void selectCategory(WebElement element,String category){
        actions= new Actions(driver);
        actions.click(element)
                .sendKeys(category)
                .click()
                .perform();
    }

    void goToElement(WebElement element){
        actions= new Actions(driver);
        actions.moveToElement(element).perform();
    }

    void chooseDate(WebElement element, String date){
        element.sendKeys(Keys.ENTER);
        element.click();
        element.sendKeys(date);

       /* actions= new Actions(driver);
        actions.sendKeys(element,Keys.ENTER)
                .click(element)
                .sendKeys(date)
                .perform();*/
    }

    void fillElement(By locator, String text){
        WebElement element = findByLocator(locator);
        if (element.getTagName().equalsIgnoreCase("select")){
            selectByText(element, text);
        } else {
            element.sendKeys(text);
        }
    }

    void clickToCount(WebElement element){
        element.sendKeys(Keys.ENTER);
    }


}
