pipeline {
    agent any

    environment {
        DOCKER_TAG = "${env.BUILD_NUMBER}"
        DOCKER_IMAGE = "ilyalisov/qaprotours:${DOCKER_TAG}"
    }

    stages {
        stage('Checkout') {
            steps {
                sh "rm -dfr github"
                sh "mkdir github"
                sh "cd github"
                git branch: 'main', url: 'https://github.com/ilisau/qaprotours.git'
            }
        }

        stage('Build image') {
            steps {
                sh "docker image prune -f"
                sh "docker build -t ${DOCKER_IMAGE} -t ilyalisov/qaprotours:latest ."
            }
        }

        stage('Push image') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'DOCKERHUB', passwordVariable: 'DOCKERHUB_PASSWORD', usernameVariable: 'DOCKERHUB_USERNAME')]) {
                    sh "docker login -u ${DOCKERHUB_USERNAME} -p ${DOCKERHUB_PASSWORD}"
                    sh "docker push ${DOCKER_IMAGE}"
                    sh "docker push ilyalisov/qaprotours:latest"
                }
            }
        }

        stage('Update cluster') {
            steps {
                sh "kubectl rollout restart deployment qaprotours"
            }
        }
    }
}