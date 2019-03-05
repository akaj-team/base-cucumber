pipeline {
    agent any

    stages {
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
                        success {
                            echo "Validate succeeded"
                        }
                        failure {
                            echo "Validate failed"
                        }
                    }
                }
            }
        }
        stages('Report To Github') {
            stage('Install bundle') {
                agent {
                    docker {
                        image 'ruby:2.6.1-alpine3.8'
                        args '-v $HOME/vendor/bundle:/vendor/bundle'
                    }
                }
                // https://stackoverflow.com/questions/45142855/bin-sh-apt-get-not-found
                // Docker run a docker file [follow case each [environment]
                // https://docs.docker.com/engine/reference/builder/
                steps("Install bundle & danger") {
                    sh 'gem -v'
                    sh 'apk add libgcrypt-dev make gcc libc-dev git'
                    sh 'gem install bundle --no-document -- --use-system-libraries'
                    sh 'bundle install --path /vendor/bundle'
                }

                steps('Report To Github') {
                    sh 'pwd'
                    sh 'find ./'
                    sh 'bundle exec danger'
                }

                post {
                    success {
                        echo "Report succeeded"
                    }
                    failure {
                        echo "Report failed"
                    }
                }
            }
        }
    }
}
