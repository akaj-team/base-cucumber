pipeline {

    agent {
        docker {
           image 'maven:3-alpine'
                args '-v /root/.m2:/root/.m2'
        }
    }

    steps {

        stage('Build Test') {
            echo "Test abc"

            parallel(
                a: {
                    // sh 'run-test.sh chrome 3'
                    echo "Step A"
                },
                b: {
                    sh 'mvn validate'
                }
            )
        }
    }

//    stages {
//        stage('Build') {
//            steps {
//                sh 'run-test.sh chrome 3'
//            }
//        }
//    }

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
