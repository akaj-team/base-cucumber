# Read and show report
require 'json'
warn("Bla ble")
reportJsonFile = "target/GitHubReport.json"
if(File.file?(reportJsonFile))
   file = File.read(reportJsonFile)
   message "------------------------------------------------"
   message "|           |  Features  |  Scenarios  |   Steps  |"
   message "------------------------------------------------"
   message "|  Total    |      #{file['features']['totalFeatures']}     |       #{file['scenarios']['totalScenarios']}     |    #{file['steps']['totalSteps']}    |"
   message "------------------------------------------------"
   message "|  Passed   |      #{file['features']['featurePassed']}     |      #{file['scenarios']['scenarioPassed']}      |    #{file['steps']['stepPassed']}    |"
   message "------------------------------------------------"
   message "|  Failed   |      #{file['features']['featureFailed']}     |      #{file['scenarios']['scenarioFailed']}      |    #{file['steps']['stepFailed']}     |"
   message "------------------------------------------------"
   message "|  Skipped  |      0     |      0      |    #{file['steps']['stepSkipped']}     |"
   message "------------------------------------------------"
   message "|  Pending  |      0     |      0      |    #{file['steps']['stepPending']}     |"
   message "------------------------------------------------"
   message "| Undefined |      0     |      0      |    #{file['steps']['stepUndefined']}     |"
else
   message "Can not find json report file: #{reportJsonFile}"
end
