package learningAPI.clients;

import io.restassured.response.Response;
import learningAPI.pojos.Booking;
import learningAPI.pojos.BookingResponse;

import static io.restassured.RestAssured.given;

// Restful-Booker API'si ile olan tüm iletişimi yöneten istemci sınıfı.
// Bu sınıf, BaseTest'ten miras alarak ortak RequestSpecification'ı kullanır.
public class BookerApiClient extends BaseTest {

    /**
     * Belirtilen ID'ye sahip rezervasyonu getirir.
     * @param bookingId Getirilecek rezervasyonun ID'si.
     * @return Rest-Assured'un Response nesnesi.
     */
    public Response getBookingById(int bookingId) {
        return given()
                .spec(specBooker) // BaseTest'ten gelen ortak ayarları kullan
        .when()
                .get("/booking/" + bookingId);
    }

    /**
     * Yeni bir rezervasyon oluşturur.
     * @param booking Gönderilecek rezervasyon bilgilerini içeren Booking POJO'su.
     * @return Yanıttan oluşturulan BookingResponse POJO'su.
     */
    public BookingResponse createBooking(Booking booking) {
        return given()
                .spec(specBooker)
                .body(booking)
        .when()
                .post("/booking")
        .then()
                .statusCode(200)
                .extract().as(BookingResponse.class);
    }
}
