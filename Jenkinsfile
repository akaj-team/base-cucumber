pipeline {
    agent any
//    agent {
//        docker {
//            image 'maven:3-alpine'
//        }
//    }

    stages {

        stage('Install bundle') {
            agent {
                docker {
                    image 'ruby:2.6.1-alpine3.8'
                }
            }
            // https://stackoverflow.com/questions/45142855/bin-sh-apt-get-not-found
            steps("Install bundle & danger") {
//                sh 'apk update'
//                sh 'apk add libgmp3-dev'
                sh 'apt-get install libgmp3-dev'
                sh 'gem install bundler'
                sh 'bundle install'
                sh 'bundle update danger'
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
                        // always {
                        //    archiveArtifacts artifacts: 'target/**'
                        //    cucumber fileIncludePattern: 'target/cucumber-reports/*.json', sortingMethod: 'ALPHABETICAL'
                        // }

                        success {
                            echo "Test succeeded"
                        }
                        failure {
                            echo "Test failed"
                        }
                    }
                }

                stage('Validate Code Convention') {
                    agent {
                        docker {
                            image 'maven:3-alpine'
                        }
                    }

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
}
