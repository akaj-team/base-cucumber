pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            image 'ruby:2.6.1-alpine3.8'
        }
    }

    stages {
        stage('Install bundle') {
            steps("Install bundle") {
                sh "gem install bundle"
                sh "bundle install"
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
