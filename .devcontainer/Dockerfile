FROM eclipse-temurin:8-jdk-jammy

ARG MAVEN_VERSION=3.9.9
ARG MAVEN_URL=https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz
ARG USER_UID=1000
ARG USER_GID=1000

ENV JAVA_HOME=/opt/java/openjdk
ENV MAVEN_HOME=/opt/maven
ENV PATH="${MAVEN_HOME}/bin:${PATH}"

RUN apt-get update && apt-get install -y --no-install-recommends \
    curl \
    git \
    ca-certificates \
    jq \
    tree \
    vim \
    && rm -rf /var/lib/apt/lists/* \
    && echo "Descargando Maven ${MAVEN_VERSION}..." \
    && curl -fsSL "${MAVEN_URL}" -o /tmp/maven.tar.gz \
    && curl -fsSL "${MAVEN_URL}.sha512" -o /tmp/maven.tar.gz.sha512 \
    && echo "Verificando checksum SHA-512..." \
    && cd /tmp \
    && echo "$(cat maven.tar.gz.sha512)  maven.tar.gz" | sha512sum -c - \
    && echo "Checksum verificado correctamente" \
    && tar -xzf maven.tar.gz -C /opt \
    && ln -s "/opt/apache-maven-${MAVEN_VERSION}" /opt/maven \
    && rm /tmp/maven.tar.gz /tmp/maven.tar.gz.sha512

RUN groupadd -g ${USER_GID} vscode \
    && useradd -u ${USER_UID} -g ${USER_GID} -m -s /bin/bash vscode \
    && mkdir -p /home/vscode/.m2 \
    && chown -R vscode:vscode /home/vscode

USER vscode
WORKDIR /workspace

RUN git config --global --add safe.directory /workspace

CMD ["sleep", "infinity"]
