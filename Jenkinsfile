pipeline {
    agent any
    environment {
        DOCKER_PASSWORD = credentials("docker_password")
    }

    stages {
        stage('Build & Test') {
            steps {
                sh './gradlew clean build'
            }
        }
         stage('Tag image') {
             steps {
                script {
                    GIT_TAG = sh([script: 'git fetch --tag && git tag', returnStdout: true]).trim()
                    MAJOR_VERSION = sh([script: 'git tag | cut -d . -f 1', returnStdout: true]).trim()
                    MINOR_VERSION = sh([script: 'git tag | cut -d . -f 2', returnStdout: true]).trim()
                    PATCH_VERSION = sh([script: 'git tag | cut -d . -f 3', returnStdout: true]).trim()
                    IMAGE_VERSION = $MAJOR_VERSION.$NEW_MINOR_VERSION.$PATCH_VERSION
                }
                sh "docker build -t renasoaie/hello-img:${MAJOR_VERSION}.\$((${MINOR_VERSION} + 1)).${PATCH_VERSION} ."
                sh "docker login docker.io -u renasoaie -p renasoaie"
                sh "docker push renasoaie/hello-img:$IMAGE_VERSION"
        }
    }
}
   
}
