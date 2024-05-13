pipeline {
    agent any
    tools {
        maven 'maven_3_8_1'
    }
    stages {
        stage('Clean ws') {
            agent any
            steps {
                echo 'Cleaning WS stage...'
                cleanWs()
                echo "Cleaning workspace completed"
            }
        } 
         
        stage('Project Build') {
            steps {
                echo "Running build stage"
                checkout scmGit(branches: [[name: '*/develop']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/hswagh9/util-common-service']])
                bat 'mvn clean install'
                echo "Build stage completed"
            }
        }
        
        stage('Project Test') {
            agent any
            steps {
                echo "Running test stage"
                bat "mvn test"
                echo "Test stage completed"
            }
        }
        
        stage('Build Docker Image'){
            steps{
                script{
                    bat 'docker build -t hswagh9/util-common-service-docker-image .'
                }
            }
        }
        
        stage('Push Docker Image to DockerHub'){
		    steps{
		        script{
		            withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
		                bat "docker login -u hswagh9 -p ${dockerhubpwd}"
		                bat 'docker push hswagh9/util-common-service-docker-image'
		            }
				}
		    }
		}
    }
}

