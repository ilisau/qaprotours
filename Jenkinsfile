pipeline {
    agent any

    environment {
        DOCKER_IMAGE_NAME = "qaprotours"
        DOCKER_REGISTRY_URL = "docker.io"
    }

    stages {
        stage('Build Docker Image') {
            steps {
                git url: 'https://github.com/ilisau/qaprotours.git', branch: 'main'
                sh "docker build -t $DOCKER_REGISTRY_URL/$DOCKER_IMAGE_NAME:${BUILD_NUMBER} ."
            }
        }
//         stage('Push Docker Image') {
//             steps {
//                 withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials',
//                 passwordVariable: 'DOCKERHUB_PASSWORD',
//                 usernameVariable: 'DOCKERHUB_USERNAME')]) {
//                     sh "docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD $DOCKER_REGISTRY_URL"
//                     sh "docker push $DOCKER_REGISTRY_URL/$DOCKER_IMAGE_NAME:${BUILD_NUMBER}"
//                 }
//             }
//         }
    }
}
