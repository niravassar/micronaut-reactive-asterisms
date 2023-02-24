FROM eclipse-temurin:17
WORKDIR /home/app
COPY ./build/libs/micronaut-reactive-asterisms-0.1-all.jar /home/app/application.jar

COPY  ./certificates /home/certificates

ARG KEYSTORE=/opt/java/openjdk/lib/security/cacerts
RUN keytool -import -trustcacerts -file /home/certificates/rootCA.der -alias "Asterisms Certificate Authority"  -keystore ${KEYSTORE} -storepass changeit -noprompt
RUN keytool --importcert -file /home/certificates/asterisms_local.crt -alias "Asterisms Development" -cacerts -storepass changeit -noprompt

RUN useradd -u 8877 asterisms
# Change to non-root privilege
USER asterisms

ENTRYPOINT ["java", "-jar", "/home/app/application.jar"]
