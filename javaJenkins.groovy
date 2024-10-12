pipeline {
    agent any
    stages {
        stage('Clone Repository') {
            steps {
                git 'https://github.com/your-repo-url'
            }
        }
        stage('Build') {
            steps {
                sh 'javac HelloWorld.java'
            }
        }
        stage('Test') {
            steps {
                sh 'java HelloWorld'
            }
        }
        stage('Docker Build') {
            steps {
                script {
                    dockerImage = docker.build("my-java-app:${env.BUILD_NUMBER}")
                }
            }
        }
        stage('Push to Artifactory') {
            steps {
                script {
                    docker.withRegistry('https://artifactory.example.com', 'docker-registry-credentials') {
                        dockerImage.push()
                    }
                }
            }
        }
        stage('Deploy to Kubernetes') {
            steps {
                sh 'kubectl apply -f k8s-deployment.yaml'
            }
        }
    }
    post {
        success {
            echo 'Deployment successful!'
        }
        failure {
            echo 'Deployment failed!'
        }
    }
}
