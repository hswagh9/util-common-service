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
        
        // Uncomment and correct the syntax below
//        stage('Build Docker Image') {
//            steps {
//                script {
//                    sh 'docker build -t book-devops-automation .'
//                }
//            }
//        }
    }
}
