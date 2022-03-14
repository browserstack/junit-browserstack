package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import runners.WebDriverTest;
import utils.MarkSessionStatus;

public class SingleTest {

    @WebDriverTest
    void singleTest(WebDriver driver) {
        MarkSessionStatus sessionStatus = new MarkSessionStatus();
        try {
            driver.get("https://bstackdemo.com/");
            final WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.titleIs("StackDemo"));
            String product_name = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='1']/p"))).getText();
            WebElement cart_btn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='1']/div[4]")));
            cart_btn.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("float-cart__content")));
            final String product_in_cart = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='__next']/div/div/div[2]/div[2]/div[2]/div/div[3]/p[1]"))).getText();
            if (product_name.equals(product_in_cart)) {
                sessionStatus.markTestStatus("passed", "Product has been successfully added to the cart!", driver);
            } else {
                sessionStatus.markTestStatus("failed", "There was some issue!", driver);
            }
        } catch (Exception e) {
            sessionStatus.markTestStatus("failed", "There was some issue!", driver);
            System.out.println("Exception: " + e.getMessage());
        }
        driver.quit();
    }

    //@WebDriverTest
    void bstackTest(WebDriver driver) {
        driver.get("https://bstackdemo.com/");
        System.out.println("Test 1: " + Thread.currentThread().getName());
        driver.quit();
    }
}
