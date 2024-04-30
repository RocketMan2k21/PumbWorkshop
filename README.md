# PumbWorkshop
## Overview
This RESTful API provides endpoints for managing animals in a platform. It allows users to perform various operations such as adding new animals, retrieving lists of animals, and filtering animals based on certain criteria.

## Table of Contents

- [Getting started](#getting-started)
- [API Description](#api-description)
- [Development](#development)

## Getting started
### Prerequisites

Java JDK(at least 8 version).

Any Java compatible IDE(Intelij, Eclipce, etc.).

Apache Maven (3.9.5) [Download Here](https://maven.apache.org/download.cgi)

Ensure that Java JDK and Maven are added to your system PATH variables.

## Installation
1. Clone the repository to your local machine
```
git clone https://github.com/RocketMan2k21/PumbWorkshop.git
```

2. Navigate to project folder in command line with `cd`.

3. Build the project:
```
mvn install
```

## Usage
To execude the code either open the project in your IDE and run Main.java file with built-in tool or in terminal:
 ```
javac App.java
java App
```


## Development
Run tests:
```
mvn test
```

## API Description
### Base URL  
````
http://localhost:4567
````
## Endpoints

### Get Animals

- **URL**: `/animals`
- **Method**: GET
- **Description**: Retrieves a list of animals from the platform.
- **Query Parameters**:
    - `category` (optional): Filter animals by category.
    - `type` (optional): Filter animals by type.
    - `sex` (optional): Filter animals by sex.
    - `sort_by` (optional): Sort animals in ascending order by a specific field.
- **Responses**:
    - `200 OK`: Successful operation. Returns a JSON array of animals.
    - `500 Internal Server Error`: Error processing request.
    - `501`: No animals found by given parameters
    - `502`: No animals found

### Upload Animals

- **URL**: `/animals/upload`
- **Method**: POST
- **Description**: Uploads a CSV or XML file containing animal data to add them to the platform.
- **Request Body**: CSV or XML file containing animal data.
- **Responses**:
    - `200 OK`: Successful operation. Data added successfully to the database.
    - `500 Internal Server Error`: Unsupported file format or error uploading file.
    - `501`: Error uploading a file
    - `503`: No valid data found in uploaded source

### Data Models

#### Animal

Represents an animal entity in the platform.

**Properties:**
- `id` (integer): Unique identifier for the animal.
- `name` (string): Name of the animal.
- `type` (string): Type of the animal (e.g., cat, dog).
- `sex` (string): Sex of the animal (e.g., male, female).
- `weight` (integer): Weight of the animal.
- `cost` (integer): Cost of the animal.
- `category` (integer): Category of the animal (e.g., 1, 2, 3, 4).
