package learningAPI;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class L7_UseDataProvider {

    private static RequestSpecification spec;

    @BeforeClass
    public void setup() {
        // Testlerimizde kullanacağımız reqres.in API'si için ortak ayarları hazırlıyoruz.
        spec = new RequestSpecBuilder()
                .setBaseUri("https://reqres.in/api")
                .build();
    }

    /**
     * BU METODUN AMACI: Veri Sağlayıcı (Data Provider)
     *
     * @DataProvider anotasyonu, bu metodun bir test metoduna veri sağlayacağını belirtir.
     * 'name' parametresi, bu veri sağlayıcıya benzersiz bir isim verir. Bu isim,
     * @Test anotasyonunda bu sağlayıcıya bağlanmak için kullanılır.
     *
     * YAPI:
     * Metot, bir 2D Object dizisi (Object[][]) döndürmelidir.
     * - Dıştaki dizi, toplamda kaç farklı test çalışması olacağını belirtir. (Burada 4)
     * - İçteki her bir dizi, tek bir test çalışmasının parametrelerini temsil eder.
     *   (Burada {kullanıcıID, beklenenIsim})
     *
     * @return Test için kullanılacak kullanıcı ID'leri ve beklenen isimleri içeren veri setleri.
     */
    @DataProvider(name = "userData")
    public Object[][] userDataProvider() {
        return new Object[][]{
                {1, "George"},
                {2, "Janet"},
                {3, "Emma"},
                {4, "Eve"} // Yeni bir veri seti eklemek bu kadar kolay!
        };
    }

    /**
     * BU TESTİN AMACI: Veri Odaklı Test (Data-Driven Test)
     *
     * Bu test, test mantığını (logic) ve test verisini (data) birbirinden ayırır.
     * Test mantığı burada yazılırken, test verisi "userData" isimli DataProvider'dan gelir.
     *
     * YAKLAŞIM:
     * 1. @Test anotasyonuna, 'dataProvider' parametresi ile bağlanmak istediğimiz
     *    veri sağlayıcısının adını veririz.
     * 2. Bu test metodu, DataProvider'daki her bir iç dizinin yapısına uygun olarak
     *    parametreler alır (int userId, String expectedFirstName).
     * 3. TestNG, "userData" sağlayıcısındaki her bir satır için bu testi otomatik olarak
     *    bir kez çalıştırır. Her çalıştırmada, ilgili satırdaki verileri metodun
     *    parametrelerine atar.
     */
    @Test(dataProvider = "userData")
    public void getUserByIdAndValidateNameTest(int userId, String expectedFirstName) {

        System.out.println("Çalışan Test Verisi -> ID: " + userId + ", Beklenen İsim: " + expectedFirstName);

        given()
                .spec(spec)
        .when()
                .get("/users/" + userId)
        .then()
                .statusCode(200)
                // Doğrulamaları, DataProvider'dan gelen dinamik verileri kullanarak yapıyoruz.
                .body("data.id", equalTo(userId))
                .body("data.first_name", equalTo(expectedFirstName));

        System.out.println("--> ID: " + userId + " için doğrulama BAŞARILI");
    }
}
