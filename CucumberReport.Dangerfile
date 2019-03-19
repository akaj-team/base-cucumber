# Read and show report
require 'json'

reportJsonFile = "App/target/GitHubReport.json"
if(File.file?(reportJsonFile))
      file = File.read(reportJsonFile)
      data = JSON.parse(file)
      if(data['features']['failedFeature']==0)
            messages = "||Passed|Failed|Skipped|Pending|Undefined|Total|\n"
            messages << "|:---:|:---:|:---:|:---:|:---:|:---:|:---:|\n"
            messages << "|Features|#{data['features']['passedFeature']}|#{data['features']['failedFeature']}|0|0|0|#{data['features']['totalFeatures']}|\n"
            messages << "|Scenarios|#{data['scenarios']['passedScenario']}|#{data['scenarios']['failedScenario']}|0|0|0|#{data['scenarios']['totalScenarios']}|\n"
            messages << "|Steps|#{data['steps']['passedStep']}|#{data['steps']['failedStep']}|#{data['steps']['skippedStep']}|#{data['steps']['pendingStep']}|#{data['steps']['undefinedStep']}|#{data['steps']['totalSteps']}|\n"
            messages << "|Duration|#{data['durations']['passedDuration']}|#{data['durations']['failedDuration']}|#{data['durations']['skippedDuration']}|#{data['durations']['pendingDuration']}|#{data['durations']['undefinedDuration']}|#{data['durations']['totalDuration']}|\n"
           markdown messages if data['features']['totalFeatures'] > 0
      else
          fail("Having some features failed.")
      end
else
    warn("Can not find json report file: #{reportJsonFile}")
end
