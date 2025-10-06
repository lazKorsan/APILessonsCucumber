package learningAPI;

import io.restassured.response.Response;
import learningAPI.clients.BookerApiClient;
import learningAPI.clients.JsonPlaceholderApiClient;
import learningAPI.pojos.Booking;
import learningAPI.pojos.BookingDates;
import learningAPI.pojos.BookingResponse;
import learningAPI.pojos.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

// Test sınıfı, BaseTest'ten miras alarak merkezi şartnamelere (specOk gibi) erişebilir.
public class L2_step2 extends BaseTest {

    private final BookerApiClient bookerApiClient = new BookerApiClient();
    private final JsonPlaceholderApiClient jsonApiClient = new JsonPlaceholderApiClient();

    @Test
    public void getBookingByIdTest() {
        Response response = bookerApiClient.getBookingById(10);

        response.then()
                // .statusCode(200) gibi tekil doğrulamalar yerine, merkezi şartnameyi kullanıyoruz.
                .spec(specOk)
                // Sadece bu teste özgü olan ek doğrulamaları ekliyoruz.
                .contentType("application/json; charset=utf-8")
                .header("Server", "Cowboy")
                .statusLine("HTTP/1.1 200 OK")
                .time(lessThan(5000L));
    }

    @Test
    public void updatePostTest() {
        Post post = new Post("Ahmet", "Merhaba", 10, 70);

        // Bu istemci metodu, 200 OK doğrulamasını kendi içinde (specOk ile) zaten yapıyor.
        Response response = jsonApiClient.updatePost(70, post);

        // Bu nedenle, then() bloğunda tekrar statusCode kontrolü yapmamıza gerek yok.
        // Sadece bu teste özgü olan ek doğrulamaları ekliyoruz.
        response.then()
                .contentType("application/json; charset=utf-8")
                .header("Server", "cloudflare")
                .statusLine("HTTP/1.1 200 OK")
                .body("title", equalTo(post.getTitle()));
    }

    @Test
    public void createBookingTest() {
        BookingDates bookingDates = new BookingDates("2023-01-01", "2024-01-01");
        Booking bookingRequest = new Booking("Jim", "Brown", 111, true, bookingDates, "Breakfast");

        // Bu istemci metodu, 200 OK doğrulamasını ve POJO'ya çevrimi kendi içinde yapıyor.
        BookingResponse bookingResponse = bookerApiClient.createBooking(bookingRequest);

        // Testimiz, sadece dönen POJO üzerindeki iş mantığı doğrulamalarına odaklanıyor.
        Assertions.assertTrue(bookingResponse.getBookingid() > 0);
        Booking bookingFromResponse = bookingResponse.getBooking();
        Assertions.assertEquals(bookingRequest.getFirstname(), bookingFromResponse.getFirstname());
        Assertions.assertEquals(bookingRequest.getLastname(), bookingFromResponse.getLastname());
        Assertions.assertEquals(bookingRequest.getTotalprice(), bookingFromResponse.getTotalprice());
    }
}
