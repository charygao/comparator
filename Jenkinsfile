pipeline {
  agent {
    label 'maven'
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
      steps {
        sh 'mvn -B clean test'
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
    stage('Site') {
      when {
        anyOf {
          branch '2.0.develop'
          branch '2.0.master'
        }
      }
      steps {
        sh 'mvn -B clean site-deploy'
      }
    }
  }
}