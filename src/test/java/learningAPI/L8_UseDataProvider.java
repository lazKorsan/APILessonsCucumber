package learningAPI;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import learningAPI.pojos.Booking;
import learningAPI.pojos.BookingDates;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class L8_UseDataProvider {

    private static RequestSpecification spec;

    @BeforeClass
    public void setup() {
        // Testlerimizde kullanacağımız restful-booker API'si için ortak ayarları hazırlıyoruz.
        spec = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .setContentType("application/json") // Gönderdiğimiz body'nin formatını belirtiyoruz.
                .build();
    }

    /**
     * BU METODUN AMACI: Karmaşık Nesneler Sağlayan Veri Sağlayıcı
     *
     * @DataProvider, sadece basit veri tipleri (String, int) değil, aynı zamanda
     * karmaşık Java nesneleri (POJO'lar) da sağlayabilir. Bu, testlerimizi
     * son derece güçlü ve okunabilir hale getirir.
     *
     * YAPI:
     * Metot, bir 2D Object dizisi (Object[][]) döndürmelidir.
     * - İçteki her bir dizi, tek bir test çalışmasının parametrelerini temsil eder.
     *   Burada her bir iç dizi, tek bir 'Booking' nesnesi içerir.
     *
     * @return Test için kullanılacak farklı 'Booking' nesneleri içeren veri setleri.
     */
    @DataProvider(name = "bookingDataProvider")
    public Object[][] bookingDataProvider() {
        // Test verilerimizi POJO'lar olarak oluşturuyoruz.
        // Bu, veriyi yönetmeyi ve okumayı çok daha kolaylaştırır.
        return new Object[][]{
                {
                        new Booking("Tugrul", "Tester", 101, true,
                                new BookingDates("2024-01-01", "2024-01-10"), "Data Provider 1")
                },
                {
                        new Booking("Ahmet", "Developer", 202, false,
                                new BookingDates("2024-02-01", "2024-02-10"), "Data Provider 2")
                },
                {
                        new Booking("Ayse", "Analyst", 303, true,
                                new BookingDates("2024-03-01", "2024-03-10"), "Data Provider 3")
                }
        };
    }

    /**
     * BU TESTİN AMACI: Request Body'yi Veri Sağlayıcıdan Beslemek
     *
     * Bu test, test mantığını (yeni bir rezervasyon oluşturma) ve test verisini
     * (farklı rezervasyon profilleri) birbirinden ayırır.
     *
     * YAKLAŞIM:
     * 1. @Test anotasyonu, 'bookingDataProvider' isimli veri sağlayıcıya bağlanır.
     * 2. Bu test metodu, DataProvider'dan gelen her bir veri seti için bir 'Booking'
     *    nesnesini parametre olarak alır.
     * 3. TestNG, "bookingDataProvider"daki her bir 'Booking' nesnesi için bu testi
     *    otomatik olarak bir kez çalıştırır.
     * 4. Her çalıştırmada, alınan 'Booking' nesnesi doğrudan .body() metoduna verilir
     *    ve Rest-Assured bu nesneyi otomatik olarak JSON'a çevirir.
     * 5. Dönen yanıt, teste girdi olarak gelen 'Booking' nesnesinin verilerine göre doğrulanır.
     */
    @Test(dataProvider = "bookingDataProvider")
    public void createBookingDataDrivenTest(Booking bookingRequest) {

        System.out.println("Çalışan Test Verisi -> İsim: " + bookingRequest.getFirstname() +
                ", Toplam Fiyat: " + bookingRequest.getTotalprice());

        given()
                .spec(spec)
                // Body olarak, DataProvider'dan gelen POJO nesnesini doğrudan kullanıyoruz.
                .body(bookingRequest)
        .when()
                .post("/booking")
        .then()
                .statusCode(200)
                .body("bookingid", notNullValue())
                // Doğrulamaları, teste parametre olarak gelen 'bookingRequest' nesnesinin
                // verilerini kullanarak dinamik olarak yapıyoruz.
                .body("booking.firstname", equalTo(bookingRequest.getFirstname()))
                .body("booking.lastname", equalTo(bookingRequest.getLastname()))
                .body("booking.totalprice", equalTo(bookingRequest.getTotalprice()))
                .body("booking.depositpaid", equalTo(bookingRequest.isDepositpaid()))
                .body("booking.bookingdates.checkin", equalTo(bookingRequest.getBookingdates().getCheckin()));

        System.out.println("--> " + bookingRequest.getFirstname() + " için rezervasyon oluşturma ve doğrulama BAŞARILI");
    }
}
