# OpenJio - CS203 Collaborative Software Development Project

This repository contains the source code for the OpenJio web application. OpenJio is a powerful and user-friendly events website tailored for university students, designed to enhance the way you discover, create, and participate in events on your campus.

## Features

- **Central Hub to Navigate and Discover New Events:** A vibrant digital marketplace where users can effortlessly explore a diverse array of events.


- **Student Score System:** Each student has their own score which is calculated behind the scenes based off their event attendance rates.


- **Customised Queue System with 4 Unique Algorithms:** Simplify the event registration process with our intuitive user interface. Most importantly, we incorporated a queue system depending on the type of event and have implemented 4 different queuing algorithms.


- **Event Creation for University Clubs and Societies:** Organisers can easily set up event details. They can also specify registration requirements, capacity limits, their queuing algorithm of choice and a filter to accept students based off their student score.


- **Generated Student Schedule for Signed Up Events:** An event schedule table is generated to indicate students' rejected, pending and accepted events.


- **JWT Authentication and Password Reset:** Upon login, our application provides you with a JWT token which is used to secure our API endpoints. We also have an email service to send you a token to reset your password. All these are implemented just to provide our users with a safe and secure application, so you can enhance your campus experience with ease!

## Technologies Used

**Frontend:** React (JavaScript + TypeScript) and styled with Tailwind CSS, shadcn & MUI libraries

**Backend:** Spring Boot

**Database:** MySQL

## Deployment Tools

**Continuous Integration:**

- Maven
- Docker Compose
- SonarQube and SonarCloud

**Continuous Delivery:**

- Docker
- AWS services

## Database Entity-Related Diagram

<img width="375" alt="Database Entity-Related Diagram" src="frontend/public/openjio-ER-diagram.jpg">

## Workflow Diagram

This diagram consists of our CI/CD workflow and AWS architecture.

<img width="700" alt="OpenJio Workflow Diagram" src="frontend/public/workflow-diagram.png">

## Documentation

For documentation on the API endpoints and usage, refer to our Swagger API documentation.

## Contributors

Done by Ashley, Ron, Pramit, Ignatius, Darius and Justin
