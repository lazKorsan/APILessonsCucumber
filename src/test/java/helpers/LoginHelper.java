package helpers;

import org.junit.Assert;
import static org.hamcrest.Matchers.*;

public class LoginHelper {

    public void performLoginWithValidCredentials() {
        // Geçerli kullanıcı ile giriş
        System.out.println("performLoginWithValidCredentials executed");
    }
    public void verifyHomePageRedirect() {
        // Anasayfa yönlendirme kontrolü
        System.out.println("verifyHomePageRedirect executed");
    }
    public void navigateToLoginPage() {
        // Login sayfasına navigasyon
        System.out.println("navigateToLoginPage executed");
    }
    public void performLoginWithInvalidCredentials() {
        // Geçersiz kullanıcı ile giriş
        System.out.println("performLoginWithInvalidCredentials executed");
    }
    public void verifyErrorMessageDisplayed() {
        // Hata mesajı kontrolü
        System.out.println("verifyErrorMessageDisplayed executed");
    }
}