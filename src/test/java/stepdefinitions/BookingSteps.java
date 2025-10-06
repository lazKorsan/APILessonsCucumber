package stepdefinitions;

import io.cucumber.java.en.*;
import helpers.*;
import static org.junit.Assert.*;

public class BookingSteps {

    private BookingHelper helper = new BookingHelper();

    @And("Yeni rezervasyon formu açılır")
    public void openbookingform() {
        helper.openBookingForm();
    }
    @And("Rezervasyon listesinde görüntülenir")
    public void verifybookingınlist() {
        helper.verifyBookingInList();
    }
    @And("Rezervasyon oluşturulur")
    public void createbooking() {
        helper.createBooking();
    }
    @And("Rezervasyon bilgileri doldurulur")
    public void fillbookingdetails() {
        helper.fillBookingDetails();
    }
    @And("Rezervasyon başarı mesajı görüntülenir")
    public void verifybookingsuccessmessage() {
        helper.verifyBookingSuccessMessage();
    }

    @Given("Test verileri hazırlanır")
    public void test_verileri_hazırlanır() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @When("Cancel booking")
    public void cancel_booking() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("İşlem sonucu doğrulanır")
    public void işlem_sonucu_doğrulanır() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }


    @When("Update booking")
    public void updateBooking() {

    }
}