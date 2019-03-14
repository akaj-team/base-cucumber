import at.core.CucumberRunnerBase;
import cucumber.api.CucumberOptions;

/**
 * DefaultTestRunner
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
class DefaultTestRunner extends CucumberRunnerBase {

}
