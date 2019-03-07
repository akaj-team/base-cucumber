# github comment settings
github.dismiss_out_of_range_messages

# Warn when there is a big PR
warn("Big PR, try to keep changes smaller if you can") if git.lines_of_code > 500

#checkstyle_format.base_path = (Dir.pwd).gsub(/@.*/,'')

# Report
#checkstyle_format.report 'target/checkstyle.xml'

# Read and show report
message 'Begin'
reportJsonFile = 'target/GitHubReport.json'
message 'end'
if(File.file?(reportJsonFile))
    file1 = File.read(reportJsonFile)
    file = JSON.parse(file1)

    puts "------------------------------------------------"
    puts "|           |  Features  |  Scenarios  |   Steps  |"
    puts "------------------------------------------------"
    puts "|  Total    |      #{file['features']['totalFeatures']}     |       #{file['scenarios']['totalScenarios']}     |    #{file['steps']['totalSteps']}    |"
    puts "------------------------------------------------"
    puts "|  Passed   |      #{file['features']['featurePassed']}     |      #{file['scenarios']['scenarioPassed']}      |    #{file['steps']['stepPassed']}    |"
    puts "------------------------------------------------"
    puts "|  Failed   |      #{file['features']['featureFailed']}     |      #{file['scenarios']['scenarioFailed']}      |    #{file['steps']['stepFailed']}     |"
    puts "------------------------------------------------"
    puts "|  Skipped  |      0     |      0      |    #{file['steps']['stepSkipped']}     |"
    puts "------------------------------------------------"
    puts "|  Pending  |      0     |      0      |    #{file['steps']['stepPending']}     |"
    puts "------------------------------------------------"
    puts "| Undefined |      0     |      0      |    #{file['steps']['stepUndefined']}     |"

else
    puts "Can not find json report file: #{reportJsonFile}"
end
