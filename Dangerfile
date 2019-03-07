# github comment settings
github.dismiss_out_of_range_messages

# Warn when there is a big PR
warn("Big PR, try to keep changes smaller if you can") if git.lines_of_code > 500

checkstyle_format.base_path = (Dir.pwd).gsub(/@.*/,'')

# Report
checkstyle_format.report 'target/checkstyle.xml'

# Read and show report
message = " | Features | Scenarios |Steps |\n"
message << "--- |--- | --- | --- |\n"
end
reportJsonFile = "target/GitHubReport.json"
if(File.file?(reportJsonFile))
    file = File.read(reportJsonFile)
    data = JSON.parse(file)
    col_labels = { features: "Features", scenario: "Scenarios", steps: "Steps" }
    puts "aaaaaaa"
else
    puts "Can not find json report file: #{reportJsonFile}"
end
