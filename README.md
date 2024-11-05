# DBToyStorage

DBToyStorage is a desktop application for managing toy inventory and sales using PostgreSQL as the database. The project leverages JavaFX for the graphical user interface and Spring framework for application logic and database interaction. The styling of the application is enhanced using AtlantaFX.

## Table of Contents

- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Database Schema](#database-schema)
- [Technologies Used](#technologies-used)
- [Contributing](#contributing)
- [License](#license)

## Getting Started

These instructions will guide you on how to set up and run the DBToyStorage application on your local machine for development and testing purposes.

### Prerequisites

- Java 21
- PostgreSQL 16.2-1 or higher

### Installation

1. **Clone the repository:**

   ```sh
   git clone https://github.com/k1fl1k/DBToyStorage.git
   cd DBToyStorage
   ```

2. **Set up PostgreSQL:**

   Create a database and user for the application. Run the following SQL commands in your PostgreSQL client:

   ```sql
   CREATE DATABASE toystorage;
   ```

3. **Configure the application:**

   Update the `application.properties` file with your PostgreSQL database credentials:

   ```properties
   db.url=jdbc:postgresql://localhost:5432/toystorage
   db.username=postgres
   db.password=postgres
   db.pool.size=5
   ```

4. **Build the project:**

   Use Maven to build the project:

   ```sh
   mvn clean install
   ```

5. **Run the application:**

   Start the application using Maven:

   ```sh
   mvn spring-boot:run
   ```

## Usage

Upon launching the application, you will be presented with a user-friendly interface to manage toy inventory and sales. You can add, update, delete, and view toys and their details.

## Database Schema

The PostgreSQL database schema consists of the following tables:

- **cart**
- **category**
- **client**
- **manufacture**
- **sections**
- **toy**
- **users**

DDL and DML scripts are located in the `src/main/resources` directory for easy database setup and initialization.

## Technologies Used

- **Java 21**
- **PostgreSQL**
- **Spring Boot**
- **JavaFX**
- **AtlantaFX**
- **Maven**
- **Logger**

## Contributing

Contributions are welcome! Please fork the repository and create a pull request with your changes. Ensure your code follows the project's coding standards and includes appropriate tests.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
