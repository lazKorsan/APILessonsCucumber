package utilities;

import java.util.*;

public class BatchStepGenerator {

    public static void main(String[] args) {
        System.out.println("🚀 Step Definitions Generator Başlıyor...\n");

        generateAllStepDefinitions();

        System.out.println("\n🎉 Tüm Step Definitions başarıyla oluşturuldu!");
        System.out.println("📁 Dosyalar: src/test/java/stepdefinitions/ klasöründe");
    }

    public static void generateAllStepDefinitions() {

        // 1. LOGIN STEP DEFINITIONS
        System.out.println("🔧 LoginSteps oluşturuluyor...");
        StepDefinitionsGenerator.generateStepDefinitions("LoginSteps",
                Map.of(
                        "Kullanıcı login sayfasında", "navigateToLoginPage",
                        "Geçerli bilgilerle giriş yapar", "performLoginWithValidCredentials",
                        "Geçersiz bilgilerle giriş yapar", "performLoginWithInvalidCredentials",
                        "Anasayfaya yönlendirilir", "verifyHomePageRedirect",
                        "Hata mesajı görüntülenir", "verifyErrorMessageDisplayed"
                )
        );

        StepDefinitionsGenerator.generateHelperClass("LoginHelper",
                Map.of(
                        "navigateToLoginPage", "Login sayfasına navigasyon",
                        "performLoginWithValidCredentials", "Geçerli kullanıcı ile giriş",
                        "performLoginWithInvalidCredentials", "Geçersiz kullanıcı ile giriş",
                        "verifyHomePageRedirect", "Anasayfa yönlendirme kontrolü",
                        "verifyErrorMessageDisplayed", "Hata mesajı kontrolü"
                )
        );

        // 2. API STEP DEFINITIONS
        System.out.println("🔧 APISteps oluşturuluyor...");
        StepDefinitionsGenerator.generateStepDefinitions("APISteps",
                Map.of(
                        "API endpoint {string} hazırlanır", "setupAPIEndpoint",
                        "{string} endpoint'ine {string} request gönderilir", "sendAPIRequest",
                        "Status kod {int} olduğu doğrulanır", "verifyStatusCode",
                        "Response content type {string} olmalıdır", "verifyContentType",
                        "Response body JSON schema'ya uygun olmalıdır", "verifyJSONSchema",
                        "Response body'de {string} alanı {string} değerine sahip olmalı", "verifyResponseField"
                )
        );

        StepDefinitionsGenerator.generateHelperClass("APIHelper",
                Map.of(
                        "setupAPIEndpoint", "API endpoint konfigürasyonu",
                        "sendAPIRequest", "API isteği gönderimi",
                        "verifyStatusCode", "Status kodu doğrulama",
                        "verifyContentType", "Content type doğrulama",
                        "verifyJSONSchema", "JSON schema validasyonu",
                        "verifyResponseField", "Response alanı doğrulama"
                )
        );

        // 3. COMMON STEP DEFINITIONS
        System.out.println("🔧 CommonSteps oluşturuluyor...");
        StepDefinitionsGenerator.generateStepDefinitions("CommonSteps",
                Map.of(
                        "Test verileri hazırlanır", "prepareTestData",
                        "Veritabanı bağlantısı açılır", "openDatabaseConnection",
                        "Veritabanı temizlenir", "cleanDatabase",
                        "Browser kapatılır", "closeBrowser",
                        "Ekran görüntüsü alınır", "takeScreenshot"
                )
        );

        StepDefinitionsGenerator.generateHelperClass("CommonHelper",
                Map.of(
                        "prepareTestData", "Test verileri hazırlama",
                        "openDatabaseConnection", "DB bağlantısı açma",
                        "cleanDatabase", "Veritabanı temizleme",
                        "closeBrowser", "Browser kapatma",
                        "takeScreenshot", "Ekran görüntüsü alma"
                )
        );

        // 4. BOOKING STEP DEFINITIONS
        System.out.println("🔧 BookingSteps oluşturuluyor...");
        StepDefinitionsGenerator.generateStepDefinitions("BookingSteps",
                Map.of(
                        "Yeni rezervasyon formu açılır", "openBookingForm",
                        "Rezervasyon bilgileri doldurulur", "fillBookingDetails",
                        "Rezervasyon oluşturulur", "createBooking",
                        "Rezervasyon başarı mesajı görüntülenir", "verifyBookingSuccessMessage",
                        "Rezervasyon listesinde görüntülenir", "verifyBookingInList"
                )
        );

        StepDefinitionsGenerator.generateHelperClass("BookingHelper",
                Map.of(
                        "openBookingForm", "Rezervasyon formu açma",
                        "fillBookingDetails", "Rezervasyon bilgilerini doldurma",
                        "createBooking", "Rezervasyon oluşturma",
                        "verifyBookingSuccessMessage", "Başarı mesajı kontrolü",
                        "verifyBookingInList", "Rezervasyon listesi kontrolü"
                )
        );
    }
}