pipeline {
    agent any
    
    stages {
        stage('Check') {            
            agent {
                docker {
                    image 'at/android-check'
                    args  '-v $HOME/.gradle:/.gradle:rw'
                    }
            }          
            steps {
                sh 'rm -f env.list'
                sh 'env | grep "GIT\\|NODE_\\|STAGE\\|BUILD\\|JOB_NAME\\|ghprbPullId\\|CHANGE_ID" > env.list'
                sh './gradlew clean :app:check || exit 1 && \
                    bundle exec danger'
            }      
        }
     }

    post {
        always {
            echo 'Archive artifact'
            archiveArtifacts artifacts: 'app/build/reports/**'

            echo 'Delete Workspace.'
            sh 'sudo chown -R jenkins.jenkins .'
            sh 'chmod -R +w .'
            deleteDir()

        }
        success {
            echo 'build is success!!!'
        }
        failure {
            echo 'build is failure!!!'
            // TODO something
        }
    }
}
