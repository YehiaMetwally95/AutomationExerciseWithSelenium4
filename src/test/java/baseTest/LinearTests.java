package baseTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.*;

import java.io.*;

import static io.restassured.RestAssured.form;
import static io.restassured.RestAssured.given;
import static utils.AlertsActions.typeTextInAlert;

@Listeners(utils.TestNGListners.class)
public class LinearTests {
    private static final Logger log = LoggerFactory.getLogger(LinearTests.class);
     String query = "SELECT * FROM yehiadb1.usercredentials ORDER BY Username ASC";
     String filepath = "src/test/resources/TestDataJsonFiles/LoginCredentials.json";
     String[] jsonKeys = {"Username","Password","Massage"};
     String[] jsonMainKeys = {"ValidCredentials","InvalidUserCredentials",
             "InvalidPasswordCredentials"};

    JsonManager json = new JsonManager(filepath);

    @Test
    public void test10() throws IOException {

    }

    @Test
    public void test11() throws IOException {

       String yehia = "$10.11";
        System.out.println(yehia.split("\\$",2)[1]);

        double num1 = 11.11;
        double num2 = num1*1.08;
        double num3 = Math.round(num2);
        System.out.println(num2);
        System.out.println(num3);

    }


}
