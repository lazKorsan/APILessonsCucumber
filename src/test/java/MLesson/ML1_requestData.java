package MLesson;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ML1_requestData {

    String contentType = "application/json; charset=utf-8";
    String title_Status = "HTTP/1.1 200 OK";

    String resFirstName = "firstname";
    String resLastName = "lastname";
    String resTotalPirice = "totalprice";

    String resDepositPaid= "depositpaid";

    String resBookingDates ="bookingdates";

    String resChickIn = "checkin";
    String resCheckout = "checkout";


       /*
        Tüm api testleri 4 aşamada gerçekleşir
            1-Endpoint ve varsa request Body(PUT-POST-PACTH) Hazırlama
            2-Soruda bize verilmiş ise expectedbody hazırlama
            3-Response kayıt altına alma
            4-Assertion işlemleri
         */
     /*
        https://restful-booker.herokuapp.com/booking/10 url’ine
                bir GET request gonderdigimizde donen Response’un,
 	            status code’unun 200,
	            ve content type’inin application/json; charset=utf-8,
	            ve Server isimli Header’in degerinin Cowboy,
	            ve status Line’in HTTP/1.1 200 OK
	            ve response suresinin 5 sn’den kisa oldugunu manuel olarak test ediniz.

      */

    @Test
    public void TC_01(){

        String url = "https://restful-booker.herokuapp.com/booking/10";

        Response response = given().when().get(url);

        response.prettyPrint(); //Sorguda dönen cevabın içeriğini yazdırır
        response.prettyPeek(); //Sorgunun tüm bilgileri ile içeriğini yazdırır

        response.then().assertThat().statusCode(200).contentType(contentType)
                .statusLine(title_Status).header("Server", "Heroku");

    }

}
