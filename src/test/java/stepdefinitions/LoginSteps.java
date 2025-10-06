package stepdefinitions;

import io.cucumber.java.en.*;
import helpers.*;
import static org.junit.Assert.*;

public class LoginSteps {

    private LoginHelper helper = new LoginHelper();

    @And("Anasayfaya yönlendirilir")
    public void verifyhomepageredirect() {
        helper.verifyHomePageRedirect();
    }
    @And("Geçersiz bilgilerle giriş yapar")
    public void performloginwithınvalidcredentials() {
        helper.performLoginWithInvalidCredentials();
    }
    @And("Geçerli bilgilerle giriş yapar")
    public void performloginwithvalidcredentials() {
        helper.performLoginWithValidCredentials();
    }
    @And("Hata mesajı görüntülenir")
    public void verifyerrormessagedisplayed() {
        helper.verifyErrorMessageDisplayed();
    }
    @And("Kullanıcı login sayfasında")
    public void navigatetologinpage() {
        helper.navigateToLoginPage();
    }


    @Given("Test ortamı hazırlanır")
    public void test_ortamı_hazırlanır() {

    }
    @When("Empty credentials")
    public void empty_credentials() {

    }
    @Then("Sonuçlar doğrulanır")
    public void sonuçlar_doğrulanır() {

    }

    @When("Successful login")
    public void successfulLogin() {

    }

    @When("Invalid credentials")
    public void invalidCredentials() {
    }
}