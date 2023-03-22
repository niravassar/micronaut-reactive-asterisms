
# Micronaut Reactive Asterisms Summary And Purpose

This application was created with the purpose of deploying it to the Asterisms Ecosystem. In order to do this, I followed the documentation 
by the asterisms team to set up the environment locally with docker and micronaut. The purpose of this was to get familiar on how to integrate
a micronaut app into the asterisms ecosystem and the effort involved.

## Reference Documentation

- Documentation on Developer Setup to use the Asterism Eco System 
https://objectcomputing.github.io/asterisms-documentation/#

- Asterisms Project Page
https://objectcomputing.com/expertise/asterisms

- Nirav Detailed Person Notes 
these notes were taken along the way i am just saving them for reference. 
[nirav-asterisms-notes.txt](./nirav-asterisms-notes.txt)

---
# Micronaut Reactive Asterisms - Features Used In Asterisms Eco-system

This section describes the features used within the asterisms eco-system, and also explains the technical
details used to implement them

## Live debugging Application in Asterisms Docker Environment

In order to debug the application when it is plugged into the asterisms eco-system, you must create a `Remote JVM Debug`
Configuration in Intellij. The `docker-compose.yml` exposes a debug port in java tool options, and the intellij
must connect to do. Create a run configuration as `Remote JVM Debug`. Choose the port as 6000, and the `main` as the module.
Run the configuration and see it connect.

The configuration is stored as an xml file under `/.runConfiguration`, and checked in.

## JPA Entity Mapping to Postgres Database

- The `Actor` entity is mapped to a postgres database. A reactive micronaut data repository is used to retrieve the data. 
- The `asterisms-backend-core-postgres` dependency contains the libraries and annotation necessary for mapping the entity, flyway and using the repository.
- To set up the database in the asterisms system, you must execute the script in the `asterismsDatabase` container. For ex: `docker exec asterismsDatabase db_config/provision.sh asterisms_nirav io_asterisms_nirav`
- See `application.yml` for configuration used to hook to the database

## Sending Notifications to Asterisms User

The application has a controller method which will send a basic message to an authorized user in the asterisms system. See 
`MovieController`, the `sendNotification()` method.  This method searches for the `gandalf@example.com`, then uses the asterisms
notification api to send a message. 

The api is in `io.asterisms:asterisms-account` and this requires a direct db connection to the asterismsDatabase. I created a multiple datasource
connection so that we could have a connection to asterisms databases, and also one to the custom application database. 

Execute `/sendNotification` and then check mailhog to see the notification come through to the gandalf account. 

## Testing Using Spock    

I was only able to get tests working in this application by using the spock testing harness. The original application was built
with Junit tests using `@MicronautTest` annotation. Once I installed asterimsms dependencies the test Netty server would not start up any longer.

I converted all the tests to spock test harness by adding in the necessary dependencies and changing the syntax of the tests to abide by spock.
The tests work and pass. Therefore Asterisms may be incompatible with Junit at this time. 

**UPDATE** 

Junit tests will work if you apply this configuration into `application-test.yml`

```
micronaut:
  server:
    port: -1
    ssl:
      enabled: false
```

## Asterisms Security

In order to implement security into your app you must have `asterisms-security` package in the dependencies. Security is used mostly with annotations. 
This application demonstrates the use of `@Unsecured` and `@RequiresMemberOfWorkspace`.

In `application.yml`, you must turn off micronaut's security filter, and turn on asterisms security filter. See `micronaut.security.filter.enabled`, along with 
`asterisms.security.filter.enabled` and `pattern`. In `MovieController` the route must be declared in the `@Controller` annotation, and must include `{workspaceId}`. The workspaceId
must be passed in the URL of a http request, and the asterisms-security filter will pick it up and compare for authorization. Be sure
that you have a user configured as a workspace member. In this app it is `gandalf` as an admin.

In order to authenticate you must grab the jwt token generated by asterisms authentication module. Login to `auth.asterisms.local` as `gandalf`,
then go to the browser debugger and run `JSON.parse(localStorage.getItem('io.asterisms.auth.info')).token.token`. The token can also be accessed
in local storage of the browser.  You must also get the workspaceId allocated to the user by running `JSON.parse(localStorage.getItem('io.asterisms.account.current.workspace')).id`

Place these in a curl request and this will allow authentication into `MovieController.findDramasAfter78()`.

Here is a sample:

