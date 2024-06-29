# Backend Assignment

## Overview
This project consists of APIs for user authentication, profile updates, coin data retrieval, and logging mechanisms for API requests and user actions.

## Table of Contents
1. [API Documentation](#api-documentation)
2. [Database Schema](#database-schema)
3. [Docker Setup](#docker-setup)

## API Documentation

### Sign up request
**Method:** `POST`  
**Endpoint:** `http://localhost:8084/backend_assignment/auth/sign-up`

**Headers:**
- `X_PO_AUTH_KEY: yourPredefinedAuthKey`
- `X_PO_ORIGIN: yourPredefinedOrigin`

**Request Body:**
```json
{
  "firstName": "jgdg",
  "lastName": "Doe",
  "email": "forrahul8@gmail.com",
  "mobile": "1234567890",
  "username": "jhondoe120",
  "password": "P@ssowrd12"
}

Update Api

Method: PATCH
Endpoint: http://localhost:8084/backend_assignment/user-profile/update

Headers:

    X_PO_AUTH_KEY: yourPredefinedAuthKey (disabled)
    X_PO_ORIGIN: yourPredefinedOrigin

Request Body:

json

{
  "firstName": "Rahul",
  "lastName": "Saha",
  "mobile": "9679165255",
  "password": "P@ssw0rd!"
}

Login Request

Method: POST
Endpoint: http://localhost:8084/backend_assignment/auth/login

Headers:

    X_PO_AUTH_KEY: yourPredefinedAuthKey
    X_PO_ORIGIN: yourPredefinedOrigin

Request Body:

json

{
  "username": "jhondoe120",
  "password": "P@ssowrd12"
}

Coin View

Method: GET
Endpoint: http://localhost:8084/backend_assignment/coins-view/get-coins-data?symbol=test

Headers:

    X_PO_AUTH_KEY: yourPredefinedAuthKey (disabled)
    X_PO_ORIGIN: yourPredefinedOrigin

Query Parameter:

    symbol: test

Database Schema
Users Table

sql

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    mobile VARCHAR(15),
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE INDEX idx_email_users ON users(email);
CREATE INDEX idx_username_users ON users(username);

API Request Logs Table

sql

CREATE TABLE api_request_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    api_type VARCHAR(50) NOT NULL,
    request_url VARCHAR(200) NOT NULL,
    request_payload TEXT,
    response_payload TEXT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

Audit Log Table

sql

CREATE TABLE audit_log (
    id BIGINT NOT NULL AUTO_INCREMENT,
    request_uri VARCHAR(255) NOT NULL,
    client_id VARCHAR(255),
    username VARCHAR(255),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    additional_info TEXT,
    user_agent VARCHAR(255),
    client_ip_address VARCHAR(50),
    PRIMARY KEY (id)
);

User Action Attempts Table

sql

CREATE TABLE user_action_attempts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    action_attempt INT NOT NULL DEFAULT 0,
    client_ip VARCHAR(255) NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (username) REFERENCES users(username)
);

Docker Setup

To build and run the Docker containers, execute the following commands:
Build and Start Containers

bash

docker-compose up -d --build

or

bash

docker-compose up --build

View Backend Logs

bash

docker logs -f springbootassign

Replace <springbootcontainer> with the actual container name for your Spring Boot application.
