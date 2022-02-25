package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class MarkSessionStatus {
    public void markTestStatus(String status, String reason, WebDriver driver){
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \""+ status + "\", \"reason\": \"" + reason + "\"}}");
    }
}
