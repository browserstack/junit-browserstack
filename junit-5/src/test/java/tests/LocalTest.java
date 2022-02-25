package tests;

import utils.MarkSessionStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import runners.WebDriverTest;

public class LocalTest {

    @WebDriverTest
    void localTest(WebDriver driver) {
        MarkSessionStatus sessionStatus = new MarkSessionStatus();

        try{
            driver.get("http://localhost:45691/check");
            String validateContent = driver.findElement(By.cssSelector("body")).getText();
            if(validateContent.contains("Up and running"))
                sessionStatus.markTestStatus("passed", "Local content validated!",driver);
            else
                sessionStatus.markTestStatus("failed", "Local content not validated!",driver);
        }catch (Exception e){
            sessionStatus.markTestStatus("failed", "There was some issue!",driver);
            System.out.println(e.getMessage());
        }
        driver.quit();
    }
}