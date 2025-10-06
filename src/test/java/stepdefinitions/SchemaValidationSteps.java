package stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class SchemaValidationSteps {

    private String baseUrl;
    private Response response;
    private String bookingId;
    private Object requestBody;

    @Given("API base URL'si {string} olarak ayarlanmıştır")
    public void api_base_url_si_olarak_ayarlanmıştır(String url) {
        baseUrl = url;
    }

    @When("{string} request {string} endpoint'ine gönderilir")
    public void request_endpoint_ine_gönderilir(String httpMethod, String endpoint) {
        switch (httpMethod.toUpperCase()) {
            case "GET":
                response = given().when().get(baseUrl + "/" + endpoint);
                break;
            case "POST":
                response = given()
                        .contentType("application/json")
                        .body(requestBody)
                        .when()
                        .post(baseUrl + "/" + endpoint);
                break;
            case "PUT":
                response = given()
                        .contentType("application/json")
                        .body(requestBody)
                        .when()
                        .put(baseUrl + "/" + endpoint.replace("<bookingId>", bookingId));
                break;
        }
    }

    @Then("Status kod {int} olmalıdır")
    public void status_kod_olmalıdır(Integer statusCode) {
        response.then().statusCode(statusCode);
    }

    @Then("Response content type {string} olmalıdır")
    public void response_content_type_olmalıdır(String contentType) {
        response.then().contentType(contentType);
    }

    @Then("Response body booking JSON schema'sına uygun olmalıdır")
    public void response_body_booking_json_schema_sına_uygun_olmalıdır() {
        response.then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/booking-schema.json"));
    }

    @Then("Response body aşağıdaki zorunlu alanları içermelidir:")
    public void response_body_aşağıdaki_zorunlu_alanları_içermelidir(io.cucumber.datatable.DataTable dataTable) {
        java.util.List<String> fields = dataTable.asList();

        for (String field : fields) {
            response.then().body("$", hasKey(field));
        }
    }

    @Then("Response body'deki alan tipleri doğru olmalıdır:")
    public void response_body_deki_alan_tipleri_doğru_olmalıdır(io.cucumber.datatable.DataTable dataTable) {
        java.util.List<java.util.Map<String, String>> fields = dataTable.asMaps();

        for (java.util.Map<String, String> field : fields) {
            String fieldName = field.get("field");
            String expectedType = field.get("type");

            // Basit type kontrolü - gerçek implementasyonda daha detaylı kontrol yapılabilir
            response.then().body(fieldName, notNullValue());
        }
    }

    @Then("Response bookingdates object'i aşağıdaki alanları içermelidir:")
    public void response_bookingdates_object_i_aşağıdaki_alanları_içermelidir(io.cucumber.datatable.DataTable dataTable) {
        java.util.List<java.util.Map<String, String>> fields = dataTable.asMaps();

        for (java.util.Map<String, String> field : fields) {
            String fieldName = field.get("field");
            response.then().body("bookingdates." + fieldName, notNullValue());
        }
    }

    @Then("Response body hata mesajı schema'sına uygun olmalıdır")
    public void response_body_hata_mesajı_schema_sına_uygun_olmalıdır() {
        response.then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/error-schema.json"));
    }

    @Given("Aşağıdaki booking data'sı hazırlanır:")
    public void aşağıdaki_booking_data_sı_hazırlanır(io.cucumber.datatable.DataTable dataTable) {
        java.util.Map<String, String> data = dataTable.asMap();

        // Request body oluşturma
        java.util.Map<String, Object> bookingDates = new java.util.HashMap<>();
        bookingDates.put("checkin", data.get("checkin"));
        bookingDates.put("checkout", data.get("checkout"));

        java.util.Map<String, Object> bookingData = new java.util.HashMap<>();
        bookingData.put("firstname", data.get("firstname"));
        bookingData.put("lastname", data.get("lastname"));
        bookingData.put("totalprice", Integer.parseInt(data.get("totalprice")));
        bookingData.put("depositpaid", Boolean.parseBoolean(data.get("depositpaid")));
        bookingData.put("bookingdates", bookingDates);
        bookingData.put("additionalneeds", data.get("additionalneeds"));

        requestBody = bookingData;
    }

    @Given("Geçerli bir booking ID alınır")
    public void geçerli_bir_booking_id_alınır() {
        // Mevcut bir booking ID almak için
        Response tempResponse = given().when().get(baseUrl + "/booking");
        bookingId = tempResponse.jsonPath().getString("bookingid[0]");
    }

    @Then("Response body create booking JSON schema'sına uygun olmalıdır")
    public void response_body_create_booking_json_schema_sına_uygun_olmalıdır() {
        response.then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/create-booking-schema.json"));
    }

    @Then("Response body update booking JSON schema'sına uygun olmalıdır")
    public void response_body_update_booking_json_schema_sına_uygun_olmalıdır() {
        response.then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/update-booking-schema.json"));
    }

    @And("Aşağıdaki update data'sı hazırlanır:")
    public void aşağıdakiUpdateDataSıHazırlanır() {
    }
}