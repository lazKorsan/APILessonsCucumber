package learningAPI;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import learningAPI.pojos.Booking;
import learningAPI.pojos.BookingDates;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class L4_UseSchema {

    private static RequestSpecification spec;

    // @BeforeClass, TestNG anotasyonudur. Bu sınıftaki test metotları çalışmadan
    // önce SADECE BİR KEZ çalışır. Testler için ortak hazırlıkları yapmak için kullanılır.
    @BeforeClass
    public void setup() {
        spec = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .setContentType(ContentType.JSON)
                .build();
    }

    /**
     * Test 1: Temel Şema Doğrulaması
     * Amaç: Bir GET isteğinden dönen yanıtın yapısının, önceden tanımlanmış
     * bir şemaya (SingleBookingSchema.json) uyup uymadığını kontrol etmek.
     */
    @Test
    public void basicSchemaValidationTest() {
        given()
                .spec(spec)
        .when()
                .get("/booking/1")
        .then()
                .statusCode(200)
                // Yanıtın gövdesinin, belirtilen şema dosyasına %100 uyduğunu doğrula.
                .body(matchesJsonSchemaInClasspath("schemas/SingleBookingSchema.json"));
    }

    /**
     * Test 2: Birleşik Doğrulama (Şema ve Değer)
     * Amaç: Bir yanıtın hem yapısal olarak şemaya uyduğunu hem de içindeki belirli
     * alanların beklenen değerlere sahip olduğunu AYNI ANDA doğrulamak.
     * Rest-Assured, then() bloğu içinde birden çok .body() kontrolünü zincirlemeye izin verir.
     */
    @Test
    public void combinedSchemaAndValueValidationTest() {
        given()
                .spec(spec)
        .when()
                .get("/booking/1")
        .then()
                .statusCode(200)
                // 1. Doğrulama: Yanıtın yapısı şemaya uyuyor mu?
                .body(matchesJsonSchemaInClasspath("schemas/SingleBookingSchema.json"))
                // 2. Doğrulama: Yapı doğruysa, içindeki belirli bir alanın değeri beklenen gibi mi?
                .body("firstname", equalTo("Eric"));
    }

    /**
     * Test 3: POST İsteği Yanıtını Doğrulama
     * Amaç: Yeni bir kaynak oluşturduktan sonra dönen ve farklı bir yapıya sahip olan
     * yanıtın, bu yapıya özel olarak hazırlanmış ikinci bir şema (BookingResponseSchema.json)
     * ile doğrulanması.
     */
    @Test
    public void postRequestSchemaValidationTest() {
        // Test verisini POJO'lar ile hazırlıyoruz.
        BookingDates bookingDates = new BookingDates("2024-01-01", "2024-02-01");
        Booking booking = new Booking("Tugrul", "Test", 500, true, bookingDates, "None");

        given()
                .spec(spec)
                .body(booking)
        .when()
                .post("/booking")
        .then()
                .statusCode(200)
                // Bu sefer, POST yanıtının yapısını tanımlayan farklı bir şema kullanıyoruz.
                .body(matchesJsonSchemaInClasspath("schemas/BookingResponseSchema.json"));
    }
}
