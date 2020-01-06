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
        sh 'curl -s https://codecov.io/bash | bash -s - -t ${CODECOV_TOKEN}'
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
    stage('Snapshot Site') {
      when {
        branch '2.0.develop'
      }
      steps {
        sh 'mvn -B clean site-deploy'
      }
    }
    stage('Release Site') {
      when {
        branch '2.0.master'
      }
      steps {
        sh 'mvn -B -P gh-pages-site clean site site:stage scm-publish:publish-scm'
      }
    }
  }
}