node {
    boolean runBuildAndTest = env.REPO_BUILD_TEST.toBoolean()
    boolean runDockerBuild = env.DOCKER_BUILD.toBoolean()
    boolean k3sBuild = env.K3S_BUILD.toBoolean()

    stage("Clone"){
        git 'git@github.com:roachmaster/WeddingRsvpRegistry.git'
    }

    stage("Build"){
        if(runBuildAndTest){
            sh "./gradlew clean build -x test -x acceptanceTest -x integrationTest --info"
        }
    }

    stage("Run Unit Test"){
        if(runBuildAndTest){
            sh "./gradlew test -x acceptanceTest -x integrationTest --info"
            junit 'build/test-results/**/*.xml'
        }
    }

    stage("Integration Test"){
        if(runBuildAndTest){
            sh "./gradlew integrationTest -x acceptanceTest --info"  //SPRING_DATASOURCE_PASSWORD stored in ~/.gradle/init.d/spring.gradle
            junit 'build/test-results/**/*.xml'
        }
    }

    stage("Docker Build"){
        if(runBuildAndTest && runDockerBuild){
            withCredentials([usernamePassword(credentialsId: '87e61f11-079d-4052-b083-ea5859f0f85b', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                def dockerVersion = sh(returnStdout: true, script: "./gradlew properties -q --no-daemon --console=plain -q | grep '^version:' | awk '{print \$2}'").trim()
                dockerBuild(dockerName:"${DOCKER_USERNAME}/wedding-rsvp-registry:${dockerVersion}",
                            dockerOpt:"--build-arg JAR_FILE=build/libs/weddingRsvpRegistry-${dockerVersion}.jar",
                            DOCKER_PASSWORD: "${DOCKER_PASSWORD}",
                            DOCKER_USERNAME:"${DOCKER_USERNAME}")
            }
        }
    }

    stage("K3S Deployment"){
        if(k3sBuild){
            withCredentials([usernamePassword(credentialsId: '8047ae57-cfa7-4ee1-86aa-be906b124593', passwordVariable: 'credPw', usernameVariable: 'credName')]) {
                k3sSecret name:"mysql-pass", credPw: "${credPw}"
            }
            k3sDeployment name: "wedding-rsvp-registry"
            k3sService name: "wedding-rsvp-registry"
            waitForPodToBeReady name:"wedding-rsvp-registry", maxNumOfAttempts: 30
        }
    }

    stage("Run Acceptance Test"){
        sh "./gradlew acceptanceTest --info"
        cucumber buildStatus: 'null', customCssFiles: '', customJsFiles: '', failedFeaturesNumber: -1, failedScenariosNumber: -1, failedStepsNumber: -1, fileIncludePattern: '**/Cucumber-*.json', jsonReportDirectory: 'build/reports/tests/acceptanceTest', pendingStepsNumber: -1, reportTitle: 'WeddingRsvpRegistry', skippedStepsNumber: -1, sortingMethod: 'ALPHABETICAL', undefinedStepsNumber: -1
    }
}
