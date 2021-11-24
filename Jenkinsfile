node("kube2"){
    boolean runBuildAndTest = env.REPO_BUILD_TEST.toBoolean()
    boolean runDockerBuild = env.DOCKER_BUILD.toBoolean()
    boolean k3sBuild = env.K3S_BUILD.toBoolean()

    stage("clone"){
        git 'git@github.com:roachmaster/WeddingRsvpRegistry.git'
    }

    stage("build"){
        if(runBuildAndTest){
            sh "./gradlew clean build -x test -x integrationTest --info"
        }
    }

    stage("unit test"){
        if(runBuildAndTest){
            sh "./gradlew clean build test -x integrationTest --info"
        }
    }

    stage("Integration Test"){
        if(runBuildAndTest){
            sh "./gradlew integrationTest --info"  //SPRING_DATASOURCE_PASSWORD stored in ~/.gradle/init.d/spring.gradle
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
}
