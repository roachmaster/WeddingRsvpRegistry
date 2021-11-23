node("kube2"){
    boolean buildAndTest = env.REPO_BUILD_TEST.toBoolean()
    boolean dockerBuild = env.DOCKER_BUILD.toBoolean()
    boolean k3sBuild = env.K3S_BUILD.toBoolean()

    stage("clone"){
        git 'git@github.com:roachmaster/WeddingRsvpRegistry.git'
    }

    stage("build"){
        if(buildAndTest){
            sh "./gradlew clean build -x test -x integrationTest --info"
        }
    }

    stage("unit test"){
        if(buildAndTest){
            sh "./gradlew clean build test -x integrationTest --info"
        }
    }

    stage("Integration Test"){
        if(buildAndTest){
            //SPRING_DATASOURCE_PASSWORD stored in ~/.gradle/init.d/spring.gradle
            sh "./gradlew integrationTest --info"
        }
    }

    stage("Docker Build"){
        if(buildAndTest && dockerBuild){
            withCredentials([usernamePassword(credentialsId: '87e61f11-079d-4052-b083-ea5859f0f85b', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                def appName = "weddingRsvpRegistry"
                def dockerImage = "wedding-rsvp-registry"
                def dockerVersion = "0.0.1-SNAPSHOT"
                dockerBuild(dockerName:"${DOCKER_USERNAME}/${dockerImage}:${dockerVersion}",
                             dockerOpt:"--build-arg JAR_FILE=build/libs/${appName}-${dockerVersion}.jar",
                             DOCKER_PASSWORD: "${DOCKER_PASSWORD}",
                             DOCKER_USERNAME:"${DOCKER_USERNAME}")
            }
        }
    }

    stage("Create Secret"){
        if(k3sBuild){
            withCredentials([usernamePassword(credentialsId: '8047ae57-cfa7-4ee1-86aa-be906b124593', passwordVariable: 'credPw', usernameVariable: 'credName')]) {
                String secretName = "mysql-pass"
                String ksecrets = sh(returnStdout: true, script: "kubectl get secrets").trim()
                if(ksecrets.count(secretName) == 0){
                    sh "kubectl create secret generic ${secretName} --from-literal=password=${credPw}"
                }
            }
        }
    }

    stage("Create Deployment"){
        if(k3sBuild){
            String deploymentName = "wedding-rsvp-registry"
            String kdeployments = sh(returnStdout: true, script: "kubectl get deployments").trim()
            if(kdeployments.count(deploymentName) > 0){
                sh "kubectl delete deployment ${deploymentName}"
            }
            sh "kubectl create -f k3s/deployment.yml"
        }
    }

    stage("Create Service"){
        if(k3sBuild){
            String svcName = "wedding-rsvp-registry"
            String ksvc = sh(returnStdout: true, script: "kubectl get svc").trim()
            if(ksvc.count(svcName) > 0){
                sh "kubectl delete svc ${svcName}"
            }
            sh "kubectl apply -f k3s/service.yml"
        }
    }
}
