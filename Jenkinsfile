def dooraySendMessage(msg, env) {
    def hook_url = 'https://hook.dooray.com/services/2256444361245483660/2670261080034638179/DRRnSLweQaCPk7QQNyI60Q'
    def text = "${msg} jenkins: ${env.JOB_NAME}(${env.LOCAL_WEB_VERSION} #${env.BUILD_ID})"
    def data = "'{\"botName\": \"Jenkins\", \"botIconImage\": \"https://static.dooray.com/static_images/dooray-bot.png\", \"text\":\"${text}\"}'"

    sh "curl -s -X POST -H \"Content-Type: application/json\" -d ${data} ${hook_url}"
}

pipeline {
    agent any

    options {
        disableConcurrentBuilds()
        // https://stackoverflow.com/questions/39542485/how-to-write-pipeline-to-discard-old-builds/44155346
        buildDiscarder logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '30', daysToKeepStr: '30', numToKeepStr: '')
    }

    tools {
        maven 'M3'
        jdk 'openjdk-11'
    }

    stages {
        stage('prepared') {
            steps {
                script {
                    env.LOCAL_WEB_VERSION = readMavenPom().getVersion()
                    env.LOCAL_WEB_VERSION_BUILD_NUMBER = "${env.LOCAL_WEB_VERSION}_${env.BUILD_ID}"
                }
                dooraySendMessage(':rocket: [start] elandretail-server build -', env)
            }
        }

        stage('checkout') {
            steps {
                checkout scm
            }
        }

        stage('build') {
            steps {
                sh "mvn clean package"
            }
            post {
                success {
                    junit allowEmptyResults: true, healthScaleFactor: 0.0, testResults: '**/target/surefire-reports/TEST-*.xml'
                }
            }
        }

        stage('code inspection tools') {
            steps {
                sh "mvn sonar:sonar -Dsonar.projectName=local_elandretail_server -Dsonar.projectKey=local_elandretail_server -Dsonar.host.url=http://sonarqube.parkingcloud.co.kr -Dsonar.login=b926cc89ffe32182e8ba6fd3d2c9a42ab09e4833"
            }
        }

        stage('artifact') {
            when {
                expression { BRANCH_NAME ==~ /^(?:.*master|.*RC[1-9]|.*SNAPSHOT)$/ }
            }
            steps {
                archiveArtifacts 'target/*.jar'
            }
        }
    }
    post {
        success {
            dooraySendMessage(':large_blue_circle: [success] elandretail-server finally -', env)
        }
        failure {
            dooraySendMessage(':red_circle: [failure] elandretail-server finally -', env)
        }
    }
}