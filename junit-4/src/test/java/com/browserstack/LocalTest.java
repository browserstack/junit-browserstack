package com.browserstack;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

public class LocalTest extends BrowserStackJUnitTest {

  @Test
  public void test() throws Exception {
    SessionId sessionId = ((RemoteWebDriver) driver).getSessionId();
    try{
      driver.get("http://bs-local.com:45691/check");

      assertTrue(driver.getPageSource().contains("Up and running"));
      mark(sessionId, "passed", "Local content validated!");

    }catch (AssertionError e){
      mark(sessionId, "failed","Local content not validated!");
      System.out.println("Exception: "+e);
    }
  }
}
