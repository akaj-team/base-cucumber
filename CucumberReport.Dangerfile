# Read and show report
require 'json'

message "Cucumber report"
reportJsonFile = "target/GitHubReport.json"
if(File.file?(reportJsonFile))
      file = File.read(reportJsonFile)
      data = JSON.parse(file)
      messages = "||Features|Scenarios|Steps|Duration|\n"
      messages << "|---|---|---|---|---|\n"
      messages << "|Total|#{data['features']['totalFeatures']}|#{data['scenarios']['totalScenarios']}|#{data['steps']['totalSteps']}|#{data['durations']['totalDuration']}|\n"
      messages << "|Passed|#{data['features']['featurePassed']}|#{data['scenarios']['scenarioPassed']}|#{data['steps']['stepPassed']}||\n"
      messages << "|Failed|#{data['features']['featureFailed']}|#{data['scenarios']['scenarioFailed']}|#{data['steps']['stepFailed']}||\n"
      messages << "|Skipped|||#{data['steps']['stepSkipped']}||\n"
      messages << "|Pending|||#{data['steps']['stepPending']}||\n"
      messages << "|Undefined|||#{data['steps']['stepUndefined']}||\n"
    markdown messages
else
    warn("Can not find json report file: #{reportJsonFile}")
end