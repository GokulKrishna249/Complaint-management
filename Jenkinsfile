pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk   'JDK'
    }

    stages {

        stage('Checkout') {
            steps {
                echo '--- Checking out source code ---'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo '--- Building with Maven ---'
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                echo '--- Running JUnit Tests ---'
                bat 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

    }

    post {
        success {
            echo 'BUILD SUCCESS - All tests passed!'
        }
        failure {
            echo 'BUILD FAILED - Check the logs above.'
        }
    }
}
