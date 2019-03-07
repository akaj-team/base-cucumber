pipeline {
    agent any

    stages {
        stage('Run Tests') {

            parallel {
                stage('Run cucumber') {
                    steps {
                        sh 'run-test.sh chrome 3'
                    }
                    post {
                        always {
                            archiveArtifacts artifacts: 'target/**'
                            cucumber fileIncludePattern: 'target/cucumber-reports/*.json', sortingMethod: 'ALPHABETICAL'
                        }

                        success {
                            echo "Test succeeded"
                            sh "bundle exec danger"
                        }
                    }
                    failure {
                        echo "Test failed"
                    }
                }
            }
            stage('Validate code') {
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
