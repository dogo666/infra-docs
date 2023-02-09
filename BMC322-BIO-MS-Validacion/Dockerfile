FROM adoptopenjdk/openjdk11:alpine-slim

# Docs
LABEL autor="Globant Biometria Team <macro-devops@globant.com>"
LABEL vendor="Globant"

# Environment variables
ARG CONTAINER_PATH=.
ARG RUNTIME_FOLDER="/usr/apps"
ENV RUNTIME_FOLDER=${RUNTIME_FOLDER}
ENV SPRING_CLOUD_CONFIG_ENABLED "false"
ENV APP_FILE macro-validacion.jar

WORKDIR ${RUNTIME_FOLDER}

# Add none root user to docker container
ARG USER="biometria"
ENV USER=${USER}

# Deployment artifacts
COPY ${CONTAINER_PATH}/conf/cloud .
COPY ${CONTAINER_PATH}/target/$APP_FILE $RUNTIME_FOLDER/

# User permissions
RUN addgroup -g 1000 -S ${USER} && \
    adduser -u 1000 -S ${USER} -G ${USER} && \
    chown -R ${USER}:${USER} ${RUNTIME_FOLDER}

USER ${USER}

# Execution
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar -Dmacro.home.config=conf/ -Djavax.net.ssl.trustStore=/usr/apps/security/cacerts.jks $APP_FILE"]