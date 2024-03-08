
# Library API

This is a RESTful MVP API for library management systems, built on top of Spring Boot.

## Table of contents

- [Introduction](#introduction)
- [Features](#features)
- [Status](#status)
- [Technologies](#technologies)
- [Setup](#setup)
- [Demo](#demo)
- [License](#license)

### Introduction

The main goal of this project is to learn and implement almost 80% to 90% of concepts and techniques used in real-life software development using Spring Boot.

### Features

For learning purposes, this project has covered and implemented the following concepts:

- Initial project setup
- Table indexing
- Initialize the database with seeders
- Configure and track system logs
- Documenting API with OpenAPI and Swagger
- Manipulate DTOs with mappers
- Handling exceptions globally
- Validate request body and parameters

### Status

The progress status of this project as of now (March 2024) is still in the development stage.

### Technologies

To implement all the features listed [above](#features), the following technologies have been used for project setup:

- Java ```Version: 21```
- Spring Boot(Maven) ```Version: 3.2.3```
- Mysql ```Version: 8.2.0```
- For more details on project dependencies, please read the ```pom.xml``` file.

### Setup

To get started with this project on your local machine, kindly follow the steps below:

- Clone this repository.

```bash
  git clone https://github.com/muddy700/spring-library-api.git
```

- Navigate to the root project folder.

```bash
  cd spring-library-api
```

- Install project dependencies.

```bash
  mvn install
```

- Add configuration variables.

    Create ```application-dev.properties``` file then paste and fill all the fields available in ```application-example.properties```

- Run the project

```bash
  mvn spring-boot:run 
```

- View the API documentation.

    Open your browser and visit ```http://localhost:port``` and follow the instructions.
  
    NB: Remember to change the word "port" with the port number you set in ```application-dev.properties``` file.

### Demo

For a live demonstration of the project, kindly click [here](https://id.heroku.com/login).

### License

The development of the underlying project falls within the standards provided by the following licenses:

- [MIT](https://choosealicense.com/licenses/mit/)
