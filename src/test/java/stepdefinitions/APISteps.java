package stepdefinitions;

import io.cucumber.java.en.*;
import helpers.*;
import static org.junit.Assert.*;

public class APISteps {

    private APIHelper helper = new APIHelper();

    @And("API endpoint {string} hazırlanır")
    public void setupapıendpoint() {
        helper.setupAPIEndpoint();
    }
    @And("Response content type {string} olmalıdır")
    public void verifycontenttype() {
        helper.verifyContentType();
    }
    @Then("Status kod {int} olduğu doğrulanır")
    public void verifystatuscode() {
        helper.verifyStatusCode();
    }
    @And("{string} endpoint'ine {string} request gönderilir")
    public void sendapırequest() {
        helper.sendAPIRequest();
    }
    @And("Response body JSON schema'ya uygun olmalıdır")
    public void verifyjsonschema() {
        helper.verifyJSONSchema();
    }
    @And("Response body'de {string} alanı {string} değerine sahip olmalı")
    public void verifyresponsefield() {
        helper.verifyResponseField();
    }

    @Given("API endpoint hazırlanır")
    public void apiEndpointHazırlanır() {
        
        
    }

    @When("Refund payment")
    public void refundPayment() {


    }

    @Then("Response doğrulanır")
    public void responseDoğrulanır() {
    }

    @When("Process payment")
    public void processPayment() {
    }
}