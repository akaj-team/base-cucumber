pipeline {
    /* Step Build */
    agent any

    environment {
                HOME = '/.gradle'
                GRADLE_CACHE = '/.gradle_cache'
            }

    stages {
        /* Step build images Check */
        stage('Build Check Image') {
            steps {
                sh 'env'
                sh 'rm -f env.list'
                sh 'env | grep "GIT\\|NODE_\\|STAGE\\|BUILD\\|JOB_NAME\\|ghprbPullId\\|CHANGE_ID" > env.list'
                sh 'cat env.list'
                sh 'make check'
            }
        }

        /* Step Check */
        stage('Check') {
            agent {
                docker {
                    image 'fr/android-check'
                    // Mount the Gradle cache in the container
                    args  '-v /Users/huong.nguyen/.gradle:/.gradle:rw'
                }
            }
    
            steps {
                // Copy the Gradle cache from the host, so we can write to it
                // sh "rsync -a --include /caches --include /wrapper --exclude '/*' ${GRADLE_CACHE}/ ${HOME} || true"
                // sh './gradlew clean :app:check'
                sh "./gradlew detekt"
            }

            post {
                success {
                    sh "bundle exec danger"
                    // Write updates to the Gradle cache back to the host
                    // sh "rsync -au ${HOME}/caches ${HOME}/wrapper ${GRADLE_CACHE}/ || true"
                }
            }
        }

        /* Step build images report */
        stage('Build Report Image') {
            steps {
                sh 'make report'
            }
            // post {
            //     success {
            //         sh "./gradlew detekt"
            //     }
            // }
        }
        
        // /* Step Reporting */
        // stage('Reporting') {
        //     agent {
        //         docker {
        //             image 'at/reporting:latest'
        //             args '-v $HOME/vendor/bundle:/vendor/bundle'
        //         }
        //     }
        //     // options { skipDefaultCheckout() }
        //     steps("Preparing source code & Installing gems") {
        //         sh "gem -v"
        //         sh "bundle install --path /vendor/bundle"
        //     }
        //     post {
        //         success {
        //             sh "bundle exec danger"
        //         }
        //     }
        // }
    }
    
    post {
        /* Step Build */
        always {
            echo 'Report check to jenkins!!!'
            deleteDir()
        }
        success {
            echo 'I succeeeded!'
        }
        unstable {
            echo 'I am unstable :/'
        }
        failure {
            echo 'I failed :('
        }
        changed {
            echo 'Things were different before...'
        }
    }
}