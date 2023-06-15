package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import runners.BstackRunner;


public class BStackLocalTest extends BstackRunner {

    @Test
    void localTest() {
        driver.get("http://bs-local.com:45454/");
        String validateContent = driver.getTitle();
        Assertions.assertTrue(validateContent.contains("BrowserStack Local"), "Local content not validated!");
    }
}
