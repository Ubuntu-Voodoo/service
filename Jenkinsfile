pipeline {
    agent any
    environment {
        DOCKER_PASSWORD = credentials("docker_password")
    }

    stages {
        stage('Build & Test') {
            steps {
                sh 'kubectl apply -f kubernetes/hello.yaml'
            }
        }
    }
}
