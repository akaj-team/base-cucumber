# Read and show report
require 'json'

reportJsonFile = "target/GitHubReport.json"
if(File.file?(reportJsonFile))
      file = File.read(reportJsonFile)
      data = JSON.parse(file)
      messages = "||Passed|Failed|Skipped|Pending|Undefined|Total|\n"
      messages << "|---|---|---|---|---|---|---|\n"
      messages << "|Features|#{data['features']['featurePassed']}|#{data['features']['featureFailed']}|0|0|0|#{data['features']['totalFeatures']}|\n"
      messages << "|Scenarios|#{data['scenarios']['scenarioPassed']}|#{data['scenarios']['scenarioFailed']}|0|0|0|#{data['scenarios']['totalScenarios']}|\n"
      messages << "|Steps|#{data['steps']['stepPassed']}|#{data['steps']['stepFailed']}|#{data['steps']['stepSkipped']}|#{data['steps']['stepPending']}|#{data['steps']['stepUndefined']}|#{data['steps']['totalSteps']}|\n"
      messages << "|Duration||||||#{data['durations']['totalDuration']}|\n"
    markdown messages
else
    warn("Can not find json report file: #{reportJsonFile}")
end