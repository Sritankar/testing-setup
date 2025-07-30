package com.janitri.base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
    
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected static final String BASE_URL = "https://dev-dash.janitri.in/";
    
    public static WebDriver getDriver() {
        return driver.get();
    }
    
    @Parameters({"browser"})
    @BeforeMethod
    public void setUp(String browser) {
        setupDriver(browser.toLowerCase());
        configureDriver();
        
        try {
            System.out.println("Navigating to: " + BASE_URL);
            getDriver().get(BASE_URL);
            
            // Wait for page to load and print current URL
            Thread.sleep(3000);
            System.out.println("Current URL: " + getDriver().getCurrentUrl());
            System.out.println("Page Title: " + getDriver().getTitle());
            
        } catch (Exception e) {
            System.out.println("Error loading page: " + e.getMessage());
            System.out.println("Trying to continue with current page...");
        }
    }
    
    private void setupDriver(String browser) {
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-popup-blocking");
                chromeOptions.addArguments("--disable-web-security");
                chromeOptions.addArguments("--allow-running-insecure-content");
                chromeOptions.addArguments("--disable-extensions");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--remote-allow-origins=*");
                driver.set(new ChromeDriver(chromeOptions));
                break;
                
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addPreference("dom.webnotifications.enabled", false);
                driver.set(new FirefoxDriver(firefoxOptions));
                break;
                
            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }
    }
    
    private void configureDriver() {
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        getDriver().manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
    }
    
    @AfterMethod
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }
}