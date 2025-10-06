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
                "html:target/cucumber-reports/regression-test.html",
                "json:target/cucumber-reports/regression-test.json"
        },
        tags = "not @wip and not @ignore"  // WIP ve ignore tag'li testler hariç
)
public class RegressionTestRunner {
}