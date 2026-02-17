pipeline {
    agent {
        label 'jenkins-agent'
    }
    environment {
        SONAR_HOME = tool 'sonar-scanner'
        SONAR_OPTS = "${'-Dsonar.branch.name='+env.BRANCH_NAME+' -Dsonar.branch.target=develop'}"
        CLAIR_URL = 'http://clair-devsecops-test.apps.ocp2.tesoro.es/'
        CLAIR_THRESHOLD = 'High'
        CLAIR_MAX_VULNERABILITIES = '10'
        DOCKER_REPO = 'nexus-registry-devsecops-test.apps.ocp2.tesoro.es'
        APP_NAME = 'maetd-sftp-client'
    }
    options {
        buildDiscarder(logRotator(numToKeepStr:'10'))
        disableConcurrentBuilds()
    }
    stages {
        stage('build') {
            steps {
                container('jenkins-agent'){
                    sh 'mvn clean install -P pre,default-profile'
                }
            }
        }

        stage('upload-artifact') {
            steps {
                container('jenkins-agent'){
                    sh 'mvn clean deploy -P pre,default-profile'
                }
            }
        }

    }
    post {
        always {
            publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: '', reportFiles: 'clair-report/index.html', reportName: 'Clair', reportTitles: 'Clair Report'])
        }
        success {
            echo 'Everything is OK'
        }
        unstable {
            echo 'I am unstable :/'
        }
        failure {
            echo 'I failed :('
        }
    }
}
