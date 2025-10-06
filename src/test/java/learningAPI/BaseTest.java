package learningAPI;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

// Bu sınıf, projedeki tüm test sınıfları için bir temel (base) görevi görür.
// Ortak ayarlar ve konfigürasyonlar bu sınıfta merkezi olarak yönetilir.
public class BaseTest {

    // protected erişim belirleyicisi, bu değişkenlerin bu sınıftan miras alan
    // alt sınıflar tarafından doğrudan kullanılabilmesini sağlar.
    protected static RequestSpecification specBooker;
    protected static RequestSpecification specJsonPlaceholder;

    @BeforeAll
    public static void setup() {
        // Restful-Booker API için ortak ayarlar
        specBooker = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .setContentType(ContentType.JSON)
                .build();

        // JSONPlaceholder API için ortak ayarlar
        specJsonPlaceholder = new RequestSpecBuilder()
                .setBaseUri("https://jsonplaceholder.typicode.com")
                .setContentType(ContentType.JSON)
                .build();
    }
}
