package stepdefinitions;

import com.github.fge.jackson.JacksonUtils;
import io.cucumber.java.en.*;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RestFullStepdefinitions {

    String url;
    String requestUrl;
    Response response;

    /**
     * Given step: Test için temel URL'nin hazırlanması
     * Bu step, API'nin base URL'sini set eder
     * Örnek: https://restful-booker.herokuapp.com/
     */

    @Given("Kullanici url hazirlar")
    public void kullanici_url_hazirlar() {
        // Base URL set edildi - tüm API çağrıları bu URL üzerinden yapılacak
        url = "https://restful-booker.herokuapp.com/";

    }
    @Then("Hazirladigi url e endpoint ekler")
    public void hazirladigi_url_e_endpoint_ekler() {

        /**
         * Then step: Base URL'ye spesifik endpoint eklenmesi
         * Bu step, tam request URL'sini oluşturur
         * Örnek: https://restful-booker.herokuapp.com/ + booking/10 = https://restful-booker.herokuapp.com/booking/10
         */

        //// Oluşturulan URL: https://restful-booker.herokuapp.com/booking/10
        String endPoint = "booking/10";
        requestUrl = url + endPoint;

    }
    @Then("test datalarini olusturacak kod blogunu hazirlar")
    public void test_datalarini_olusturacak_kod_blogunu_hazirlar() {

        /**
         * Then step: API çağrısının yapılması ve response'un alınması
         * Bu step, GET request gönderir ve dönen response'u kaydeder
         * RestAssured kullanılarak HTTP GET isteği yapılır
         */
        // GET request gönderiliyor ve response değişkenine atanıyor
        response = given().when().get(requestUrl);
        // // given(): Request specification başlatır
        //        // when(): HTTP method ve URL belirtir
        //        // get(): GET request gönderir

    }

    /**
     * Then step: API response'unun konsola yazdırılması
     * Bu step, dönen response'u okunabilir formatta loglar
     * Debug ve verification amaçlı kullanılır
     */
    @Then("donen yaniti konsola yazdirir")
    public void donen_yaniti_konsola_yazdirir() {

        // prettyPrint(): Sadece response body'yi formatlı şekilde yazdırır
        response.prettyPrint(); // Sadece body'i yazdırır

        // prettyPeek(): Tüm response'u (headers, status, body) formatlı şekilde yazdırır
        response.prettyPeek(); // Tüm response'u yazdırır

        // < -- =================================================
        /*
        response = given().when().get(requestUrl); DETAYLI AÇIKLAMASI

1. given() - REQUEST HAZIRLIK AŞAMASI
   • HTTP isteğinin konfigürasyonunu başlatır
   • Request header'ları, body, parametreler, authentication gibi ayarların yapıldığı bölümdür
   • RequestSpecification interface'ini döndürür
   • Örnek kullanım:
        given()
          .header("Content-Type", "application/json")
          .auth().basic("kullanici", "sifre")
          .queryParam("sayfa", 2)

2. when() - EXECUTION TANIMLAMA
   • Request'in "ne zaman" ve "nereye" yapılacağını belirtir
   • HTTP method (GET, POST, PUT, DELETE) ve target URL'nin tanımlandığı bölümdür
   • Request specification'tan request execution'a geçişi sağlar
   • "When" kelimesi BDD (Behavior Driven Development) yaklaşımına uygundur

3. get(requestUrl) - REQUEST GÖNDERME
   • Belirtilen URL'ye GET request gönderir
   • requestUrl parametresi: hedef endpoint URL'sini alır
   • HTTP GET methodunu execute eder ve Response'u döndürür
   • Diğer HTTP method alternatifleri:
        .post(requestUrl)    - CREATE işlemleri
        .put(requestUrl)     - UPDATE işlemleri
        .delete(requestUrl)  - DELETE işlemleri
        .patch(requestUrl)   - PARTIAL UPDATE işlemleri

4. response = - RESPONSE'U SAKLAMA
   • API'den dönen response'u bir değişkende saklar
   • Response interface tipindedir
   • Sonraki adımlarda assertion, veri çekme, loglama gibi işlemler için kullanılır

ÖRNEK TAM KULLANIM:
response = given()
             .header("Content-Type", "application/json")
             .queryParam("aktif", true)
          .when()
             .get("https://api.ornek.com/kullanici/1")
          .then()
             .extract().response();

RESPONSE ÜZERİNDE YAPILABİLECEK İŞLEMLER:
• response.getStatusCode() - Status kodunu al
• response.getBody().asString() - Response body'i string olarak al
• response.jsonPath().getString("isim") - JSON path ile veri çek
• response.prettyPrint() - Formatlı yazdır
• response.then().statusCode(200) - Doğrudan assertion yap
         */

        // =============================================== -->

        /**
         * BU SINIFIN ÇALIŞMA MANTIĞI:
         *
         * 1. URL Hazırlama (Given)
         *    - Base URL set edilir: "https://restful-booker.herokuapp.com/"
         *
         * 2. Endpoint Ekleme (Then)
         *    - Base URL'ye endpoint eklenir: "booking/10"
         *    - Tam URL oluşur: "https://restful-booker.herokuapp.com/booking/10"
         *
         * 3. API Çağrısı (Then)
         *    - GET request gönderilir
         *    - Response alınır ve 'response' değişkenine kaydedilir
         *
         * 4. Response Yazdırma (Then)
         *    - Response konsola formatlı şekilde yazdırılır
         *    - Hem body hem de tüm response detayları görüntülenir
         *
         * KULLANIM ÖRNEĞİ:
         * Feature dosyasında:
         *   Given Kullanici url hazirlar
         *   Then Hazirladigi url e endpoint ekler
         *   Then test datalarini olusturacak kod blogunu hazirlar
         *   Then donen yaniti konsola yazdirir
         */

// < - =====================================
// < -
// // 1. Status Code Kontrolü
//int statusCode = response.getStatusCode();
//
//// 2. Response Body'i String Olarak Alma
//String body = response.getBody().asString();
//
//// 3. JSON Path ile Veri Çekme
//String firstName = response.jsonPath().getString("data.first_name");
//
//// 4. Header Bilgileri
//String contentType = response.getHeader("Content-Type");
//
//// 5. Response Time
//long responseTime = response.getTime();
//
//// 6. Pretty Print
//response.prettyPrint();
//
//// 7. Doğrudan Assertion
//response.then().statusCode(200).body("data.id", equalTo(1));

    }

}
