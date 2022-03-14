package com.browserstack;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertTrue;

public class SingleTest extends BrowserStackJUnitTest {

    @Test
    public void test() throws Exception {
        SessionId sessionId = ((RemoteWebDriver) driver).getSessionId();
        try {
            driver.get("https://bstackdemo.com/");
            final WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.titleIs("StackDemo"));
            String product_name = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='1']/p"))).getText();
            WebElement cart_btn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='1']/div[4]")));
            cart_btn.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("float-cart__content")));
            final String product_in_cart = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='__next']/div/div/div[2]/div[2]/div[2]/div/div[3]/p[1]"))).getText();
            assertTrue(product_name.matches(product_in_cart));
            mark(sessionId, "passed", "Product has been successfully added to the cart!");
        } catch (AssertionError e) {
            mark(sessionId, "failed", "There was some issue!");
            System.out.println("Exception: " + e);
        }
    }
}
