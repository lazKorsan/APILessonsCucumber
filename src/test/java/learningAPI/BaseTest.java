package learningAPI;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    protected static RequestSpecification specBooker;
    protected static RequestSpecification specJsonPlaceholder;

    // Tüm 200 OK yanıtları için ortak doğrulama kurallarını içeren şartname.
    protected static ResponseSpecification specOk;

    @BeforeAll
    public static void setup() {
        // Request Specifications
        specBooker = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .setContentType(ContentType.JSON)
                .build();

        specJsonPlaceholder = new RequestSpecBuilder()
                .setBaseUri("https://jsonplaceholder.typicode.com")
                .setContentType(ContentType.JSON)
                .build();

        // Response Specification
        // Projedeki tüm 200 OK yanıtlarının uyması gereken temel kuralları burada tanımlarız.
        specOk = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }
}
