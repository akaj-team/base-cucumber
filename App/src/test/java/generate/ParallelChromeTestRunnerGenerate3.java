package generate;
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
                "junit:target/cucumber-reports/junit-report3.xml",
                "html:target/cucumber-reports/cucumber-pretty",
                "json:target/cucumber-reports/ChromeCucumberTestReport3.json",
                "rerun:target/cucumber-reports/rerun.txt"
        })
class ParallelChromeTestRunnerGenerate3 extends ParallelCucumberRunnerBase {
}