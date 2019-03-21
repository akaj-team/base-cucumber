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
                                    archiveArtifacts artifacts: "${APP_MODULE}/target/cucumber-reports/,${APP_MODULE}/target/screenshots/,${APP_MODULE}/target/GitHubReport.json,${APP_MODULE}/target/browser.properties"
                                    junit "${APP_MODULE}/target/cucumber-reports/*.xml"
                                    script {
                                        def props = readProperties interpolate: true, file: "${APP_MODULE}/target/browser.properties"
                                        cucumber fileIncludePattern: "${APP_MODULE}/target/cucumber-reports/*.json",
                                                sortingMethod: 'ALPHABETICAL',
                                                classifications: [
                                                        ['key'  : 'Platform',
                                                         'value': props.Platform
                                                        ],
                                                        ['key'  : 'BrowserName',
                                                         'value': props.BrowserName
                                                        ],
                                                        ['key'  : 'BrowserVersion',
                                                         'value': props.BrowserVersion
                                                        ],
                                                        ['key'  : 'Server',
                                                         'value': props.Server
                                                        ]
                                                ]
                                    }
                                    stash includes: "${APP_MODULE}/target/GitHubReport.json", name: 'cucumber-report'
                                   // stash includes: "${APP_MODULE}/target/browser.properties", name: 'cucumber-properties'
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
                             //   unstash('cucumber-properties')
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
                                sh "mvn install -DskipTests"
                                sh "mvn validate"
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
