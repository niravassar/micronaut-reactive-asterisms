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

# Reactive Programming Learning Plan

I set out to learn reactive programming in java because I realized that when I encountered reactive code, I just mentally 
shutoff and quickly looked for a way around it. When I would read reactive code, I would just say "oh no I don't get this", and 
consequentially ignore any of the concerns and promptly move on to other parts of the code which I could understand. When
I had to manipulate reactive java code, I would unconsciously change it to be blocking code to get it to work, or if allowed, totally rewrite
it to the programming model I was comfortable with, which is imperative programming. 

It was time to understand reactive programming head on, so I took upon this learning effort. The following is a list of 
resources and the learning path I took.

## Introductory Articles on Reactive Programming

Read this article as an introduction to philosophy of reactive programming in Java and the origins.

Reactive Systems in Java
https://www.baeldung.com/java-reactive-systems

Get acquainted with Project reactor and browse through the documentation as an introduction.

Project Reactor
https://projectreactor.io/

Get familiar with some reactive concepts in Micronaut, even though earlier versions of Micronaut use RxJava

Micronaut Tutorial: Reactive
https://piotrminkowski.com/2019/11/12/micronaut-tutorial-reactive/

## Udemy Reactive Java

Udemy courses are comprehensive and explain the concepts while conducting coding exercises. The instructor explains 
concepts at the beginning of each lesson and follows through with an exercise. He is very meticulous, and it's beneficial step by step.
It is a 7 hour video course. To learn the material I took 4-5 hours per video hour to consume notes, do the exercise and experiment
with code. 

Reactive Programming in Modern Java using Project Reactor
https://objectcomputing.udemy.com/course/reactive-programming-in-modern-java-using-project-reactor/

## Java Brain Reactive Course

To supplement the udemy course, I needed some more conceptual lectures to really grasp reactive. I started diving into this 
video series free on youtube with Java Brains. The lecturer really contrasts the different mindset of imperative programming vs 
functional and how that leads to reactive thinking. He also explains why reactive is needed, and gives the underlying philosophy 
on how reactive became so popular. During these learning sessions, something clicked where I started feeling like I knew the purpose
and could think in the reactive programming model. He also provides exercises for you to do, which I followed along. The course
is about 30 lectures of about 10 minutes each. Allocate 1 hour to process and learn each subject thoroughly along with exercise and 
supplemental research. 

Reactive programming with Java
https://www.youtube.com/playlist?list=PLqq-6Pq4lTTYPR2oH7kgElMYZhJd4vOGI

Code Repo
https://github.com/koushikkothagal/reactive-java-workshop

## Personal Artifacts and Output

During the course I took notes, which are summaries of what I observed along with code samples. The notes are stream of thought but helpful 
to me in retaining knowledge. I cloned the repos from udemy and javabrains and combined them into one which I stored in my github. I also created
a README where all my notes are housed.

My repo
https://github.com/niravassar/reactive-programming-using-reactor/tree/nirav-course/doc
- see the `nirav-course` branch, `docs` folder
- `io.javabrains.reactiveworkshop` package has the javabrains exercises (i combined it with the udemy).
- `main` branch is the untouched course 
- `final` branch has all the solutions

## Final Project Micronaut

In conclusion, I created this repo `micronaut-beginner-reactive`, a project attempting to place what I learned into a 
rest application. 

Enjoy and please provide me any feedback

Nirav Assar
assarn@objectcomputing.com
