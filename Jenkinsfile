node("kube2"){
    git 'git@github.com:roachmaster/WeddingRsvpRegistry.git'
    withCredentials([usernamePassword(credentialsId: '8047ae57-cfa7-4ee1-86aa-be906b124593', passwordVariable: 'credPw', usernameVariable: 'credName')]) {
        sh "./gradlew clean build test --info"
        sh "./gradlew integrationTest -Pspring.datasource.password=${credPw} --info"
    }

    withCredentials([usernamePassword(credentialsId: '87e61f11-079d-4052-b083-ea5859f0f85b', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME'),
                        usernamePassword(credentialsId: '8047ae57-cfa7-4ee1-86aa-be906b124593', passwordVariable: 'credPw', usernameVariable: 'credName')]) {
        sh "docker build --build-arg mariaPw=${credPw} -t ${DOCKER_USERNAME}/wedding-rsvp-registry:0.0.1-SNAPSHOT ."
        sh "docker login --username ${DOCKER_USERNAME} --password ${DOCKER_PASSWORD}"
        sh "docker push ${DOCKER_USERNAME}/wedding-rsvp-registry:0.0.1-SNAPSHOT"
        sh "docker rmi ${DOCKER_USERNAME}/wedding-rsvp-registry:0.0.1-SNAPSHOT"
   }

        String tempString;
        withCredentials([usernamePassword(credentialsId: '8047ae57-cfa7-4ee1-86aa-be906b124593', passwordVariable: 'credPw', usernameVariable: 'credName')]) {
            tempString = sh(returnStatus: true, script: 'kubectl get secrets | grep -c mysql-pass')
            if(tempString.trim().equals("1")){
                println("Adding Secret");
                sh "kubectl create secret generic mysql-pass --from-literal=password=${credPw}"
            }
        }

        tempString = sh(returnStatus: true, script: 'kubectl get deployments | grep -c wedding-rsvp-registry')
        if(!tempString.trim().equals("1")){
            println("Removing wedding-rsvp-registry deployment");
            sh "kubectl delete deployment wedding-rsvp-registry"
        }
        sh "kubectl create -f k3s/deployment.yml"

        tempString = sh(returnStatus: true, script: 'kubectl get svc | grep -c wedding-rsvp-registry')
        if(!tempString.trim().equals("1")){
            println("Removing wedding-rsvp-registry svc");
            sh "kubectl delete svc wedding-rsvp-registry"
        }
        sh "kubectl apply -f k3s/service.yml"

}
