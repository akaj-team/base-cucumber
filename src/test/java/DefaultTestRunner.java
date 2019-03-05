import cucumber.api.CucumberOptions;
import vn.asiantech.core.CucumberRunnerBase;

/**
 * DefaultTestRunner
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefs"},
        tags = {"not @Ignore"},
        plugin = {
                "pretty",
                "usage:target/cucumber-usage.json",
                "html:target/cucumber-reports/cucumber-pretty",
                "json:target/cucumber-reports/CucumberTestReport.json",
                "rerun:target/cucumber-reports/rerun.txt"
        })
class DefaultTestRunner extends CucumberRunnerBase {

}
