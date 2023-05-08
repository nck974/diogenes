# Diogenes

[![codecov](https://codecov.io/github/nck974/diogenes/branch/main/graph/badge.svg?token=XDI3M0M5AE)](https://codecov.io/github/nck974/diogenes)

- [Usage](#usage)
  - [Docker](#docker)
- [Development](#development)
  - [Run tests](#run-tests)
  - [Coverage](#coverage)
  - [Create docker container](#create-docker-container)
  - [Export realm in keycloak](#export-realm-in-keycloak)

## Usage

### Docker

1. Add keycloak to your hosts file if you are running this locally:

  ```shell
  # Added to be used with diogenes
  127.0.0.1 keycloak
  ```

1. Pull the project or downlaod the `docker-compose.yaml`, `keycloak` folder and `.example.env`.
1. Create a copy of the `example.env` into a `.env` file with your own passwords.
1. Start the containers with `docker-compose up -d`
1. The latest image can be found in the [docker-hub](https://hub.docker.com/r/nck974/diogenes/tags)

### Create user

1. Login keycloak (port 8081) with your admin account.
1. Access the  `diogenes` realm.
1. Access users.
1. Create a user.
1. Go to credentials and set a password.
1. Go to role mapping. Add add the role `diogenes-user` (Filter by client).

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

## Test Authentication

1. Debug authentication:

```shell
# Access token:
curl -X POST 'http://localhost:8081/realms/diogenes/protocol/openid-connect/token' \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  --data-urlencode 'grant_type=password' \
  --data-urlencode 'client_id=diogenes-client' \
  --data-urlencode 'username=test' \
  --data-urlencode 'password=test' | jq

# Refresh token  
curl -X POST 'http://localhost:8081/keycloak/realms/diogenes/protocol/openid-connect/token' \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  --data-urlencode 'grant_type=refresh_token' \
  --data-urlencode 'client_id=diogenes-client' \
  --data-urlencode 'refresh_token=eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI3NTlkMTAxMi03NWNlLTQwNTgtOGI2MS00NDAzY2YyZGI0ZDgifQ.eyJleHAiOjE2ODA1NTg0ODAsImlhdCI6MTY4MDU1NjY4MCwianRpIjoiNTU5N2UxOTItNGVkNi00OTg4LWFlZTQtOWRkZjg4YWYxZTc2IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgxL3JlYWxtcy9kaW9nZW5lcyIsImF1ZCI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MS9yZWFsbXMvZGlvZ2VuZXMiLCJzdWIiOiI1ZTQ2MzE5OC03MGFmLTQ4OGItODA4ZS1jODMxNzI4OTI4YmUiLCJ0eXAiOiJSZWZyZXNoIiwiYXpwIjoiZGlvZ2VuZXMtY2xpZW50Iiwic2Vzc2lvbl9zdGF0ZSI6IjJlM2ZiOWEyLTBmYjgtNDNhZC1hYzQ0LWM3YTU0N2M0ODg1YSIsInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsInNpZCI6IjJlM2ZiOWEyLTBmYjgtNDNhZC1hYzQ0LWM3YTU0N2M0ODg1YSJ9.6rCoMqxqZ0FOSr2pNKM-lBZ77IX18VCpvZXjicMhusE' | jq

# Curl with access token
curl 'http://localhost:8080/diogenes/api/v1/item/?pageSize=10&offset=0&sort=ID&sortDirection=ASC' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJDSnIwS1FncTFIXzdCbkFlWkw5TEU2Q2IyRjhLQnRIRUNHV0RPMVk0MmdvIn0.eyJleHAiOjE2ODEzMjgyNTksImlhdCI6MTY4MTMyNzk1OSwianRpIjoiOTQ3MWZjN2EtNWQ3Mi00ZDA4LWI0ZDEtMjc0OTBhNDQ0Nzc1IiwiaXNzIjoiaHR0cDovL2tleWNsb2FrOjgwODAvcmVhbG1zL2Rpb2dlbmVzIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjEwOWIyNTdlLTA3YzItNGUxNi05ZWE2LTJmNTc0Nzc2MDE2OSIsInR5cCI6IkJlYXJlciIsImF6cCI6ImRpb2dlbmVzLWNsaWVudCIsInNlc3Npb25fc3RhdGUiOiIyYWRjZGEwMS0xOWM4LTRjOWYtYWNhZS1iNTJkMjZhN2MxY2MiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHA6Ly9kaW9nZW5lczo4MDgwIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIiwiZGVmYXVsdC1yb2xlcy1kaW9nZW5lcyJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImRpb2dlbmVzLWNsaWVudCI6eyJyb2xlcyI6WyJkaW9nZW5lcy11c2VyIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6ImVtYWlsIHByb2ZpbGUiLCJzaWQiOiIyYWRjZGEwMS0xOWM4LTRjOWYtYWNhZS1iNTJkMjZhN2MxY2MiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsInByZWZlcnJlZF91c2VybmFtZSI6InRlc3QiLCJnaXZlbl9uYW1lIjoiIiwiZmFtaWx5X25hbWUiOiIifQ.brWIr3Uc28VjaROMjX_7d3K1nSE9WOdqwWoU5JqqIz1kz7C2aZXkuT5URmjtocJD_LxhMlO5sFPKPijs2tlwWADfZ1_f5-Pdhhe63BbWGOtEI8K70X_2X0bFSKjiYZ8-OuJELo4vSEDzIwweIA7aUJ3uErEwNF7SMn01cFH_0JoZCm9Z_TJ0DQy4hL7LZylQdkFvmVMsBHTTLp9Yp9EdlIZW_IM5AlXGZL7ebovdDKOp3lVvR6IWsDPPqzt68ZGOAuXUmAh2UdGt8Ss7CZS_fc-7DvrjDvITPdWqn2nI54jZzvEN-66-IOKBn2DHCl4Y2kRN_kZKBzIhgBpOiDUzmw' -v
```

## Export realm in keycloak

1. Login as admin in `/keycloak/admin`.
1. Select de diogenes realm.
1. Go to `Settings`.
1. Click `actions`.
1. Select `Partial export`.
1. Mark everything.
1. Export.
