pipeline {
    agent any

    stages {
        stage('Stash source code') {
            steps {
                stash includes: '**', name: 'source-code'
            }
        }
        stage('Run Tests') {
            parallel {
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
                        }
                        failure {
                            echo "Test failed"
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
                            options { skipDefaultCheckout() }
                            steps("Preparing source code & Installing gems") {
                                unstash('source-code')
                                unstash('checkstyle')
                                sh "gem -v"
                                sh "bundle install --path /vendor/bundle"
                            }
                            post {
                                success {
                                    sh "bundle exec danger"
                                }
                            }
                        }
                    }

                }
            }

        }

    }
}
