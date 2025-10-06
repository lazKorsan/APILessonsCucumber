package learningAPI;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class L4_UseSchemaAndMap {

    private static RequestSpecification spec;
    // Oluşturulan rezervasyonun ID'sini testler arasında taşımak için bir sınıf değişkeni.
    private static int bookingId;

    @BeforeClass
    public void setup() {
        spec = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .setContentType(ContentType.JSON)
                .build();
    }

    /**
     * Test 1: Map ile POST İsteği ve Şema Doğrulama
     * AMAÇ: POJO sınıfı oluşturmadan, anında ve dinamik olarak iç içe bir JSON gövdesi
     * oluşturmak için Map'in nasıl kullanılacağını göstermek.
     *
     * YAKLAŞIM:
     * 1. İç içe olan "bookingdates" yapısı için bir Map (innerMap) oluşturulur.
     * 2. Ana JSON gövdesi için ikinci bir Map (outerMap) oluşturulur.
     * 3. innerMap, outerMap'in içine bir anahtar-değer olarak yerleştirilir.
     * 4. Bu Map, .body() metoduna verilerek POST isteği gönderilir.
     * 5. Dönen yanıtın yapısı, "BookingResponseSchema.json" şeması ile doğrulanır.
     */
    @Test(priority = 1) // Testlerin çalışma sırasını belirlemek için priority kullanılır.
    public void createBookingWithMapTest() {
        // İç içe JSON yapısı için iç Map'i oluştur
        Map<String, String> bookingDatesMap = new HashMap<>();
        bookingDatesMap.put("checkin", "2024-05-01");
        bookingDatesMap.put("checkout", "2024-05-10");

        // Ana JSON gövdesi için dış Map'i oluştur
        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("firstname", "Tugrul");
        requestBodyMap.put("lastname", "MapTest");
        requestBodyMap.put("totalprice", 250);
        requestBodyMap.put("depositpaid", true);
        requestBodyMap.put("bookingdates", bookingDatesMap); // İç Map'i dış Map'e ekle
        requestBodyMap.put("additionalneeds", "Late check-in");

        // İsteği gönder, yanıtı doğrula ve bir sonraki test için bookingId'yi al
        bookingId = given()
                .spec(spec)
                .body(requestBodyMap) // Body olarak POJO yerine Map kullanıyoruz.
        .when()
                .post("/booking")
        .then()
                .statusCode(200)
                // Yanıtın yapısının beklenen sözleşmeye uyduğunu doğrula
                .body(matchesJsonSchemaInClasspath("schemas/BookingResponseSchema.json"))
                // bookingid'nin null olmadığını da kontrol edelim
                .body("bookingid", notNullValue())
                // Bir sonraki testte kullanmak üzere bookingid'yi yanıttan çıkar (extract)
                .extract().path("bookingid");

        System.out.println("Oluşturulan Rezervasyon ID: " + bookingId);
    }

    /**
     * Test 2: Map ile PATCH İsteği ve Birleşik Doğrulama
     * AMAÇ: Bir kaynağın sadece birkaç alanını güncellemek için PATCH isteği ile Map
     * kullanmanın ne kadar ideal olduğunu göstermek.
     * 
     * YAKLAŞIM:
     * 1. Sadece güncellenecek alanları ("firstname" ve "totalprice") içeren küçük bir Map oluşturulur.
     *    Bu, tüm alanları içeren bir POJO oluşturmaktan çok daha pratiktir.
     * 2. restful-booker'ın PATCH endpoint'i kimlik doğrulaması (token) gerektirir.
     *    Bu token alınır ve isteğin header'ına eklenir.
     * 3. PATCH isteği, önceki testte alınan bookingId kullanılarak gönderilir.
     * 4. Dönen yanıt, hem "SingleBookingSchema.json" şeması ile yapısal olarak,
     *    hem de güncellenen değerlerin doğruluğu kontrol edilerek doğrulanır.
     */
    @Test(priority = 2, dependsOnMethods = "createBookingWithMapTest") // Bu test, ilk testin başarısına bağlıdır.
    public void patchBookingWithMapTest() {
        // Adım 2.1: Kimlik doğrulama token'ı al
        String token = getAuthToken();

        // Adım 2.2: Sadece güncellenecek alanları içeren bir Map oluştur
        Map<String, Object> patchRequestBodyMap = new HashMap<>();
        patchRequestBodyMap.put("firstname", "Tugrul Updated");
        patchRequestBodyMap.put("totalprice", 300);

        // Adım 2.3: PATCH isteğini gönder ve yanıtı doğrula
        given()
                .spec(spec)
                .header("Cookie", "token=" + token) // Token'ı Cookie header'ı olarak ekle
                .body(patchRequestBodyMap)
        .when()
                .patch("/booking/" + bookingId)
        .then()
                .statusCode(200)
                // Birleşik Doğrulama:
                // 1. Yanıtın yapısı doğru mu?
                .body(matchesJsonSchemaInClasspath("schemas/SingleBookingSchema.json"))
                // 2. Yapı doğruysa, güncellenen alanların değerleri doğru mu?
                .body("firstname", equalTo("Tugrul Updated"))
                .body("totalprice", equalTo(300))
                // 3. Değişmeyen bir alanın hala aynı kaldığını da kontrol edelim.
                .body("lastname", equalTo("MapTest"));

        System.out.println("Rezervasyon (" + bookingId + ") başarıyla güncellendi.");
    }

    /**
     * restful-booker API'si için kimlik doğrulama token'ı alan yardımcı metot.
     * @return String olarak token.
     */
    private String getAuthToken() {
        Map<String, String> authBody = new HashMap<>();
        authBody.put("username", "admin");
        authBody.put("password", "password123");

        return given()
                .spec(spec)
                .body(authBody)
        .when()
                .post("/auth")
        .then()
                .statusCode(200)
                .extract().path("token");
    }
}
