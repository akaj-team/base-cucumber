import cucumber.api.CucumberOptions;
import at.core.ParallelCucumberRunnerBase;

/**
 * Do not change this file
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefs"},
        tags = {"not @Ignore"},
        plugin = {
                "pretty",
                "junit:target/cucumber-reports/junit-report.xml",
                "html:target/cucumber-reports/cucumber-pretty",
                "json:target/cucumber-reports/CucumberTestReport.json",
                "rerun:target/cucumber-reports/rerun.txt"
        })
class ParallelTestRunner extends ParallelCucumberRunnerBase {
}
