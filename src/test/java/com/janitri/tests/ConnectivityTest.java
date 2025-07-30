package com.janitri.tests;

import java.net.HttpURLConnection;
import java.net.URL;

import org.testng.annotations.Test;

import com.janitri.base.BaseTest;

public class ConnectivityTest extends BaseTest {
    
    @Test(priority = 0, description = "Test URL connectivity before running main tests")
    public void testUrlConnectivity() {
        System.out.println("\n=== Connectivity Test ===");
        
        try {
            URL url = new URL(BASE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000); // 10 seconds
            connection.setReadTimeout(10000);
            
            int responseCode = connection.getResponseCode();
            System.out.println("HTTP Response Code: " + responseCode);
            System.out.println("Response Message: " + connection.getResponseMessage());
            
            if (responseCode >= 200 && responseCode < 400) {
                System.out.println("✓ URL is accessible");
            } else {
                System.out.println("⚠ URL returned non-success status: " + responseCode);
            }
            
        } catch (Exception e) {
            System.out.println("⚠ URL connectivity test failed: " + e.getMessage());
            System.out.println("This could indicate:");
            System.out.println("  1. Network connectivity issues");
            System.out.println("  2. Firewall blocking access");
            System.out.println("  3. Test environment is down");
            System.out.println("  4. URL may require authentication");
        }
        
        // Also test with Selenium
        try {
            System.out.println("\nTesting with Selenium WebDriver...");
            getDriver().get(BASE_URL);
            Thread.sleep(5000); // Wait 5 seconds
            
            String currentUrl = getDriver().getCurrentUrl();
            String title = getDriver().getTitle();
            String pageSource = getDriver().getPageSource();
            
            System.out.println("Final URL: " + currentUrl);
            System.out.println("Page Title: " + title);
            System.out.println("Page Source Length: " + pageSource.length());
            
            if (pageSource.length() > 100) {
                System.out.println("✓ Page content loaded successfully");
                
                // Look for common login elements
                boolean hasInputs = pageSource.toLowerCase().contains("<input");
                boolean hasPassword = pageSource.toLowerCase().contains("password");
                boolean hasLogin = pageSource.toLowerCase().contains("login");
                
                System.out.println("Has input elements: " + hasInputs);
                System.out.println("Has password field: " + hasPassword);
                System.out.println("Contains 'login': " + hasLogin);
                
            } else {
                System.out.println("⚠ Page content appears to be minimal");
            }
            
        } catch (Exception e) {
            System.out.println("⚠ Selenium test failed: " + e.getMessage());
        }
    }
}