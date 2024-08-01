# Task Management API

## API Documentation

You can view the API documentation [here](https://app.swaggerhub.com/apis/riabikinroman/TodoAPI/1.0.0).

## Overview

This API allows you to manage tasks and users with the following endpoints:

### Auth Endpoints
- **POST /auth/login**: Authenticate a user and return a JWT token
- **POST /auth/register**: Register a new user

### Task Endpoints
- **GET /api/tasks**: Get all tasks
- **POST /api/tasks**: Create a new task
- **GET /api/tasks/{id}**: Get a specific task by ID
- **PUT /api/tasks/{id}**: Update a specific task
- **DELETE /api/tasks/{id}**: Delete a specific task
- **GET /api/tasks/{taskId}/steps**: Get all steps for a specific task
- **POST /api/tasks/{taskId}/steps**: Add a step to a specific task
- **GET /api/tasks/steps/{stepId}**: Get a specific step by ID
- **PUT /api/tasks/steps/{stepId}**: Update a specific step
- **DELETE /api/tasks/steps/{stepId}**: Delete a specific step

### User Endpoints
- **GET /api/users**: Get all users
- **POST /api/users**: Create a new user
- **GET /api/users/{userId}**: Get a specific user by ID
- **PUT /api/users/{userId}**: Update a specific user
- **DELETE /api/users/{userId}**: Delete a specific user