
# Micronaut Reactive Asterisms

This application was created with the purpose of deploying it to the Asterisms Ecosystem. In order to do this, I followed the documentation 
by the asterisms team to set up the environment locally with docker and micronaut. The purpose of this was to get familiar on how to integrate
a micronaut app into the asterisms ecosystem and the effort involved.

## Reference Documentation

Documentation on Developer Setup to use the Asterism Eco System 
https://objectcomputing.github.io/asterisms-documentation/#

Asterisms Project Page
https://objectcomputing.com/expertise/asterisms

Nirav Detail Person Notes
- these notes were taken along the way i am just saving them for reference. 
- [nirav-asterisms-notes.txt](./nirav-asterisms-notes.txt)

# Micronaut Beginner Reactive Application

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
