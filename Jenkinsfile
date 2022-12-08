/*
 * Groovy
 *
 * Copyright 2022 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
pipeline {
    agent {
        docker {
            label 'docker'
            image 'eclipse-temurin:11'
            args '-v ${JENKINS_TOOLS_HOME}/common/java-keystore/keystore-public-cross:${JENKINS_TOOLS_HOME}/common/java-keystore/keystore-public-cross'
        }
    }

    stages {
        stage ('Build') {
            steps {
                script {
                    def branch = env.GIT_BRANCH
                    // Fetch level
                    env.fetchLevel = "snapshot"
                    if(branch.startsWith("maintenance/") || branch.startsWith("release/") ||
                           branch.startsWith("hotfix/") || branch.equals("origin/master") || branch.equals("master")) {
                        env.fetchLevel = "release"
                    }
                    print("Fetch level : " + fetchLevel)
                    // Publish level
                    env.publishLevel = "snapshot"
                    if(branch.startsWith("release/") || branch.startsWith("hotfix/")|| branch.startsWith("feature/")) {
                        env.publishLevel = "noPublish"
                    } else if(branch.startsWith("maintenance/") || branch.equals("origin/master") || branch.equals("master")) {
                        env.publishLevel = "release"
                    }
                    print("Publish level : " + publishLevel)
                }
                wrap([$class: 'BuildUser']) {
                    withCredentials([[$class: 'UsernamePasswordMultiBinding',
                                        credentialsId: '2f7097bd-af94-4ac1-ba6b-d41788b9b5cf',
                                        usernameVariable: 'ARTIFACTORY_CROSS_INTEGRATION_USERNAME',
                                        passwordVariable: 'ARTIFACTORY_CROSS_INTEGRATION_PASSWORD']]) {
                        sh "./gradlew -I ./ci/microej.init.gradle.kts --info clean build -Djavax.net.ssl.trustStore=${JENKINS_TOOLS_HOME}/common/java-keystore/keystore-public-cross -Dartifactory.cross.username=${ARTIFACTORY_CROSS_INTEGRATION_USERNAME} -Dartifactory.cross.password=${ARTIFACTORY_CROSS_INTEGRATION_PASSWORD} -Duser.id=${env.BUILD_USER_ID} -Dartifacts.fetch.level=${env.fetchLevel}"
                    }
                }
            }
        }
        stage ('Publish') {
            when {
                expression {
                    return env.publishLevel != null && env.publishLevel != 'noPublish';
                }
            }
            steps {
                wrap([$class: 'BuildUser']) {
                    withCredentials([[$class: 'UsernamePasswordMultiBinding',
                                        credentialsId: '2f7097bd-af94-4ac1-ba6b-d41788b9b5cf',
                                        usernameVariable: 'ARTIFACTORY_CROSS_INTEGRATION_USERNAME',
                                        passwordVariable: 'ARTIFACTORY_CROSS_INTEGRATION_PASSWORD']]) {
                        sh "./gradlew -I ./ci/microej.init.gradle.kts --info publish -Djavax.net.ssl.trustStore=${JENKINS_TOOLS_HOME}/common/java-keystore/keystore-public-cross -Dartifactory.cross.username=${ARTIFACTORY_CROSS_INTEGRATION_USERNAME} -Dartifactory.cross.password=${ARTIFACTORY_CROSS_INTEGRATION_PASSWORD} -Duser.id=${env.BUILD_USER_ID} -Dartifacts.fetch.level=${env.fetchLevel} -Dartifacts.publish.level=${env.publishLevel}"
                    }
                }
            }
        }
    }
}