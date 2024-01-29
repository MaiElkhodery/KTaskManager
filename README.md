# Ktor App with KMongo

#### This Kotlin application is built with Ktor and KMongo, featuring a local database with an asynchronous driver. The project includes two essential APIs for user authentication: login and signup, utilizing JWT (JSON Web Token) for secure authentication. Authenticated users can access the Tasks API, allowing CRUD operations on tasks with additional filtering capabilities based on priority, status, or a combination of both.

## Features:
+ ### User Authentication:
 - **Login API**: Authenticate users securely using the login API.
 - **Signup API**: Allow users to register with the signup API.

+ ### Task Management API:
  - **Create**: Authenticated users can create new tasks.
  - **Delete**: Authenticated users can delete tasks by ID.
  - **Update**: Authenticated users can update task details.
  - **Get**: Retrieve a list of all tasks and filtering based on priority, status, or both.

+ ### Security:
  - **JWT Authentication**: Utilize JSON Web Tokens for secure user authentication.
  - **Token-Based Access**: Ensure authenticated access to the Tasks API.

+ ### Database Connectivity:
  - **KMongo Integration**: Seamlessly integrate KMongo for MongoDB connectivity.
  - **Async Driver**: Leverage asynchronous drivers for efficient database operations.

## Getting Started
 **1. Clone the Repository**
 **2. Configure Database**:
  - Ensure MongoDB is installed and running locally.
  - Update database configurations in application.conf
 **3. Run the Application**
 **4. Access APIs**:
    - Login: POST /login
    - Signup: POST /signup
    - Tasks: GET /tasks, POST /tasks, PUT /tasks, DELETE /tasks/{id}