```shell
curl -k https://micronaut-reactive-asterisms.asterisms.local:3010/api/workspace/1b4912c3-f917-4fc8-a534-7353150e8ca1/findDramasAfter78 -H 'Authorization: Bearer eyJraWQiOiJjMzE1MTZkNTZiZmM0OGI3OWE2NzQ2OWMwZmQ5MTBjMCIsInR5cCI6IkpXVCIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiI3ZTczYzQwMS1iYzM4LTQxNTEtOWFhOS0yZTFiZWJiODc4MDUiLCJhdWQiOiJhc3RlcmlzbXMubG9jYWwiLCJyb2xlcyI6WyJXb3Jrc3BhY2VPd25lciJdLCJhY2Nlc3NvcklkIjoiN2U3M2M0MDEtYmMzOC00MTUxLTlhYTktMmUxYmViYjg3ODA1IiwiaXNzIjoiaHR0cHM6Ly9hdXRoLmFzdGVyaXNtcy5sb2NhbCIsIm9pZCI6IjdlNzNjNDAxLWJjMzgtNDE1MS05YWE5LTJlMWJlYmI4NzgwNSIsImV4cCI6MTY3OTUwMjE0MCwiaWF0IjoxNjc5NDk4NTQwLCJhY2Nlc3NvclNvdXJjZSI6IlVzZXIiLCJqdGkiOiIxMjc3NjljMi1jNGJhLTRlNWMtYWJhNy1iMzFmMDY3NzQzMzYiLCJ3b3Jrc3BhY2VJZCI6IjFiNDkxMmMzLWY5MTctNGZjOC1hNTM0LTczNTMxNTBlOGNhMSJ9.RHbvjLC1Qq8g74u7S61JDftjKv5bshNtnIOIcXaMbb-RnDtK4KuBQs9gZ1kgjQVes3vhVXGHZAQ04aadDn-uSMpaakT9TusUuVXbqrMcEC2KrPht1C4XBCXbrYDnf6ZHS3_IJZq8pPcOAy0rQayYYyl12k1AaQlftEmwi2Fs1Lfrl736T1hR82Oc46ID8a9R9hyOuH6KmUOmiwkbEV1uGTEOfluDYJTjeaoSGV52NnEnZbpW-8Fn1oYTPQ9Aiprl1tCV08jgW13uQJOX2Bwn3-kJdJ00inGToxFzTEqO2Xcb9JS_gM0TEXZVi4U4EqArnukiMXbCr8FTO5NCOZvAtA'

```

---
# Micronaut Reactive Asterisms Application Summary

This application was created by me (Nirav Assar) January 2023 while learning and exploring Project Reactor and reactive programming 
principles.  

## Purpose

This application was built from scratch in order to apply reactive programming examples gained during 
self learning.  I created the requirements for this simple application myself. I wanted to directly apply reactive programming to a micronaut application
built as rest services. I created  an example that was easy to understand with domain objects, and manufactured a few scenarios where 
using reactive publishers and subscribers, along with operators. I deliberately created data services that do not go to a database, but 
rather just send back reactive stream data, as that sets up the other layers to abide by reactive programming. I attempted to be consistent 
in the service and controller layer using reactive programming. In addition, tests are created that
validate the functionality of the service and controller. Reactive tests are written along with a few blocking tests. 

## Application Requirements 

## Theme

Build a sample reactive micronaut application rest service. Create a rest api micronaut application that is reactive in native. 
The application will not have a real data store. It will have a service that mimics outside api calls and return data with delays
in between elements. The application will be built with reactive programming techniques all the way up to the controller layer.

## Domain Layer

The application will have two entities, `Movie` and `Actor`. The data services will provide a list of movies and a list of actors. 
The movies will have `name`, `year`, `genre` as attributes and `Actor` will have `name` and `age` as attributes. 
The application services will do queries and return various sets of data.

## Service Layer

Requirements will be labeled with a numeric system just for reference. These should be easily relatable to the code in the service
layer.

- For movies, we will query for all movies (1.0), all movies made after a year with a certain genre(1.1). Retrieve movies 
with the title now changed to be title and year in it (1.2).
- For actors, find all actors older than a certain age (2.0).
- The application will also add actors to a movie and send it back (3.0).
- For simplicity, some the parameters for the methods are hardcoded for the purpose of focusing on reactive programming 
not http specifics.
- The application will contain tests for the service layer and the controller layer. The tests will use reactive testing techniques.
