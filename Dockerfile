FROM all-docker.asuproject.ru/base/ois-base-orel-ljdk17:230110.0734.1
EXPOSE 8080
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["sh","-c", "java -Djava.security.egd=file:/dev/./urandom ${JAVA_OPTS} -jar app.jar"]