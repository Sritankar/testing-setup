# Janitri Login Automation (TestNG + Selenium)

This project automates the login flow of the Janitri dashboard (`https://cloud.janitri.in/login`) using **Java**, **Selenium WebDriver**, and **TestNG**.

---

## 📁 Project Structure

```
janitri-login-automation/
├── pom.xml
├── testng.xml
├── src/
│   └── test/
│       ├── java/
│       │   └── com/janitri/
│       │       ├── base/           → Base test setup
│       │       ├── pages/          → Page Object Model (LoginPage.java)
│       │       └── tests/          → Test classes (LoginPageTest, ConnectivityTest)
│       └── resources/
```

---

## 🚀 How to Run the Tests

### 1. Prerequisites

- JDK 8 or higher
- Maven installed and configured
- Chrome browser installed
- ChromeDriver in system PATH (or configure via WebDriverManager)

### 2. Run All Tests

```bash
mvn clean test
```

### 3. Run Only Connectivity Test

Checks whether the login URL is reachable and returns a valid HTTP response.

```bash
mvn test -Dtest=ConnectivityTest
```

### 4. Run Only Login Page Tests

Performs login tests with valid, invalid, and blank credentials.

```bash
mvn test -Dtest=LoginPageTest
```

---

## ✅ Test Features

- **URL Health Check** – Validates if the login page is accessible (`ConnectivityTest.java`)
- **Login Scenarios** – Verifies:
  - Valid credentials
  - Invalid credentials
  - Blank email/password fields
- **Page Object Model (POM)** – Clean separation of logic for easier maintenance
- **Graceful Failure Handling** – Logs messages instead of hard failures for missing elements
- **Rich Debug Logs** – See exactly what happens at each step in the terminal

---

## 📄 Example Output

```
[INFO] Opening browser and navigating to https://cloud.janitri.in/login
[INFO] Entering valid email and password
[INFO] Clicking login button
[INFO] Assertion Passed: Login successful and redirected to dashboard
```

---

## 🛠️ Troubleshooting

- **Notification API errors in frontend**: If running against `https://dev-dash.janitri.in`, a JS bug may throw `Notification is not defined`. This is a bug in the frontend and unrelated to test code.
- **ChromeDriver issues**: Make sure the correct version of ChromeDriver is installed and matches your browser version.
- **Page not loading**: Check internet connectivity or use `ConnectivityTest.java` to verify site access.

---

## 📦 Dependencies

- Selenium WebDriver
- TestNG
- Maven Surefire Plugin

---

## 📬 Contact

For issues or contributions, reach out to the automation QA team.
