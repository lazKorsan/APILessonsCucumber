package helpers;

import org.junit.Assert;
import static org.hamcrest.Matchers.*;

public class CommonHelper {

    public void prepareTestData() {
        // Test verileri hazırlama
        System.out.println("prepareTestData executed");
    }
    public void cleanDatabase() {
        // Veritabanı temizleme
        System.out.println("cleanDatabase executed");
    }
    public void closeBrowser() {
        // Browser kapatma
        System.out.println("closeBrowser executed");
    }
    public void takeScreenshot() {
        // Ekran görüntüsü alma
        System.out.println("takeScreenshot executed");
    }
    public void openDatabaseConnection() {
        // DB bağlantısı açma
        System.out.println("openDatabaseConnection executed");
    }
}