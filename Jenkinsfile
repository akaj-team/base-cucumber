pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'run-test.sh chrome 3'
            }
        }
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
