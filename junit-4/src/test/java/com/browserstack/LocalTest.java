package com.browserstack;

import org.junit.Test;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertTrue;

public class LocalTest extends BrowserStackJUnitTest {

    @Test
    public void test() throws URISyntaxException, IOException {
        SessionId sessionId = ((RemoteWebDriver) driver).getSessionId();
        try {
            driver.get("http://bs-local.com:45691/check");
            assertTrue(driver.getPageSource().contains("Up and running"));
            mark(sessionId, "passed", "Local content validated!");
        } catch (Throwable t) {
            mark(sessionId, "failed", "Local content not validated!");
            System.out.println("Exception: " + t);
        }
    }
}
