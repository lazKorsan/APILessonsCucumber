package learningAPI.pojos;

import utilities.AdvancedFeatureTemplate;
import utilities.FeatureTemplate;

import java.beans.FeatureDescriptor;
import java.util.Map;

public class otomatikFeatureOlusturma {

    static {
        AdvancedFeatureTemplate.generateFeature(
                "BookingSchemaValidation",
                "API response'larının JSON Schema'ya uygun olduğunu doğrulama",
                java.util.Map.of(
                        "GET booking endpoint response schema validasyonu", "getBookingSchemaValidation",
                        "Zorunlu alanların schema validasyonu", "requiredFieldsSchemaValidation",
                        "Response data type validasyonu", "responseDataTypeValidation",
                        "Bookingdates nested object schema validasyonu", "bookingdatesNestedObjectSchemaValidation",
                        "Geçersiz booking ID için schema validasyonu", "invalidBookingIdSchemaValidation",
                        "POST booking request schema validasyonu", "postBookingSchemaValidation",
                        "PUT booking request ve response schema validasyonu", "putBookingSchemaValidation"
                ),
                "API");

        FeatureTemplate.generateFeature("LoginTests", Map.of(
                "Successful login", "LoginTest::testSuccessfulLogin",
                "Invalid login", "LoginTest::testInvalidLogin",
                "Locked user login", "LoginTest::testLockedUserLogin"
        ));


        // Template-based
        AdvancedFeatureTemplate.generateFeature(
                "UserManagement",                    // featureName
                "Kullanıcı yönetimi işlemleri",     // description
                Map.of(
                        "Create new user", "UserTest::testCreateUser",
                        "Update user info", "UserTest::testUpdateUser",
                        "Delete user", "UserTest::testDeleteUser",
                        "Search users", "UserTest::testSearchUsers"
                ),
                "CRUD"                              // templateType
        );


        AdvancedFeatureTemplate.generateFeature(
                "BookingAPI",
                "Rezervasyon API testleri",
                Map.of(
                        "Create booking", "BookingAPITest::testCreateBooking",
                        "Get booking by ID", "BookingAPITest::testGetBooking",
                        "Update booking", "BookingAPITest::testUpdateBooking",
                        "Delete booking", "BookingAPITest::testDeleteBooking"
                ),
                "API"  // API template kullan
        );


    }
}
