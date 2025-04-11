# RentRead

## Overview
The **RentRead** API service is a Spring Boot application designed for managing an online book rental system. The service uses MySQL for data persistence and handles user authentication and authorization, book management, and rental operations. 

## RentRead API Documentation

https://documenter.getpostman.com/view/32910654/2sAYQfEVUc

---

## Features
# **Authentication & Authorization**
- Basic Authentication for secure access.
- Roles: `USER` and `ADMIN`.

# **API Endpoints**
- **Public Endpoints**: Available for anyone (e.g., Registration, Login).
- **Private Endpoints**: Accessible only to authenticated users, with specific permissions.

# **Book Management**
- **Books**: Ability to add, view, update, and delete books.
- **Availability Status**: Tracks whether a book is available for rent.

# **Rental Management**
- Users can rent books, but can only have two active rentals at a time.
- Books can also be returned.

# **Error Handling**
- Common errors handled gracefully with appropriate HTTP codes (`404`, `400`, `409`).

---

## Entities

# **User**
- **User ID**: Unique identifier.
- **Email**: Unique email address for login.
- **Password**: Hashed and stored using BCrypt.
- **Role**: Default to `USER` or can be set to `ADMIN`.

# **Book**
- **Book ID**: Unique identifier.
- **Title**: The title of the book.
- **Author**: Author of the book.
- **Genre**: Genre category.
- **Availability Status**: Indicates availability for rental.

# **Rental**
- **User ID**: The user renting the book.
- **Book ID**: The book being rented.

---

## API Endpoints

### **POST /users/register**
- **Description**: Registers a new user.
- **Input**: Email, Password, First Name, Last Name, Role.
- **Response**: User registration confirmation.

### **POST /users/login**
- **Description**: User login using email and password.
- **Input**: Email, Password.
- **Response**: Token on successful authentication.

### **POST /books/{bookId}/rent**
- **Description**: Rent a book by ID.
- **Response**: Success message or error if the user has already rented two books.

### **POST /books/{bookId}/return**
- **Description**: Return a rented book by ID.
- **Response**: Success message or error if the user didn’t rent the book.

### **GET /books**
- **Description**: Retrieve a list of all books.
- **Response**: A list of books with their availability.

### **POST /books**
- **Description**: Only admin can add new books.
- **Response**: Success or error based on permissions.

### **PUT /books/{bookId}**
- **Description**: Only admin can update book details.
- **Response**: Success or error based on permissions.

### **DELETE /books/{bookId}**
- **Description**: Only admin can delete a book.
- **Response**: Success or error based on permissions.

---

## Error Handling

- **404 Not Found**: When a resource is not found (e.g., user, book).
- **409 Conflict**: When trying to rent a third book or register the same book multiple times.
- **400 Bad Request**: Invalid inputs or missing fields.
- **401 Unauthorized**: Authentication required.

## Exception Handling
The RentRead API includes various exceptions that are caught and handled in specific scenarios. The following are the most common exceptions:

1. **MaximumRentalLimitReachedException**: This exception is thrown when a user tries to rent more books than their allowed limit. This is managed with a `400 Bad Request` HTTP status code. 
2. **BookIsNotAvailableForRentException**: This occurs when a user tries to rent a book that is currently unavailable for rental. It is handled with a `409 Conflict` HTTP status code.
3. **BookAlreadyReturnedException**: If a user tries to return a book that has already been returned, this exception is thrown and responded with a `409 Conflict` HTTP status code.
4. **AccessDeniedException**: If a user attempts to access a resource without sufficient permissions, such as an admin-level endpoint when they are not authorized, this exception is thrown. The response is a `403 Forbidden` HTTP status code.
  
---

## Unit Tests
- **MockMvc** and **Mockito** are used for testing:
  - Verifies correct handling of CRUD operations.
  - Ensures correct validation during book rental.
  - Ensures proper exception handling.

---

## Getting Started

### Prerequisites:
- Java 11+
- Spring Boot
- MySQL Database

### Running the Project

To **generate a JAR file** and run the application:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-repository/rentread.git

2. **Navigate to the project directory**:
   ```bash
   cd rentread

3. **Build the project & generate jar file**:
    ```bash
    ./gradlew build
    
4. **Run the JAR file**:
    ```bash
    java -jar build/libs/rentread.jar

5. The application will be accessible at http://localhost:8080.
