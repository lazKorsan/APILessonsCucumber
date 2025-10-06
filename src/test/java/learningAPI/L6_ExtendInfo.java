package learningAPI;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import learningAPI.pojos.ReqresUser;
import learningAPI.pojos.UserListPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class L6_ExtendInfo {

    private static RequestSpecification spec;

    @BeforeClass
    public void setup() {
        spec = new RequestSpecBuilder()
                .setBaseUri("https://reqres.in/api")
                .build();
    }

    // --- Test Senaryosu 1: Veri Odaklı Test (Data-Driven Testing) ---
    @DataProvider(name = "userIdProvider")
    public Object[][] userIdProvider() {
        return new Object[][]{
                {1},
                {2},
                {3}
        };
    }

    @Test(dataProvider = "userIdProvider")
    public void dataDrivenGetUserByIdTest(int userId) {
        System.out.println("Şu anki test verisi (Kullanıcı ID): " + userId);
        given().spec(spec).when().get("/users/" + userId).then().statusCode(200).body("data.id", equalTo(userId));
    }

    // --- Test Senaryosu 2: Negatif Senaryo Testi (Negative Path Testing) ---
    @Test
    public void negativeUserNotFoundTest() {
        given().spec(spec).when().get("/users/999").then().statusCode(404);
    }

    // --- Test Senaryosu 3: Yanıt Listesini POJO Listesine Çevirme ---
    @Test
    public void deserializeResponseToListOfPojosTest() {
        UserListPage userListPage = given().spec(spec).when().get("/users?page=2").then().statusCode(200).extract().as(UserListPage.class);
        Assert.assertEquals(userListPage.getPage(), 2);
        List<ReqresUser> users = userListPage.getData();
        for (ReqresUser user : users) {
            Assert.assertTrue(user.getEmail().endsWith("@reqres.in"));
        }
        ReqresUser userWithId7 = users.stream().filter(user -> user.getId() == 7).findFirst().orElse(null);
        Assert.assertNotNull(userWithId7, "Listede ID'si 7 olan kullanıcı bulunamadı.");
        Assert.assertEquals(userWithId7.getFirstName(), "Michael");
    }

    // --- Test Senaryosu 4: JsonPath ile Gelişmiş Veri Çıkarma ---

    /**
     * AMAÇ: Tam bir POJO deserialization yapmadan, bir JSON yanıtının içinden belirli
     * verileri (tek bir değer, bir liste, vb.) hızlı ve esnek bir şekilde çıkarmak.
     *
     * YAKLAŞIM:
     * 1. .extract().jsonPath() metodu kullanılarak yanıttan bir JsonPath nesnesi elde edilir.
     * 2. Bu nesne, GPath (Groovy Path) adı verilen bir dil kullanarak JSON içinde gezinti yapmamızı sağlar.
     *    - `jsonPath.getString("data.first_name")` -> `data` listesindeki tüm `first_name` değerlerini bir liste olarak alır.
     *    - `jsonPath.getString("data[0].email")` -> `data` listesinin ilk elemanının (`[0]`) `email` alanını alır.
     */
    @Test
    public void jsonPathExtractionTest() {
        JsonPath jsonPath = given()
                .spec(spec)
        .when()
                .get("/users?page=2")
        .then()
                .statusCode(200)
                .extract().jsonPath();

        // Örnek 1: Yanıttaki tüm kullanıcıların email adreslerini bir liste olarak al.
        List<String> allEmails = jsonPath.getList("data.email");
        System.out.println("Tüm Emailler: " + allEmails);
        Assert.assertTrue(allEmails.contains("michael.lawson@reqres.in"));

        // Örnek 2: Listenin ikinci elemanının (`[1]`) soyadını al.
        String secondUserLastName = jsonPath.getString("data[1].last_name");
        System.out.println("İkinci Kullanıcının Soyadı: " + secondUserLastName);
        Assert.assertEquals(secondUserLastName, "Ferguson");
    }


    // --- Test Senaryosu 5: Koşullu Günlük Kaydı (Conditional Logging) ---

    /**
     * AMAÇ: Test çıktılarını (console logs) temiz tutmak ve sadece bir test başarısız
     * olduğunda hata ayıklama için gerekli olan detaylı bilgileri (istek ve yanıt) görmek.
     *
     * YAKLAŞIM:
     * .log().ifValidationFails() metodu, zincirdeki bir sonraki doğrulama (.body(), .statusCode() vb.)
     * başarısız olursa, o ana kadarki tüm istek ve yanıt bilgilerini konsola yazdırır.
     * Test başarılı olursa, hiçbir şey yazdırmaz.
     */
    @Test
    public void conditionalLoggingTest() {
        System.out.println("\n--- Koşullu Günlük Kaydı Testi (Başarısız Olması Bekleniyor) ---");
        System.out.println("Bu test başarısız olduğunda, konsolda tüm istek ve yanıt detaylarını göreceksiniz.");

        given()
                .spec(spec)
                .log().ifValidationFails() // Sadece doğrulama başarısız olursa logla
        .when()
                .get("/users/2")
        .then()
                // Bu doğrulama kasıtlı olarak başarısız olacak şekilde ayarlandı.
                .statusCode(201); // Beklenen 200, ama biz 201 diyoruz.

        // Not: Bu test normalde başarısız olacaktır. TestNG raporunda "failed" olarak görünmesi beklenir.
        // Konsol çıktısında ise tüm isteğin ve yanıtın loglandığını göreceksiniz.
    }
}
