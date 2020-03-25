#Connector

1. Required
    - JDK 8 or 11+ installed with JAVA_HOME configured appropriately
    - Apache Maven 3.5.3+
    
2. Development Mode
    - mvn -DconnectorId=123 -Dfactor=123 quarkus:dev
    
3. Using with docker
    - mvn clean package
    - docker-compose up