import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

public class RgsSteps extends CommonSteps {
    private static final String BASE_RGS_URL = "https://www.rgs.ru";

    public RgsSteps(){
        BASE_URL = BASE_RGS_URL;
    }

    public static final By openInsurenceButtonLocator = By.xpath("//*[@id='main-navbar-collapse']//a[contains(text(), 'Страхование')]");
    public static final By navBarLocator = By.className("container-rgs-main-menu-links");
    public static final String categoryFormat = "//*[@id='main-navbar-collapse']//a[contains(text(), '%s')]";


    String newPattern = "//strong[@data-bind = \"%s\"]";
    String countryP = "text: Name";
    String dateP = " text: BirthDay.repr('moscowRussianDate')";
    String nameP = "text: LastName() + ' ' + FirstName()";
    String expectedJourneyText = "Многократные поездки в течение года";


    Map<String,By> assertData = new HashMap<String, By>(){
        {
            put("Др",By
                    .xpath("//label[contains(text(),'Дата рождения')]//following::input[1]"));
            put("Имя и фамилия",By
                    .xpath("//span[contains(text(),'Фамилия')]//following::input[1]"));
            put("Активный отдых",By
                    .xpath("//span[contains(text(),'Планируется')]//preceding::div[@data-toggle ='toggle']"));
            put("Рассчитать",By
                    .xpath("//button[contains(text(),'Рассчитать')]"));
            put("Не более 90 дней",By
                    .xpath("//div/label[text()[contains(.,'Не более 90 дней')]]"));
            put("дата отбытия",By
                    .xpath("//text()[contains(.,'Дата первой поездки')]//following::input[1]"));
            put("Испания",By
                    .xpath("//select[@id = 'ArrivalCountryList']"));
            put("Шенген",By
                    .xpath("//input[@class ='form-control-multiple-autocomplete-actual-input tt-input']"));
            put("согласие",By
                    .xpath("//label[contains(text(),'Я согласен на')]/preceding-sibling::input"));
            put("Многократные поездки в течение года",By
                    .xpath("//span[@class = 'btn-content-title']"));
            put("Купить полис онлайн",By
                    .xpath("//a[contains(text(),'Купить полис онлайн')]"));
            put("Страхование выезжающих за рубеж",By
                    .xpath("//span[@class='h1' and contains(text(),'Страхование выезжающих')]"));


        }
    };
    Map<String,By> checkData = new HashMap<String, By>(){
        {
            put("top",By
                    .xpath("//h2[contains(text(),'Расчет')]"));
            put(expectedJourneyText,By
                    .xpath("//span[@class ='text-bold']"));
            put("nameOfUser",By
                    .xpath(String.format(newPattern,nameP)));
            put("dateOfB",By
                    .xpath(String.format(newPattern,dateP)));
            put("stateOfLeisure",By
                    .xpath("//span[@class = 'summary-key']//following::small[1]"));
        }
    };



    public void openInsurenceNavBar(){
       click(openInsurenceButtonLocator);
       waitForVisible(navBarLocator);
    }

    public void chooseCategory(String categoryName){
        click(String.format(categoryFormat,categoryName));
    }

    public void checkAbroadHeader(String expectedText,String locatorKey){
        WebElement header = findByLocator(assertData.get(locatorKey));
        waitForVisible(assertData.get(locatorKey));
        checkElementText(header,expectedText);
    }

    public void userClickToBuyOnlineButton(String locatorKey){
        click(assertData.get(locatorKey));
    }

    public void selectArrivalCountry(String locatorKey){
        selectCategory(findByLocator(assertData.get(locatorKey)),locatorKey);
    }


    public void fillRequestForm(String amountOfJourny, String chosenCountries,String chosenContry,
                                String dateOfDeparture,  String leisureActivity, UserData user){
        waitForVisible(assertData.get(amountOfJourny));//aOJ
        click(assertData.get("согласие"));
        click(assertData.get(amountOfJourny));
        waitForVisible(assertData.get(chosenCountries));//cCs
        click(assertData.get(chosenCountries));//cCs
        fillElement(assertData.get(chosenCountries),chosenCountries);
        click(By.xpath("//div[@class = 'tt-menu tt-open']"));
        selectArrivalCountry(chosenContry);//cC
        chooseDate(findByLocator(assertData.get("дата отбытия")),dateOfDeparture);//dOD
        click(assertData.get("Не более 90 дней"));
        fillElement(assertData.get("Имя и фамилия"),user.getName());
        goToElement(findByLocator(assertData.get("Рассчитать")));
        click(assertData.get("Др"));
        fillElement(assertData.get("Др"),user.getBirthday());
        goToElement(findByLocator(assertData.get("Рассчитать")));
        click(assertData.get(leisureActivity));//lA
    }

    void clickToCountButton(){
        goToElement(findByLocator(assertData.get("Рассчитать")));
        clickToCount(findByLocator(assertData.get("Рассчитать")));
    }

    void checkForm(String amountOfJourny, String chosenCountries,
                    String leisureActivity, UserData user){
        goToElement(findByLocator(checkData.get("top")));
        waitForVisible(By.xpath("//span[@class ='text-bold']"));
        checkElementText(findByLocator(By
                .xpath("//span[@class ='text-bold']")),amountOfJourny);
        goToElement(findByLocator(checkData.get("top")));
        waitForVisible(By
                .xpath(String.format(newPattern,countryP)));
        checkElementText(findByLocator(By
                .xpath(String.format(newPattern,countryP))),chosenCountries);
        goToElement(findByLocator(checkData.get("top")));
        waitForVisible(checkData
                .get("nameOfUser"));
        checkElementText(findByLocator(checkData
                .get("nameOfUser")),user.getName().toUpperCase());
        goToElement(findByLocator(checkData.get("top")));
        waitForVisible(checkData
                .get("dateOfB"));
        checkElementText(findByLocator(checkData
                .get("dateOfB")),user.getBirthday());
        goToElement(findByLocator(checkData.get("top")));
        waitForVisible(checkData
                .get("stateOfLeisure"));
        checkElementText(findByLocator(checkData
                .get("stateOfLeisure")),leisureActivity);

    }


}
