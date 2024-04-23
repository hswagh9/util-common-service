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
            }
        } 
         
        stage('Build') {
            steps {
                echo "Running build stage"
                checkout scmGit(branches: [[name: '*/develop']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/hswagh9/util-common-service']])
                bat 'mvn clean install'
            }
        }
        
        stage('Test') {
            agent any
            steps {
                echo "Running test stage"
                bat "mvn test"
            }
        }
        stage('Build docker image'){
            steps{
                script{
                    bat 'docker build -t hswagh9/util-common-service-docker-image .'
                }
            }
        }
        stage('Push image to Hub'){
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

