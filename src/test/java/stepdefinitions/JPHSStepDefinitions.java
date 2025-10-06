package stepdefinitions;

import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import utilities.ConfigReader;

import static org.junit.Assert.assertEquals;

public class JPHStepdefinitions {

    String url="";
    Response response;
    JsonPath resJP;
    JSONObject reqBody;


    @Given("kullanici {string} adresine gider")
    public void kullanici_adresine_gider(String configBaseUrl) {
        url+= ConfigReader.getProperty(configBaseUrl);
    }
    @Given("kullanici path parametresi olarak {string} girer")
    public void kullanici_path_parametresi_olarak_girer(String pathParams) {
    url = url+"/"+pathParams;
    }
    @Given("kullanici GET request yaparak donen response bilgilerini kaydeder")
    public void kullanici_get_request_yaparak_donen_response_bilgilerini_kaydeder() {
    response = RestAssured.given().when().get(url);
    }
    @Given("kullanici response statusCode degerinin {int} oldugunu test eder")
    public void kullanici_response_status_code_degerinin_oldugunu_test_eder(int expstatusCode) {
        assertEquals(expstatusCode,response.getStatusCode());
    }
    @Given("kullanici content type degerinin {string} oldugunu test eder")
    public void kullanici_content_type_degerinin_oldugunu_test_eder(String expContentType) {

        assertEquals(expContentType,response.getContentType());
    }
    @Given("kullanici response degerini jsonpath olarak kaydeder")
    public void kullanici_response_degerini_jsonpath_olarak_kaydeder() {

        resJP = response.jsonPath();


    }
    @Given("kullanici response {string} degerinin {string} oldugunu test eder")
    public void kullanici_response_degerinin_oldugunu_test_eder(String expKey, String expValue) {

        assertEquals(expValue,resJP.getString(expKey));


    }


}
