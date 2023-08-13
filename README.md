# Diogenes

[![codecov](https://codecov.io/github/nck974/diogenes/branch/main/graph/badge.svg?token=XDI3M0M5AE)](https://codecov.io/github/nck974/diogenes)

- [Usage](#usage)
  - [Docker](#docker)
- [Development](#development)
  - [Run tests](#run-tests)
  - [Coverage](#coverage)
  - [Create docker container](#create-docker-container)

## Usage

### Docker

1. Pull the project or download the `docker-compose.yaml` and `.example.env`.
1. Create a copy of the `example.env` into a `.env` file with your own passwords.
1. Start the containers with `docker-compose up -d`
1. The latest image can be found in the [docker-hub](https://hub.docker.com/r/nck974/diogenes/tags)

## Development

This project was generated using as base start spring:

```properties
https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.0.4&packaging=jar&jvmVersion=17&groupId=io.nck&artifactId=diogenes&name=diogenes&description=Demo%20project%20for%20Spring%20Boot&packageName=io.nck.diogenes&dependencies=web]
```

1. Download maven `apache-maven-3.9.1` and add it to the path.
1. Configure the `JAVA_HOME` pointing to the folder of the JDK17.
    1. In windows with a ENVIRONMENT_VARIABLE (Can be added from path menu).
    1. In UNIX exporting with `export JAVA_HOME`.

## Run tests

1. Execute `.\mvnw test`.

### Coverage

1. Execute `mvn test jacoco:report`.
1. The report will be generated in `target\site\jacoco\index.html`.

### Create docker container

1. First build the image with `./mvnw install`.
1. Build the container with `docker build -f docker/Dockerfile -t nck974/diogenes:0.0.1-SNAPSHOT .`
1. Generate a token in `https://hub.docker.com` and login with `docker login -u <user>`. Paste the generated token as password.
1. Push the generated container with `docker push nck974/diogenes:0.0.1-SNAPSHOT`.
1. Run the image with docker compose to pass the environment variables of the database.

TOC generated from [ecotrust-canada](https://ecotrust-canada.github.io/markdown-toc/)
