package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefinitions", "hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/smoke-test.html",
                "json:target/cucumber-reports/smoke-test.json"
        },
        tags = "@smoke and @schema"  // Sadece smoke ve schema tag'ine sahip testler
)
public class SmokeTestRunner {
}