# Read and show report
require 'json'

reportJsonFile = "target/GitHubReport.json"
if(File.file?(reportJsonFile))
      file = File.read(reportJsonFile)
      data = JSON.parse(file)
      messages = "||Features|Scenarios|Steps|\n"
      messages << "|---|---|---|---|\n"
      messages << "|Total|#{data['features']['totalFeatures']}|#{data['scenarios']['totalScenarios']}|#{data['steps']['totalSteps']}|\n"
      messages << "|Passed|#{data['features']['featurePassed']}|#{data['scenarios']['scenarioPassed']}|#{data['steps']['stepPassed']}|\n"
      messages << "|Failed|#{data['features']['featureFailed']}|#{data['scenarios']['scenarioFailed']}|#{data['steps']['stepFailed']}|\n"
      messages << "|Skipped|0|0|#{data['steps']['stepSkipped']}|\n"
      messages << "|Pending|0|0|#{data['steps']['stepPending']}|\n"
      messages << "|Undefined|0|0|#{data['steps']['stepUndefined']}|\n"
    markdown messages
else
    puts "Can not find json report file: #{reportJsonFile}"
end