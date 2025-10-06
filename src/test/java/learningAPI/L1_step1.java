package learningAPI;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
// import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import learningAPI.pojos.User;
import learningAPI.pojos.UserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class L1_step1 {

    private static RequestSpecification requestSpec;
    private static ResponseSpecification responseSpec;
    private static ResponseSpecification createSuccessSpec;

    @BeforeAll
    public static void setup() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://reqres.in/api")
                .setContentType(ContentType.JSON)
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();

        createSuccessSpec = new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType(ContentType.JSON)
                .build();
    }

    /**
     * Bu test, bir listedeki tüm elemanlar için gelişmiş doğrulama yapmayı gösterir.
     */
    @Test
    public void getListOfUsersTest() {
        given()
                .spec(requestSpec)
        .when()
                .get("/users?page=2")
        .then()
                .spec(responseSpec)
                .body("page", equalTo(2))
                .body("data", hasSize(6)) // Listede 6 eleman olduğunu doğrula
                // Hamcrest'in everyItem() matcher'ı ile listedeki HER BİR eleman için doğrulama yapabiliriz:
                .body("data.id", everyItem(notNullValue())) // Listedeki her kullanıcının ID'si var mı?
                .body("data.first_name", everyItem(instanceOf(String.class))); // Listedeki her kullanıcının adı bir String mi?
    }

    @Test
    public void jsonSchemaValidationTest() {
        given()
                .spec(requestSpec)
        .when()
                .get("/users?page=2")
        .then()
                .spec(responseSpec)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/UserListSchema.json"));
    }

    @Test
    public void getSingleUserTest() {
        given()
                .spec(requestSpec)
        .when()
                .get("/users/2")
        .then()
                .spec(responseSpec)
                .body("data.id", equalTo(2));
    }

    @Test
    public void createUserTest() {
        User newUser = new User("Tugrul", "Software Tester");

        UserResponse userResponse = given()
                .spec(requestSpec)
                .body(newUser)
        .when()
                .post("/users")
        .then()
                .spec(createSuccessSpec)
                .extract().as(UserResponse.class);

        Assertions.assertEquals(newUser.getName(), userResponse.getName());
        Assertions.assertEquals(newUser.getJob(), userResponse.getJob());
        Assertions.assertNotNull(userResponse.getId());
    }

    @Test
    public void updateUserTest() {
        User updatedUser = new User("Tugrul Updated", "Senior Software Tester");

        given()
                .spec(requestSpec)
                .body(updatedUser)
        .when()
                .put("/users/2")
        .then()
                .spec(responseSpec);
    }

    @Test
    public void patchUserTest() {
        Map<String, String> patchData = new HashMap<>();
        patchData.put("job", "Lead Software Tester");

        given()
                .spec(requestSpec)
                .body(patchData)
        .when()
                .patch("/users/2")
        .then()
                .spec(responseSpec);
    }

    @Test
    public void deleteUserTest() {
        given()
                .spec(requestSpec)
        .when()
                .delete("/users/2")
        .then()
                .statusCode(204);
    }

    @Test
    public void endToEndUserFlowTest() {
        User testUser = new User("Test User", "Temporary");

        UserResponse userResponse = given()
                .spec(requestSpec)
                .body(testUser)
        .when()
                .post("/users")
        .then()
                .spec(createSuccessSpec)
                .extract().as(UserResponse.class);

        System.out.println("Oluşturulan Kullanıcı ID: " + userResponse.getId());

        given()
                .spec(requestSpec)
                .pathParam("userId", userResponse.getId())
        .when()
                .delete("/users/{userId}")
        .then()
                .statusCode(204);

        System.out.println("Kullanıcı (" + userResponse.getId() + ") başarıyla silindi.");
    }
}
