package learningAPI;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

// JSON Schema Validator'ı kullanabilmek için bu import gereklidir.
// Bu sınıf, rest-assured-json-schema-validator kütüphanesinden gelir.
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.RestAssured.given;

public class L3_UseSchema {

    private static RequestSpecification spec;

    @BeforeAll
    public static void setup() {
        // Testlerimizde kullanacağımız API için ortak ayarları (base URI gibi)
        // merkezi bir yerden yönetmek, kod tekrarını önler ve bakımı kolaylaştırır.
        spec = new RequestSpecBuilder()
                .setBaseUri("https://reqres.in/api")
                .setContentType(ContentType.JSON)
                .build();
    }

    /**
     * BU TESTİN AMACI:
     * Bu test, bir API yanıtının sadece içindeki verilerin doğruluğunu değil,
     * aynı zamanda yapısının, veri tiplerinin ve formatının önceden tanımlanmış
     * bir "sözleşmeye" (JSON Şeması) uyup uymadığını kontrol eder.
     *
     * NEDEN ÖNEMLİDİR? (SÖZLEŞME TESTİ - CONTRACT TESTING)
     * Bir API'nin arka ucunda (backend) yapılan beklenmedik bir değişiklik,
     * testlerinizin başarısız olmasına neden olabilir. Örneğin:
     *  - Bir alanın adı değişebilir (örn: "first_name" -> "firstName").
     *  - Bir alanın veri tipi değişebilir (örn: "id" alanı sayı iken metin (String) olabilir).
     *  - Zorunlu bir alan yanıttan kaldırılabilir.
     *
     * JSON Şema Doğrulaması, bu tür yapısal ve anlamsal hataları anında yakalar
     * ve API'nin kararlı bir şekilde çalıştığını garanti altına alır.
     */
    @Test
    public void userListSchemaValidationTest() {

        // 1. ADIM: İsteği Hazırla ve Gönder
        given()
                .spec(spec) // @BeforeAll'da hazırladığımız ortak ayarları kullan.
        .when()
                // UserListSchema.json dosyasının yapısına uygun bir yanıt döndüren endpoint'e istek atıyoruz.
                .get("/users?page=2")
        .then()
                // 2. ADIM: Temel Doğrulamaları Yap
                .statusCode(200)

                // 3. ADIM: JSON Şema Doğrulamasını Uygula
                // .body() metoduna, Hamcrest matcher'ları yerine JsonSchemaValidator'ı veriyoruz.
                .body(
                    // matchesJsonSchemaInClasspath metodu, projenin "classpath"inde (kaynaklar klasöründe)
                    // bulunan bir şema dosyasını bulup yüklemesini söyler.
                    // Maven projelerinde, "src/test/resources" klasörü otomatik olarak classpath'e dahildir.
                    matchesJsonSchemaInClasspath("schemas/UserListSchema.json")
                );

        // BU TEST BAŞARILI OLURSA, ANLAMI ŞUDUR:
        // "/users?page=2" endpoint'inden dönen yanıtın yapısı,
        // "src/test/resources/schemas/UserListSchema.json" dosyasında tanımlanan
        // tüm kurallara (zorunlu alanlar, veri tipleri, formatlar vb.) %100 uymaktadır.
    }
}
