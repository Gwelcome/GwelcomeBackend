FROM scottyengineering/java11
WORKDIR /usr/src/app
ARG JAR_PATH=./build/libs
COPY ${JAR_PATH}/Gwelcome-0.0.1-SNAPSHOT.jar ${JAR_PATH}/Gwelcome-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","./build/libs/Gwelcome-0.0.1-SNAPSHOT.jar"]