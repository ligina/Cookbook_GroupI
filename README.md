# Digital Cookbook

## Overview

The Digital Cookbook is an application for showing and building recipes. Users can see available recipes and search for recipes. The cookbook will display detailed information including ingredients, needed time for preparation and cooking, and pictures of the product. Additionally, users can create, edit and delete recipes if they want.

The application features user authentication, recipe management with nutritional analysis, image upload capabilities, unit conversion tools, and serving size adjustments. Built with JavaFX for the user interface and MyBatis for database operations.

## Environment

- **OS**: Windows/MacOS/Linux
- **SDK**: Java 18+
- **IDE**: Eclipse/IntelliJ IDEA
- **Database**: MySQL Server 8+
- **Build Tool**: Maven (https://maven.apache.org)
- **UI Framework**: JavaFX 20

**Before running the project please configure your database first**

## Database Configuration

Create a database called `cookbook` in MySQL and execute "use cookbook" command:

```sql
CREATE DATABASE cookbook;
USE cookbook;
```

Run `cookbook.sql` on your MySQL bash or MySQL Workbench:

```bash
mysql -u root -p cookbook < src/sql/cookbook.sql
```

Modify parameters including username in `src->main->resources->jdbc.properties` file to adapt to your local database:

```properties
database.driver = com.mysql.cj.jdbc.Driver
database.url = jdbc:mysql://localhost:3306/cookbook
database.username = root
database.password = your_password
```

Connect to your local MySQL to verify the setup.

## How to Run the Maven Project

In Maven projects, IntelliJ IDEA or Eclipse usually automatically detects and downloads the required dependencies.

### Using Eclipse:
1. Open Eclipse and import project
2. Select maven project
3. Choose this project directory
4. Wait for maven project build during which Eclipse will automatically recognize the project and start downloading the necessary dependencies
5. Run `MainApplication.java` file in `src->main->java->main` folder

### Using IntelliJ IDEA:
1. Open IntelliJ IDEA
2. File -> Open -> Select project directory
3. Wait for Maven to download dependencies
4. Run `MainApplication.java` in `src->main->java->main` folder

### Using Command Line:
```bash
mvn clean compile
mvn javafx:run
```

## Features

- **User Authentication**: Register and login functionality
- **Recipe Management**: Create, edit, delete and view recipes
- **Ingredient Management**: Add ingredients with quantities and units
- **Nutrition Analysis**: Automatic calculation of nutritional information
- **Image Upload**: Upload and display recipe images
- **Search Function**: Search recipes by name
- **Unit Conversion**: Built-in cooking unit converter
- **Serving Adjustment**: Adjust ingredient quantities based on serving size

## Project Structure

```
src/main/java/
├── main/                    # Application entry point
├── view/                    # JavaFX view classes
├── control/                 # FXML controllers
├── model/                   # Business logic and services
├── service/                 # Service layer
├── dao/mappers/            # MyBatis mappers and entities
└── config/                 # Configuration classes

src/main/resources/
├── jdbc.properties         # Database configuration
├── mybatis-config.xml      # MyBatis configuration
└── *.fxml                  # JavaFX FXML files
```

## Database Schema

The application uses four main tables:
- `users` - User authentication information
- `recipe` - Recipe basic information and nutrition data
- `ingredient` - Recipe ingredients with quantities
- `preparationstep` - Cooking instructions

## Testing

Run tests using Maven:
```bash
mvn test
```

The project includes unit tests for login functionality, recipe creation, and UI components using JUnit 5, TestFX, and Mockito.

## Tips

In this project package it has already run environments for both Eclipse and IntelliJ IDEA, you can run the project in either IDE. And you must have a nice cooking experience with this application!!!