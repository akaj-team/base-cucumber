import cucumber.api.CucumberOptions;
import at.core.CucumberRunnerBase;

/**
 * DefaultTestRunner
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"app/stepdefs"},
        tags = {"not @Ignore"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-pretty",
                "json:target/cucumber-reports/CucumberTestReport.json",
                "rerun:target/cucumber-reports/rerun.txt"
        })
class DefaultTestRunner extends CucumberRunnerBase {

}
