# Diogenes

[![codecov](https://codecov.io/github/nck974/diogenes/branch/main/graph/badge.svg?token=XDI3M0M5AE)](https://codecov.io/github/nck974/diogenes)

<img src="ng-diogenes/src/assets/logo/logo_transparent.png" alt="Logo" width="300">

Diogenes is an inventory web application designed to help you manage and keep track of your possessions at home. It is completely self-hosted, and no data is collected from users. You have the flexibility to easily migrate your data using the API, which is detailed in the Open API documentation provided by the backend server.

This app is intended to assist you in cataloging everything you own, helping you avoid the clutter often associated with Diogenes Syndrome.

The application goal is to provide a faster and more user-friendly alternative to managing your belongings compared to a plain Excel spreadsheet

- [Usage](#usage)
  - [Docker](#docker)
- [Development](#development)
  - [Backend](#backend)
    - [Run tests](#run-tests)
    - [Coverage](#coverage)
    - [Create backend docker container](#create-backend-docker-container)
  - [Frontend](#frontend)
    - [Create frontend docker container](#create-frontend-docker-container)
  - [Reverse proxy](#reverse-proxy)

## Usage

### Docker

1. Pull the project or download the `docker-compose.yaml` and `.example.env`.
1. Create a copy of the `example.env` into a `.env` file with your own passwords.
1. Add a certificate or create a self signed one for local network. Put them inside `ssl/cert.pem` and `ssl/cert.key`:

  ```bash
  openssl req -new -x509 -days 365 -noenc -out ssl/cert.pem -keyout ssl/cert.key
  ```

1. Start the containers with `docker-compose up -d`
1. The latest images can be found in the [diogenes](https://hub.docker.com/r/nck974/diogenes/tags) and [diogenes-ng](https://hub.docker.com/r/nck974/diogenes-ng/tags)

## Development

### Backend

This project was generated using as base start spring:

```properties
https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.0.4&packaging=jar&jvmVersion=17&groupId=io.nck&artifactId=diogenes&name=diogenes&description=Demo%20project%20for%20Spring%20Boot&packageName=io.nck.diogenes&dependencies=web]
```

1. Download maven `apache-maven-3.9.1` and add it to the path.
1. Configure the `JAVA_HOME` pointing to the folder of the JDK17.
    1. In windows with a ENVIRONMENT_VARIABLE (Can be added from path menu).
    1. In UNIX exporting with `export JAVA_HOME`.

#### Run tests

1. Execute `cd diogenes & .\mvnw test`.

#### Coverage

1. Execute `cd diogenes && mvn test jacoco:report`.
1. The report will be generated in `diogenes\target\site\jacoco\index.html`.

#### Create backend docker container

1. First build the image with `cd diogenes & ./mvnw install`.
1. Build the container with `docker build -f docker/Dockerfile -t nck974/diogenes:0.0.2 .`
1. Generate a token in `https://hub.docker.com` and login with `docker login -u <user>`. Paste the generated token as password.
1. Push the generated container with `docker push nck974/diogenes:0.0.2`.
1. Create the `latest` tag and push it:

  ```bash
  docker tag nck974/diogenes:0.0.2 nck974/diogenes:latest
  docker push nck974/diogenes:latest
  ```

1. Run the image with docker compose to pass the environment variables of the database.

### Frontend

To run the app in development mode just access the folder `diogenes-ng` and start the app with `ng serve`.

#### Create frontend docker container

1. Build the container with `docker build -f docker/Dockerfile.angular -t nck974/diogenes-ng:0.0.2 .`
1. Generate a token in `https://hub.docker.com` and login with `docker login -u <user>`. Paste the generated token as password.
1. Push the generated container with `docker push nck974/diogenes-ng:0.0.2`.
1. Create the `latest` tag and push it:

  ```bash
  docker tag nck974/diogenes-ng:0.0.2 nck974/diogenes-ng:latest
  docker push nck974/diogenes-ng:latest
  ```

1. Run the image with docker compose to pass the environment variables of the database.

#### Checking updates

1. Install:

```bash
npm install -g npm-check-updates
```

1. Then:

```bash
ncu -u
```

### Reverse proxy

1. Backend and frontend can be served using a custom reverse proxy or just use the one provided in the image. To do so build it with:

1. Build the container with `docker build -f docker/Dockerfile.nginx -t nck974/diogenes-reverse-proxy:0.0.1 .`
1. Generate a token in `https://hub.docker.com` and login with `docker login -u <user>`. Paste the generated token as password.
1. Push the generated container with `docker push nck974/diogenes-reverse-proxy:0.0.1`.
1. Create the `latest` tag and push it:

  ```bash
  docker tag nck974/diogenes-reverse-proxy:0.0.1 nck974/diogenes-reverse-proxy:latest
  docker push nck974/diogenes-reverse-proxy:latest
  ```

TOC generated from [ecotrust-canada](https://ecotrust-canada.github.io/markdown-toc/)
