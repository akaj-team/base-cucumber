pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
        }
    }

    stages {
        stage('Build') {
            parallel {
                stage('Run Test') {
                    // steps {
                    //     sh 'run-test.sh chrome 3'
                    // }
                    echo "Step A"
                }

                stage('Validate convention') {
                    steps {
                        sh 'mvn validate'
                    }
                }
            }
        }
    }

    post {
        always {
            junit 'target/checkstyle.xml'
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
