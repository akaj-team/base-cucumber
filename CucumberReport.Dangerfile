# Read and show report
require 'json'

if(File.file?(reportJsonFile))
  file = File.read(reportJsonFile)
  data = JSON.parse(file)
  messages = "||Feature|Scenarios|Steps|\n"
  messages << "|---|---|---|---|\n"
  messages << "|Total|#{file['features']['totalFeatures']}|#{file['scenarios']['totalScenarios']}|#{file['steps']['totalSteps']}|\n"
  messages << "|Passed|#{file['features']['featurePassed']}|#{file['scenarios']['scenarioPassed']}|#{file['steps']['stepPassed']}|\n"
  messages << "|Failed|#{file['features']['featureFailed']}|#{file['scenarios']['scenarioFailed']}|#{file['steps']['stepFailed']}|\n"
  messages << "|Skipped|0|0|#{file['steps']['stepSkipped']}|\n"
  messages << "|Pending|0|0|#{file['steps']['stepPending']}|\n"
  messages << "|Undefined|0|0|#{file['steps']['stepUndefined']}|\n"
  markdown messages
else
  warn("Can not find json report file: #{reportJsonFile}")
end
