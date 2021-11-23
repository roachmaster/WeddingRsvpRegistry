node("kube2"){
    println("REPO_BUILD_TEST: ${env.REPO_BUILD_TEST}")
    println("DOCKER_BUILD: ${env.DOCKER_BUILD}")
    println("K3S_BUILD: ${env.K3S_BUILD}")

    boolean buildAndTest = env.REPO_BUILD_TEST.toBoolean()
    boolean dockerBuild = env.DOCKER_BUILD.toBoolean()
    boolean k3sBuild = env.K3S_BUILD.toBoolean()

    println("buildAndTest: ${buildAndTest}")
    println("dockerBuild: ${dockerBuild}")
    println("k3sBuild: ${k3sBuild}")

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
            withCredentials([usernamePassword(credentialsId: '87e61f11-079d-4052-b083-ea5859f0f85b', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME'),
                             usernamePassword(credentialsId: '8047ae57-cfa7-4ee1-86aa-be906b124593', passwordVariable: 'credPw', usernameVariable: 'credName')]) {
                def dockerImage = "wedding-rsvp-registry"
                def dockerVersion = "0.0.1-SNAPSHOT"
                def dockerName = "${DOCKER_USERNAME}/${dockerImage}:${dockerVersion}"
                def dockerOpt = "--build-arg mariaPw=${credPw}"
                sh "docker build ${dockerOpt} -t ${dockerName} ."
                sh "docker login --username ${DOCKER_USERNAME} --password ${DOCKER_PASSWORD}"
                sh "docker push ${dockerName}"
                sh "docker rmi ${dockerName}"
            }
        }
    }

    stage("Create Secret"){
        if(k3sBuild){
            withCredentials([usernamePassword(credentialsId: '8047ae57-cfa7-4ee1-86aa-be906b124593', passwordVariable: 'credPw', usernameVariable: 'credName')]) {
                String secretName = "mysql-pass"
                String temp = sh(returnStatus: true, script: "kubectl get secrets | grep -c ${secretName}")
                boolean secretExists = temp.trim().toBoolean()
                if(secretExists){
                    println("Adding Secret because count was ${temp} so secret exists is ${secretExists}");
                    sh "kubectl create secret generic ${secretName} --from-literal=password=${credPw}"
                }
            }
        }
    }

    stage("Create Deployment"){
        if(k3sBuild){
            String deploymentName = "wedding-rsvp-registry"
            String temp = sh(returnStatus: true, script: "kubectl get deployments | grep -c ${deploymentName}")
            if(temp.trim().equals("1")){
                println("Removing ${deploymentName} deployment");
                sh "kubectl delete deployment ${deploymentName}"
            }
            sh "kubectl create -f k3s/deployment.yml"
        }
    }

    stage("Create Service"){
        if(k3sBuild){
            String svcName = "wedding-rsvp-registry"
            String temp = sh(returnStatus: true, script: "kubectl get svc | grep -c ${svcName}")
            if(temp.trim().equals("1")){
                println("Removing ${svcName} svc");
                sh "kubectl delete svc ${svcName}"
            }
            sh "kubectl apply -f k3s/service.yml"
        }
    }
}
