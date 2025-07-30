package com.janitri.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    
    private WebDriver driver;
    private WebDriverWait wait;
    
    // Page Elements using @FindBy annotations with multiple fallback locators
    @FindBy(xpath = "//input[@id='userId' or @name='userId' or @name='username' or @name='email' or @placeholder='User ID' or @placeholder='Username' or @placeholder='Email' or contains(@class, 'user') or contains(@class, 'email') or contains(@class, 'login')]")
    private WebElement userIdInput;
    
    @FindBy(xpath = "//input[@id='password' or @name='password' or @type='password' or @placeholder='Password' or contains(@class, 'password')]")
    private WebElement passwordInput;
    
    @FindBy(xpath = "//button[@type='submit'] | //button[contains(text(), 'Login')] | //button[contains(text(), 'Sign In')] | //input[@type='submit'] | //button[contains(@class, 'login')] | //button[contains(@class, 'submit')] | //*[@role='button' and (contains(text(), 'Login') or contains(text(), 'Sign'))]")
    private WebElement loginButton;
    
    @FindBy(xpath = "//span[contains(@class, 'eye')] | //i[contains(@class, 'eye')] | //button[contains(@class, 'password-toggle')] | //*[@data-testid='password-toggle'] | //*[contains(@class, 'toggle')] | //*[contains(@onclick, 'password')] | //span[contains(@class, 'show')] | //span[contains(@class, 'hide')]")
    private WebElement passwordToggleIcon;
    
    @FindBy(xpath = "//div[contains(@class, 'error') or contains(@class, 'alert')] | //span[contains(@class, 'error')] | //*[contains(text(), 'Invalid') or contains(text(), 'Error') or contains(text(), 'incorrect')]")
    private WebElement errorMessage;
    
    @FindBy(xpath = "//h1 | //h2 | //title | //*[contains(@class, 'title') or contains(@class, 'heading')]")
    private WebElement pageTitle;
    
    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
        
        // Debug: Print page source length and URL
        System.out.println("=== LoginPage Debug Info ===");
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page Title: " + driver.getTitle());
        System.out.println("Page Source Length: " + driver.getPageSource().length());
        
        // Try to find elements with different strategies
        findElementsWithMultipleStrategies();
    }
    
    private void findElementsWithMultipleStrategies() {
        System.out.println("=== Element Detection ===");
        
        // Try to find user input with various selectors
        String[] userSelectors = {
            "//input[@type='text']",
            "//input[@type='email']", 
            "//input[contains(@placeholder, 'user')]",
            "//input[contains(@placeholder, 'email')]",
            "//input[contains(@name, 'user')]",
            "//input[contains(@name, 'email')]",
            "//input[contains(@id, 'user')]",
            "//input[contains(@id, 'email')]",
            "//input[1]"
        };
        
        for (String selector : userSelectors) {
            try {
                var elements = driver.findElements(By.xpath(selector));
                if (!elements.isEmpty()) {
                    System.out.println("Found " + elements.size() + " elements with selector: " + selector);
                    for (int i = 0; i < elements.size(); i++) {
                        var elem = elements.get(i);
                        System.out.println("  Element " + i + ": tag=" + elem.getTagName() + 
                                         ", type=" + elem.getAttribute("type") + 
                                         ", name=" + elem.getAttribute("name") + 
                                         ", id=" + elem.getAttribute("id") +
                                         ", placeholder=" + elem.getAttribute("placeholder"));
                    }
                }
            } catch (Exception e) {
                // Continue to next selector
            }
        }
        
        // Try to find password input
        String[] passwordSelectors = {
            "//input[@type='password']",
            "//input[contains(@placeholder, 'password')]",
            "//input[contains(@name, 'password')]",
            "//input[contains(@id, 'password')]"
        };
        
        for (String selector : passwordSelectors) {
            try {
                var elements = driver.findElements(By.xpath(selector));
                if (!elements.isEmpty()) {
                    System.out.println("Found password elements with selector: " + selector + " (count: " + elements.size() + ")");
                }
            } catch (Exception e) {
                // Continue to next selector
            }
        }
        
        // Try to find any buttons
        String[] buttonSelectors = {
            "//button",
            "//input[@type='submit']",
            "//input[@type='button']",
            "//*[@role='button']"
        };
        
        for (String selector : buttonSelectors) {
            try {
                var elements = driver.findElements(By.xpath(selector));
                if (!elements.isEmpty()) {
                    System.out.println("Found buttons with selector: " + selector + " (count: " + elements.size() + ")");
                    for (var elem : elements) {
                        System.out.println("  Button text: '" + elem.getText() + "', type: " + elem.getAttribute("type"));
                    }
                }
            } catch (Exception e) {
                // Continue to next selector
            }
        }
    }
    
    // Utility Methods
    public void enterUserId(String userId) {
        wait.until(ExpectedConditions.elementToBeClickable(userIdInput));
        userIdInput.clear();
        userIdInput.sendKeys(userId);
    }
    
    public void enterPassword(String password) {
        wait.until(ExpectedConditions.elementToBeClickable(passwordInput));
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }
    
    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
    }
    
    public void clickPasswordToggle() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(passwordToggleIcon));
            passwordToggleIcon.click();
        } catch (Exception e) {
            System.out.println("Password toggle not found or not clickable");
        }
    }
    
    public boolean isLoginButtonEnabled() {
        wait.until(ExpectedConditions.visibilityOf(loginButton));
        return loginButton.isEnabled();
    }
    
    public boolean isPasswordMasked() {
        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        return passwordInput.getAttribute("type").equals("password");
    }
    
    public String getPasswordInputType() {
        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        return passwordInput.getAttribute("type");
    }
    
    public String getErrorMessage() {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            return errorMessage.getText();
        } catch (Exception e) {
            // Try alternative error message selectors
            try {
                WebElement altError = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[contains(text(), 'Invalid') or contains(text(), 'Error') or contains(text(), 'incorrect') or contains(text(), 'failed')]")));
                return altError.getText();
            } catch (Exception ex) {
                return "No error message found";
            }
        }
    }
    
    public boolean isErrorMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getPageTitle() {
        try {
            return driver.getTitle();
        } catch (Exception e) {
            return "Page title not found";
        }
    }
    
    public boolean isUserIdInputDisplayed() {
        try {
            // Try the PageFactory element first
            wait.until(ExpectedConditions.visibilityOf(userIdInput));
            return userIdInput.isDisplayed();
        } catch (Exception e) {
            System.out.println("PageFactory userIdInput not found, trying alternative selectors...");
            
            // Try alternative selectors
            String[] selectors = {
                "//input[@type='text']",
                "//input[@type='email']",
                "//input[contains(@placeholder, 'user') or contains(@placeholder, 'User')]",
                "//input[contains(@placeholder, 'email') or contains(@placeholder, 'Email')]",
                "//input[contains(@name, 'user') or contains(@name, 'email')]",
                "//input[contains(@id, 'user') or contains(@id, 'email')]",
                "//input[1]"  // First input on page
            };
            
            for (String selector : selectors) {
                try {
                    WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(selector)));
                    if (element.isDisplayed()) {
                        System.out.println("Found user input with selector: " + selector);
                        return true;
                    }
                } catch (Exception ex) {
                    // Continue to next selector
                }
            }
            
            System.out.println("No user input field found with any selector");
            return false;
        }
    }
    
    public boolean isPasswordInputDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(passwordInput));
            return passwordInput.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isPasswordToggleDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(passwordToggleIcon));
            return passwordToggleIcon.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public void clearAllFields() {
        userIdInput.clear();
        passwordInput.clear();
    }
    
    // Test Methods as required in the assignment
    public boolean testLoginButtonDisabledWhenFieldsAreEmpty() {
        clearAllFields();
        // Wait a moment for UI to update
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        boolean isDisabled = !isLoginButtonEnabled();
        System.out.println("Login button disabled when fields are empty: " + isDisabled);
        return isDisabled;
    }
    
    public boolean testPasswordMaskedButton() {
        enterPassword("testPassword");
        
        // Check initial state (should be masked)
        boolean initiallyMasked = isPasswordMasked();
        System.out.println("Password initially masked: " + initiallyMasked);
        
        // Click toggle if available
        if (isPasswordToggleDisplayed()) {
            clickPasswordToggle();
            try {
                Thread.sleep(500); // Wait for toggle to take effect
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            boolean afterToggle = isPasswordMasked();
            System.out.println("Password masked after toggle: " + afterToggle);
            
            // Toggle back
            clickPasswordToggle();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            boolean afterSecondToggle = isPasswordMasked();
            System.out.println("Password masked after second toggle: " + afterSecondToggle);
            
            return initiallyMasked && !afterToggle && afterSecondToggle;
        } else {
            System.out.println("Password toggle not found - checking basic masking functionality");
            return initiallyMasked;
        }
    }
    
    public String testInvalidLoginShowErrorMsg() {
        // Test with invalid credentials
        enterUserId("invalidUser123");
        enterPassword("invalidPassword123");
        clickLoginButton();
        
        // Wait for potential error message
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        String errorMsg = getErrorMessage();
        System.out.println("Error message displayed: " + errorMsg);
        return errorMsg;
    }
}