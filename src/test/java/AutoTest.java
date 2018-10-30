import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

public class AutoTest {
    RgsSteps user = new RgsSteps();
    UserData nameAndDate = new UserData("IVANOV VANO","12.12.1984");
    String departure = "01.11.2018";

//    @Parameterized.Parameters
//    public static Collection<Object[]> data(){
//        return Arrays.asList(new Object[][]{ {new UserData("IVANOV VANO","12.12.1984"),"10.12.2018"},
//                {new UserData("KRATOSOV KRATOS","10.11.1965"),"01.11.2018"},
//                {new UserData("kra bik","01.01.1981"),"7.12.2018"}});
//    }
//
//    @Parameterized.Parameter(0)
//    public UserData nameAndDate;
//
//    @Parameterized.Parameter(1)
//    public String departure;


    @Before
    public void before(){user.startUp();}

    @After
    public void after(){
        user.endTest();
    }

    @Test
    public void checkAbroadInsurence(){
        user.openInsurenceNavBar();
        user.chooseCategory("за рубеж");
        user.userClickToBuyOnlineButton("Купить полис онлайн");
        user.checkAbroadHeader("Страхование выезжающих за рубеж","Страхование выезжающих за рубеж");
        user.fillRequestForm("Многократные поездки в течение года","Шенген",
                "Испания",departure,"Активный отдых", nameAndDate);
        user.clickToCountButton();
        user.checkForm("Многократные поездки в течение года",
                "Шенген","(включая активный отдых)", nameAndDate);
    }

}
