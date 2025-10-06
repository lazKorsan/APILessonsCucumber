package stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.ConfigReader;


public class CollectApistepdefinitions {
    String url="";
    Response response;

    // curl --request GET \
    //  --url 'https://api.collectapi.com/health/dutyPharmacy?ilce=Yenimahalle&il=Ankara' \
    //  --header 'authorization: apikey your_token' \
    //  --header 'content-type: application/json'


    @Given("kullanici sorgu icin {string} adresine gider")
    public void kullanici_sorgu_icin_adresine_gider(String collectApiUrl) {
        url+= ConfigReader.getProperty(collectApiUrl);

    }
    @Then("kullanici path parametresi yerine {string} girer")
    public void kullanici_path_parametresi_yerine_girer(String pathParams) {
        url=url+"/"+pathParams;
    }
    @Then("kullanici query parametlerine il icin {string} ilce icin {string} girer")
    public void kullanici_query_parametlerine_il_icin_ilce_icin_girer(String il, String ilce) {
        url=url+"?"+"il="+il+"&"+"ilce="+ilce;

    }
    @Then("kullanici header degerleri ile bir GET request yapar ve response degeri kaydeder")
    public void kullanici_header_degerleri_ile_bir_get_request_yapar_ve_response_degeri_kaydeder() {
        response= RestAssured.given().when().header("authorization","apikey 4q90n30xa250errKiWzuxg:5W5hJ1DloVjag8g3KB4mss").get(url);

    }
    @Then("kullanici donen response degerinin yazdirir")
    public void kullanici_donen_response_degerinin_yazdirir() {
        response.prettyPrint();

    }

}