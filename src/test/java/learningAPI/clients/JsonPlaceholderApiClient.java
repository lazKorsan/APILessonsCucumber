package learningAPI.clients;

import io.restassured.response.Response;
import learningAPI.BaseTest;
import learningAPI.pojos.Post;

import static io.restassured.RestAssured.given;

// JSONPlaceholder API'si ile olan tüm iletişimi yöneten istemci sınıfı.
public class JsonPlaceholderApiClient extends BaseTest {

    /**
     * Mevcut bir post'u günceller.
     * @param postId Güncellenecek post'un ID'si.
     * @param post Gönderilecek yeni post bilgilerini içeren Post POJO'su.
     * @return Rest-Assured'un Response nesnesi.
     */
    public Response updatePost(int postId, Post post) {
        return given()
                .spec(specJsonPlaceholder) // BaseTest'ten gelen ortak ayarları kullan
                .body(post)
        .when()
                .put("/posts/" + postId);
    }
}
