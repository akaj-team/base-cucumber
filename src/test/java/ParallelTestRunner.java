import cucumber.api.CucumberOptions;
import org.testng.annotations.Test;
import vn.asiantech.core.ParallelCucumberRunnerBase;

/**
 * Do not change this file
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefs"},
        tags = {"not @Ignore"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-pretty",
                "json:target/cucumber-reports/CucumberTestReport.json",
                "rerun:target/cucumber-reports/rerun.txt"
        })
class ParallelTestRunner extends ParallelCucumberRunnerBase {
}