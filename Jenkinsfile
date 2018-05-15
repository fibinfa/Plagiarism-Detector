pipeline {
	   agent {
	       docker {
	           image 'maven:3-alpine'
	           args '-v /root/.m2:/root/.m2'
	       }
	   }

	   stages {
	       stage('Build') {
	           steps {
	               echo "Building"
				   sh 'mvn clean install -f codesniffer/pom.xml'
	               sh 'mvn compile -f codesniffer/pom.xml'
	               sh 'mvn package -f codesniffer/pom.xml'
	           }
	       }
	       stage('Test'){
	           steps {
	               echo "Testing"
	               sh 'mvn test -f codesniffer/pom.xml'
	           }
	       }
		stage('SonarQube') {
	            steps {
	                withSonarQubeEnv('SonarQube') {
	                        sh 'mvn clean install -f codesniffer/pom.xml'
	                        sh 'mvn sonar:sonar -f codesniffer/pom.xml'
	                }
	            }
	        }
			stage('Quality') {
	          steps {
	            sh 'sleep 30'
	            timeout(time: 10, unit: 'SECONDS') {
	               retry(5) {
			script {
	                    def qg = waitForQualityGate()
	                    if (qg.status != 'OK') {
	                  error "Pipeline aborted due to quality gate failure: ${qg.status}"
	              }
	            }
	          }
	        }
	      }
	    }
	            
	        
	      
	    }
	}

