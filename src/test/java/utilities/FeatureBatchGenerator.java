package utilities;

import utilities.AdvancedFeatureTemplate;

import java.util.Map;

public class FeatureBatchGenerator {

    public static void generateAllFeatures() {
        // Login feature
        FeatureTemplate.generateFeature("Login", Map.of(
                "Successful login", "LoginTest::testSuccess",
                "Invalid credentials", "LoginTest::testInvalid",
                "Empty credentials", "LoginTest::testEmpty"
        ));

        // Booking feature
        AdvancedFeatureTemplate.generateFeature(
                "Booking", "Rezervasyon işlemleri", Map.of(
                        "Create booking", "BookingTest::testCreate",
                        "Cancel booking", "BookingTest::testCancel",
                        "Update booking", "BookingTest::testUpdate"
                ), "CRUD"
        );

        // API feature
        AdvancedFeatureTemplate.generateFeature(
                "PaymentAPI", "Ödeme API testleri", Map.of(
                        "Process payment", "PaymentTest::testProcess",
                        "Refund payment", "PaymentTest::testRefund"
                ), "API"
        );
    }

    // Main method - tek seferde tüm feature'ları üret
    public static void main(String[] args) {
        generateAllFeatures();
        System.out.println("🎉 Tüm feature dosyaları oluşturuldu!");
    }
}