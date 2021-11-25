node {
    boolean runBuildAndTest = env.REPO_BUILD_TEST.toBoolean()
    boolean runDockerBuild = env.DOCKER_BUILD.toBoolean()
    boolean k3sBuild = env.K3S_BUILD.toBoolean()

    stage("Clone"){
        git 'git@github.com:roachmaster/WeddingRsvpRegistry.git'
    }

    stage("Build"){
        if(runBuildAndTest){
            sh "./gradlew clean build -x test -x integrationTest --info"
        }
    }

    stage("Run Unit Test"){
        if(runBuildAndTest){
            sh "./gradlew clean build test -x integrationTest --info"
            junit 'build/test-results/**/*.xml'
        }
    }

    stage("Integration Test"){
        if(runBuildAndTest){
            sh "./gradlew integrationTest --info"  //SPRING_DATASOURCE_PASSWORD stored in ~/.gradle/init.d/spring.gradle
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

    stage("Create Secret"){
        if(k3sBuild){
            withCredentials([usernamePassword(credentialsId: '8047ae57-cfa7-4ee1-86aa-be906b124593', passwordVariable: 'credPw', usernameVariable: 'credName')]) {
                k3sSecret name:"mysql-pass", credPw: "${credPw}"
            }
        }
    }

    stage("Create Deployment"){
        if(k3sBuild){
            k3sDeployment name: "wedding-rsvp-registry"
        }
    }

    stage("Create Service"){
        if(k3sBuild){
            k3sService name: "wedding-rsvp-registry"
        }
    }

    stage("Run Acceptance Test"){
        String containerName = "wedding-rsvp-registry"

        int numOfReadinessChecks = 0;
        int MAX_NUM_OF_CHECKS = 10;
        boolean isReady = false;
        String podName = "POD NOT FOUND"

        println "isReady: ${isReady}"
        println "${numOfReadinessChecks} out of ${MAX_NUM_OF_CHECKS} attempts"
        while(isReady && (numOfReadinessChecks < MAX_NUM_OF_CHECKS){
            String[] podInfo = sh(returnStdout: true ,script: "kubectl get pods | grep ^${containerName}").trim().split("\\s+")
            def podInfoList = new ArrayList<String>(Arrays.asList(podInfo))
            println podInfoList.toString()
            podName = podInfoList.get(0)
            println "podName: ${podName}"
            String readyStatus = podInfoList.get(1)
            println "readyStatus: ${readyStatus}"

            def readyStatusPair = readyStatus.tokenize('/')
            println readyStatusPair
            if(readyStatusPair[0] == readyStatusPair[1]){
                isReady = true;
                numOfReadinessChecks = MAX_NUM_OF_CHECKS;
                println "isReady: ${isReady}"
                println "${numOfReadinessChecks} out of ${MAX_NUM_OF_CHECKS} attempts"
            }else {
                numOfReadinessChecks++;
                sleep 30
                println "isReady: ${isReady}"
                println "${numOfReadinessChecks} out of ${MAX_NUM_OF_CHECKS} attempts"
            }
        }
        println("${podName} is ready for testing")

    }
}
