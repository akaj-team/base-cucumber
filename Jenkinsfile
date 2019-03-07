pipeline {
    agent any

    stages {
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
                                    archiveArtifacts artifacts: 'target/**'
                                    junit 'target/cucumber-reports/*.xml'
                                    cucumber fileIncludePattern: 'target/cucumber-reports/*.json', sortingMethod: 'ALPHABETICAL'
                                }

                                success {
                                    echo "Test succeeded"
                                    stash includes: 'target/GitHubReport.json', name: 'cucumber-report'
                                }
                                failure {
                                    echo "Test failed"
                                }
                            }
                        }
                        stage('Cucumber Report') {
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
                            steps("Install gems") {
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
                                sh "mvn validate"
                            }
                            post {
                                success {
                                    stash includes: 'target/checkstyle.xml', name: 'checkstyle'
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
                            steps("Install gems") {
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