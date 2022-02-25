package com.browserstack;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

public class SingleTest extends BrowserStackJUnitTest {

  @Test
  public void test() throws Exception {
    SessionId sessionId = ((RemoteWebDriver) driver).getSessionId();
    try{
      driver.get("https://www.google.com/ncr");
      WebElement element = driver.findElement(By.name("q"));
      element.sendKeys("BrowserStack");
      element.submit();
      Thread.sleep(5000);
      assertTrue(driver.getTitle().matches("(?i)BrowserStack - Google Search"));
      mark(sessionId, "passed","Title matches!");
    }catch (AssertionError e){
      mark(sessionId, "failed","Title does not match!");
      System.out.println("Exception: "+e);
    }
  }
}
