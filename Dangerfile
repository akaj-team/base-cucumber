# github comment settings
github.dismiss_out_of_range_messages

# Warn when there is a big PR
warn("Big PR, try to keep changes smaller if you can") if git.lines_of_code > 500

#checkstyle_format.base_path = (Dir.pwd).gsub(/@.*/,'')

# Report
#checkstyle_format.report 'target/checkstyle.xml'

# Read and show report
require 'json'
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
