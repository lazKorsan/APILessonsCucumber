package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestHooks {

    private static final Logger logger = LogManager.getLogger(TestHooks.class);

    @Before
    public void setUp(Scenario scenario) {
        logger.info("=== Test Senaryosu Başlıyor: {} ===", scenario.getName());
        logger.info("Tags: {}", scenario.getSourceTagNames());

        // RestAssured base configuration
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Before("@api")
    public void setupApiTests() {
        logger.info("API Testleri için setup yapılıyor...");
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }

    @Before("@schema")
    public void setupSchemaTests() {
        logger.info("Schema Validasyon Testleri başlıyor...");
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            logger.error("=== Test Senaryosu BAŞARISIZ: {} ===", scenario.getName());
            // Screenshot veya log kaydı eklenebilir
        } else {
            logger.info("=== Test Senaryosu BAŞARILI: {} ===", scenario.getName());
        }
    }

    @After("@cleanup")
    public void cleanupTestData() {
        logger.info("Test dataları temizleniyor...");
        // Test sonrası cleanup işlemleri
    }
}