pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
        jdk 'JDK-17'
    }

    environment {
        APP_NAME = 'student-management'
        APP_VERSION = '1.0.0'
        DOCKER_IMAGE = "${APP_NAME}:${APP_VERSION}"
        OPENSHIFT_PROJECT = 'student-management-project'
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Duke bere pull nga Git...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Duke ndertuar projektin me Maven...'
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                echo 'Duke ekzekutuar unit tests...'
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                echo 'Duke gjeneruar artifact (.jar)...'
                sh 'mvn package -DskipTests'
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Duke ndertuar Docker image...'
                sh "docker build -t ${DOCKER_IMAGE} ./web"
            }
        }

        stage('Deploy to OpenShift') {
            steps {
                echo 'Duke bere deploy ne OpenShift...'
                sh """
                    oc project ${OPENSHIFT_PROJECT} || oc new-project ${OPENSHIFT_PROJECT}
                    oc apply -f openshift/deployment.yaml
                    oc apply -f openshift/service.yaml
                    oc apply -f openshift/route.yaml
                    oc rollout status deployment/${APP_NAME}
                """
            }
        }
    }

    post {
        success {
            echo 'Pipeline u ekzekutua me sukses!'
        }
        failure {
            echo 'Pipeline deshtoi. Kontrolloni logjet.'
        }
    }
}
