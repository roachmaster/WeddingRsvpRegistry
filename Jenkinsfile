node("kube2"){

    stage("clone"){
        git 'git@github.com:roachmaster/WeddingRsvpRegistry.git'
    }

    stage("build"){
        sh "./gradlew clean build -x test -x integrationTest --info"
    }

    stage("unit test"){
        sh "./gradlew clean build test -x integrationTest --info"
    }

    stage("Integration Test"){
        //SPRING_DATASOURCE_PASSWORD stored in ~/.gradle/init.d/spring.gradle
        sh "./gradlew integrationTest --info"
    }

    stage("Docker Build"){
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

    stage("Create Secret"){
        withCredentials([usernamePassword(credentialsId: '8047ae57-cfa7-4ee1-86aa-be906b124593', passwordVariable: 'credPw', usernameVariable: 'credName')]) {
            String temp = sh(returnStatus: true, script: 'kubectl get secrets | grep -c mysql-pass')
            if(!temp.trim().equals(1)){
                println("Adding Secret");
                sh "kubectl create secret generic mysql-pass --from-literal=password=${credPw}"
            }
        }
    }

    stage("Create Deployment"){
        String temp = sh(returnStatus: true, script: 'kubectl get deployments | grep -c wedding-rsvp-registry').trim()
        if(!temp.trim().equals(1)){
            println("Removing wedding-rsvp-registry deployment");
            sh "kubectl delete deployment wedding-rsvp-registry"
        }
        sh "kubectl create -f k3s/deployment.yml"
    }

    stage("Create Service"){
        String temp = sh(returnStatus: true, script: 'kubectl get svc | grep -c wedding-rsvp-registry').trim()
        if(!temp.trim().equals(1)){
            println("Removing wedding-rsvp-registry svc");
            sh "kubectl delete svc wedding-rsvp-registry"
        }
        sh "kubectl apply -f k3s/service.yml"
    }
}
