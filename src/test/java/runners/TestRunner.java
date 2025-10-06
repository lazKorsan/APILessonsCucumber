package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",          // Feature dosyalarının yolu
        glue = {"stepdefinitions", "hooks"},               // Step definitions ve hooks package'ı
        plugin = {
                "pretty",                                       // Konsola renkli çıktı
                "html:target/cucumber-reports/cucumber.html",   // HTML rapor
                "json:target/cucumber-reports/cucumber.json",   // JSON rapor
                "junit:target/cucumber-reports/cucumber.xml",   // JUnit rapor
                "rerun:target/rerun.txt"                        // Fail olan testleri tekrar çalıştırmak için
        },
        monochrome = true,                                 // Konsol çıktısında renkleri kapatır
        dryRun = false,                                    // True yaparsanız sadece step'leri kontrol eder
        tags = "@schema-validation"                        // Sadece schema validation testlerini çalıştırır
)
public class TestRunner {
}