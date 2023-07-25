pipeline{
    agent any
    tools{
    maven 'maven'
    }
    stages{
       stage('build maven'){
          steps{
              checkout scmGit(branches: [[name: '*/lms-jenkins-pipeline']], extensions: [], userRemoteConfigs: [[credentialsId: 'git_credential', url: 'https://github.com/isarthak/lms-helmchart.git']])
               sh 'mvn clean install'
               }
            }
       stage('Build Docker Image') {
          steps {
            script {
               withCredentials([usernamePassword(credentialsId: 'DockerHub_Credential', passwordVariable: 'DOCKERHUB_PASSWORD', usernameVariable: 'DOCKERHUB_USERNAME')]) {
               // Generate a timestamp or version number for the image tag
               def timestamp = new Date().format("yyyyMMdd_HHmmss")
               def imageTag = "lms-profile-service:${timestamp}"

              // Build the Docker image using the spring-boot:build-image Maven goal
               sh "mvn spring-boot:build-image -Dspring-boot.build-image.imageName=isarthak/${imageTag}"
              //    sh "mvn spring-boot:build-image -Dspring-boot.build-image.imageName=isarthak/lms-profile-service:latest"

             // Log in to Docker Hub using the credentials
                sh "docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD"
                // sh "echo $DOCKERHUB_PASSWORD | docker login --username $DOCKERHUB_USERNAME --password-stdin"

                // Push the Docker image to a registry
                sh "docker push isarthak/${imageTag}"
                // sh "docker push isarthak/lms-profile-service:latest"

                // Tag the image as "latest"
                sh "docker tag isarthak/${imageTag} isarthak/lms-profile-service:latest"
                sh "docker push isarthak/lms-profile-service:latest"

                // Print the image tag for reference
                echo "Docker image tag: ${imageTag}"
                }
              }
            }
        }
       stage('Deploy to Kubernetes'){
            steps{
                script {
                    withCredentials([file(credentialsId: 'Kubernetes_Credentials', variable: 'KUBECONFIG')]) {
                    //Cleanup the resources
                    sh "kubectl delete -f lms-profile-service-service.yaml --ignore-not-found"
                    //Apply the new manifest file
                    sh "kubectl apply -f lms-profile-service-service.yaml"
                   }
                }
            }
        }
     }
  }

