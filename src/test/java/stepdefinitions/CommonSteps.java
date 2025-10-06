package stepdefinitions;

import io.cucumber.java.en.*;
import helpers.*;
import static org.junit.Assert.*;

public class CommonSteps {

    private CommonHelper helper = new CommonHelper();

    @Given("Veritabanı bağlantısı açılır")
    public void opendatabaseconnection() {
        helper.openDatabaseConnection();
    }
    @And("Test verileri hazırlanır")
    public void preparetestdata() {
        helper.prepareTestData();
    }
    @And("Ekran görüntüsü alınır")
    public void takescreenshot() {
        helper.takeScreenshot();
    }
    @Given("Veritabanı temizlenir")
    public void cleandatabase() {
        helper.cleanDatabase();
    }
    @And("Browser kapatılır")
    public void closebrowser() {
        helper.closeBrowser();
    }
}