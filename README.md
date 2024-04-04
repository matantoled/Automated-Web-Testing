# Automation-Project

## Description
The Automation-Project is a sophisticated Java-based automated testing framework focused on web application testing. Designed to streamline and enhance the testing process, it features a modular structure with components for application logic, page objects, and utility functions, promoting maintainability and scalability. This framework addresses the complexities of automated web testing, enabling testers and developers to achieve more reliable and efficient workflows.

### Features
- **Application Logic**: Manages driver interactions, API calls, and user authentication with classes like `API_Helper1`, `ApplicationManager1`, etc.
- **Page Object Model (POM)**: Organizes web page elements and utilities in a clear structure, simplifying the management of new page elements.
- **Test Management**: Categorizes tests into various types for better organization and efficiency.

## Installation
**Prerequisites:**
- Java JDK
- Maven

**Setup:**
1. Clone the repository:
   ```
   git clone https://github.com/matantoled/Automation-Project
   ```
2. Install dependencies with Maven:
   ```
   mvn install
   ```

## Usage
Run tests using Maven:
```
mvn test
```

## Project Structure
- `src/main/java`:
  - `applogic`: Core application logic and helpers.
  - `models`: Data models for the application.
  - `pages`: Page objects and utilities for web interaction.
  - `util`: Utility functions and helpers.
  - `webdriver`: WebDriver management and configurations.
- `src/main/resources`: Configuration properties, grid settings, and logging configurations.
- `src/test/java`:
  - `tests`: Organized test scripts and cases.
- `src/test/resources`: TestNG configuration file.

## Acknowledgments
Special thanks to Hadassah Academic College Jerusalem and my lecturer, Ilana Bikel, for their invaluable support and guidance in this project.
