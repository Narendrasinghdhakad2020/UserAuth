FROM oraclelinux:9

# Update system packages and install latest krb5-libs
RUN yum update -y && yum install -y krb5-libs

# Use an official Java runtime as a parent image
FROM openjdk:23


# Set the working directory in the container
WORKDIR /auth-app

# Copy the application JAR file to the container
COPY target/spring-user-auth.jar auth-app.jar

# Expose the application port (adjust as per your application)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "auth-app.jar"]