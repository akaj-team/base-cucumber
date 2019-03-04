pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            image 'ruby:2.6.0-alpine3.8'
        }
    }

    stages {
        stage('Install bundle') {
            steps("Install bundle") {
                sh "gem install bundler"

            }
        }

        stage('Install danger') {
            steps("Install danger") {
                sh "sh bundle install"
            }
        }

        stage('Install danger') {
            steps("Install danger") {
                sh "bundle update danger"
            }
        }

        stage('Build') {
            parallel {
                stage('Run Test') {
                    // steps {
                    //     sh 'run-test.sh chrome 3'
                    // }
                    steps {
                        echo "Step A"
                    }

                    post {
                        always {
                            archiveArtifacts artifacts: 'target/**'
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

                stage('Validate Code Convention') {
                    steps {
                        sh 'mvn validate'
                    }

                    post {
                        always {
                            echo "Post Convention Report"
                            sh 'bundle exec danger'
                        }

                        success {
                            echo "Post Convention Report"
                            sh 'bundle exec danger'
                            echo "Test succeeded"
                        }
                        failure {
                            echo "Test failed"
                        }
                    }
                }
            }
        }
    }

//    post {
//        always {
//            archiveArtifacts artifacts: 'target/**'
//            cucumber fileIncludePattern: 'target/cucumber-reports/*.json', sortingMethod: 'ALPHABETICAL'
//        }
//
//        success {
//            echo "Test succeeded"
//        }
//        failure {
//            echo "Test failed"
//        }
//    }
}
