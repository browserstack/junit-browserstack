package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import runners.WebDriverTest;
import utils.MarkSessionStatus;


public class LocalTest {

    @WebDriverTest
    void localTest(WebDriver driver) {
        SessionId sessionId = ((RemoteWebDriver) driver).getSessionId();
        MarkSessionStatus sessionStatus = new MarkSessionStatus(sessionId);

        try {
            driver.get("http://localhost:45691/check");
            String validateContent = driver.findElement(By.cssSelector("body")).getText();
            if (validateContent.contains("Up and running")) {
                sessionStatus.markTestStatus("passed", "Local content validated!");
            } else {
                sessionStatus.markTestStatus("failed", "Local content not validated!");
            }
        } catch (Exception e) {
            sessionStatus.markTestStatus("failed", "There was some issue!");
            System.out.println(e.getMessage());
        }
        driver.quit();
    }
}