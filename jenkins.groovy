pipeline {
    agent {label 'ubuntu'}
    parameters {
        string(name: "VERSION", defaultValue: "v1.0", description: "Build version")
    }
    stages{
        stage('Checkout external proj') {
            steps {
                git branch: 'main',
                    credentialsId: 'nakkumar(ghp_0f....)',
                    url: 'https://github.com/nakkumar/jenkins-git-test.git'
            }
        }

        stage('Docker Build') {
            steps{
                script{
                    sh '''
                    docker build -f docker.http -t httpd-website:$VERSION $WORKSPACE 
                    '''
                }
            }    
        }

        stage('Docker Push') {
            steps{
                script{
                    sh '''
                    docker tag  httpd-website:$VERSION nakkumar/jenkins-website-test:$VERSION
                    docker push nakkumar/jenkins-website-test:$VERSION
                    '''
                }
            }    
        }

        stage('Docker Launch') {
            steps{
                script{
                    sh '''
                    sleep 30
                    docker-compose -f httpd.yaml up -d 
                    '''
                }
            }    
        }

        stage('Update version in Docker-Compose File') {
            steps{
                script{
                    sh '''
                    sed -i 's/image: nakkumar\/jenkins-website-test:.*/image: nakkumar\/jenkins-website-test:$VERSION/g' $WORKSPACE/httpd.yaml 
                    '''
                }
            }    
        }

        stage('Start the Apache service') {
            steps{
                script{
                    sh '''
                    sleep 30
                    docker exec apache service apache2 start
                    '''
                }
            }    
        }        

    }    
} 