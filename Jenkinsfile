def APP_MODULE = "App"
pipeline {
    agent any

    stages {
        stage('Stash source code') {
            steps {
                stash includes: '**', name: 'source-code', useDefaultExcludes: false
            }
        }
        stage('Run Tests') {
            parallel {
                stage("Build") {
                    stages {
                        stage('Run cucumber') {
                            steps {
                                sh 'run-test.sh chrome 3'
                            }
                            post {
                                always {
                                    archiveArtifacts artifacts: "${APP_MODULE}/target/cucumber-reports/,${APP_MODULE}/target/screenshots/,${APP_MODULE}/target/GitHubReport.json"
                                    junit "${APP_MODULE}/target/cucumber-reports/*.xml"
                                    cucumber fileIncludePattern: "${APP_MODULE}/target/cucumber-reports/*.json", sortingMethod: 'ALPHABETICAL'
                                    stash includes: "${APP_MODULE}/target/GitHubReport.json", name: 'cucumber-report'
                                }

                                success {
                                    echo "Test succeeded"
                                }
                                failure {
                                    echo "Test failed"
                                }
                            }
                        }
                        stage('Export reports') {
                            when {
                                not {
                                    environment name: 'CHANGE_ID', value: ''
                                }
                            }
                            agent {
                                docker {
                                    image 'at/reporting:latest'
                                    args '-v $HOME/vendor/bundle:/vendor/bundle'
                                }
                            }
                            options { skipDefaultCheckout() }
                            steps("Install gems") {
                                unstash('source-code')
                                unstash('cucumber-report')
                                sh "bundle install --path /vendor/bundle"
                            }
                            post {
                                success {
                                    sh "bundle exec danger --danger_id=cucumber_report --dangerfile=CucumberReport.Dangerfile"
                                }
                            }
                        }
                    }
                }

                stage('Validate code') {
                    when {
                        not {
                            environment name: 'CHANGE_ID', value: ''
                        }
                    }
                    stages {
                        stage('Validate') {
                            steps {
                                sh "mvn install -DskipTestse"
                                sh "mvn validate"
                               step( [
                                        $class               : 'CucumberReportPublisher',
                                        classifications      : runClassifications,
                                        failedFeaturesNumber : 0,
                                        failedScenariosNumber: 0,
                                        failedStepsNumber    : 0,
                                        fileExcludePattern   : '',
                                        fileIncludePattern   : '**/*.json',
                                        jsonReportDirectory  : '**/cucumber-reports',
                                        parallelTesting      : true,
                                        pendingStepsNumber   : 0,
                                        skippedStepsNumber   : 0,
                                        trendsLimit          : 0,
                                        undefinedStepsNumber : 0
                                ])
                            }
                            post {
                                success {
                                    stash includes: "${APP_MODULE}/target/checkstyle.xml", name: 'checkstyle'
                                }
                            }
                        }
                        stage('Reporting') {
                            agent {
                                docker {
                                    image 'at/reporting:latest'
                                    args '-v $HOME/vendor/bundle:/vendor/bundle'
                                }
                            }
                            options { skipDefaultCheckout() }
                            steps("Preparing source code & Installing gems") {
                                unstash('source-code')
                                unstash('checkstyle')
                                sh "gem -v"
                                sh "bundle install --path /vendor/bundle"
                            }
                            post {
                                success {
                                    sh "bundle exec danger --danger_id=check_style --dangerfile=Dangerfile"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
import net.masterthought.jenkins.CucumberReportPublisher.Classification

HashMap<String, String> getClassificationsFromFile() {
    HashMap<String, String> classifications = new HashMap<>()
    classifications.add(new Classification("aaaa", "aaaa"))
    classifications.add(new Classification("bbbb", "bbbb"))
    classifications.add(new Classification("cccc", "cccc"))
    return classifications
}
