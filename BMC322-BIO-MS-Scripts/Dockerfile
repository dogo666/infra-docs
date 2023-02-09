FROM fabric8/java-alpine-openjdk11-jre

COPY . /app

WORKDIR /app

RUN apk add bash

#RUN touch flyway-local.conf

#RUN sed -i '1i\flyway.url="'$VARIABLE1'"' /RUTA/AL/ARCHIVO/flyway.conf

#RUN mvn clean install

#CMD ["mvn" "-Dflyway.configFiles=flyway-local.conf flyway:migrate"]