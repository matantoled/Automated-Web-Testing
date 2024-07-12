# Automated-Web-Testing

## Description
The Automated-Web-Testing is a sophisticated Java-based automated testing framework focused on web application testing. Designed to streamline and enhance the testing process, it features a modular structure with components for application logic, page objects, and utility functions, promoting maintainability and scalability. This framework addresses the complexities of automated web testing, enabling testers and developers to achieve more reliable and efficient workflows.

## Video Demonstration
![image](https://github.com/matantoled/Automated-Web-Testing/assets/75612523/abe97384-4f27-43f2-af2d-669f0aae6452)

The video below demonstrates the Automated-Web-Testing in action. It shows the program executing a series of automated tasks on a web application: registering a new user account, signing in, selecting a smartphone, adding it to the shopping cart, completing the purchase, and filling in the necessary shipping details to have the product delivered to a specified address. [Video Demonstration](https://www.youtube.com/watch?v=T4ObcdOL7qQ)

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
   git clone https://github.com/matantoled/Automated-Web-Testing
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
