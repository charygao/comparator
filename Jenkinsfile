pipeline {
  agent {
    label 'maven'
  }
  environment {
    CODECOV_TOKEN = credentials('comparator-codecov-token')
  }
  tools {
    jdk 'jdk8'
    maven 'm3'
  }
  stages {
    stage('Tools') {
      steps {
        sh 'java -version'
        sh 'mvn -B --version'
      }
    }
    stage('Test') {
      when {
        not {
          branch 'feature/*'
        }
      }
      steps {
        sh 'mvn -B clean test'
      }
      post {
        always {
          junit 'target/surefire-reports/*.xml'
          jacoco(
              execPattern: '**/coverage-reports/*.exec'
          )
        }
      }
    }
    stage('Deploy Feature') {
      when {
        branch 'feature/*'
      }
      steps {
        sh 'mvn -B -P feature,allow-features clean deploy'
      }
      post {
        always {
          junit 'target/surefire-reports/*.xml'
          jacoco(
              execPattern: '**/coverage-reports/*.exec'
          )
        }
      }
    }
    stage('Deploy') {
      when {
        anyOf {
          branch '2.0.develop'
          branch '2.0.master'
        }
      }
      steps {
        sh 'mvn -B -P deploy clean deploy'
      }
    }
    stage('Deploy Site') {
      when {
        anyOf {
          branch '2.0.develop'
          branch '2.0.master'
        }
      }
      steps {
        sh 'mvn -B clean site-deploy'
      }
      post {
        always {
          sh 'curl -s https://codecov.io/bash | bash -s - -t ${CODECOV_TOKEN}'
        }
      }
    }
  }
}