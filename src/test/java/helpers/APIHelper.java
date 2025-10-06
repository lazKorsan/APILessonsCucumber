package helpers;

import org.junit.Assert;
import static org.hamcrest.Matchers.*;

public class APIHelper {

    public void verifyJSONSchema() {
        // JSON schema validasyonu
        System.out.println("verifyJSONSchema executed");
    }
    public void sendAPIRequest() {
        // API isteği gönderimi
        System.out.println("sendAPIRequest executed");
    }
    public void setupAPIEndpoint() {
        // API endpoint konfigürasyonu
        System.out.println("setupAPIEndpoint executed");
    }
    public void verifyContentType() {
        // Content type doğrulama
        System.out.println("verifyContentType executed");
    }
    public void verifyStatusCode() {
        // Status kodu doğrulama
        System.out.println("verifyStatusCode executed");
    }
    public void verifyResponseField() {
        // Response alanı doğrulama
        System.out.println("verifyResponseField executed");
    }
}