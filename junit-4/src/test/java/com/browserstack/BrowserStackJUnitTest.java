package com.browserstack;

import com.browserstack.local.Local;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

@RunWith(Parallelized.class)
public class BrowserStackJUnitTest {
    public static String username, accessKey;
    private static JSONObject config;
    public WebDriver driver;
    public HashMap<String, String> bstackOptions;
    private static Object lock = new Object();
    private static Integer parallels = 0;

    @Parameter(value = 0)
    public int taskID;
    private Local bsLocal;

    @Parameters
    public static Iterable<? extends Object> data() throws Exception {
        List<Integer> taskIDs = new ArrayList<Integer>();

        if (System.getProperty("config") != null) {
            JSONParser parser = new JSONParser();
            config = (JSONObject) parser
                    .parse(new FileReader("src/test/resources/conf/" + System.getProperty("config")));
            int envs = ((JSONArray) config.get("environments")).size();

            for (int i = 0; i < envs; i++) {
                taskIDs.add(i);
            }
        }
        return taskIDs;
    }

    public static void mark(SessionId sessionID, String status, String reason) throws URISyntaxException, IOException {
        URI uri = new URI("https://" + username + ":" + accessKey + "@api.browserstack.com/automate/sessions/" + sessionID + ".json");
        HttpPut putRequest = new HttpPut(uri);

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add((new BasicNameValuePair("status", status)));
        nameValuePairs.add((new BasicNameValuePair("reason", reason)));
        putRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        HttpClientBuilder.create().build().execute(putRequest);
    }

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        JSONArray envs = (JSONArray) config.get("environments");
        bstackOptions = new HashMap();

        MutableCapabilities capabilities = new MutableCapabilities();

        Map<String, Object> envCapabilities = (Map<String, Object>) envs.get(taskID);
        Iterator<Map.Entry<String, Object>> it = envCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> pair = it.next();
            if ("bstack:options".equals(pair.getKey())) {
                bstackOptions.putAll((Map<? extends String, ? extends String>) pair.getValue());
            } else {
                capabilities.setCapability(pair.getKey(), pair.getValue());
            }
        }

        Map<String, Object> commonCapabilities = (Map<String, Object>) config.get("capabilities");
        it = commonCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> pair = it.next();
            if ("bstack:options".equals(pair.getKey())) {
                bstackOptions.putAll((Map<? extends String, ? extends String>) pair.getValue());
            } else {
                capabilities.setCapability(pair.getKey(), pair.getValue());
            }
        }
        bstackOptions.put("source","junit-4:sample-master:v1.1");
        capabilities.setCapability("bstack:options", bstackOptions);

        username = System.getenv("BROWSERSTACK_USERNAME");
        if (username == null) {
            username = (String) config.get("userName");
        }

        accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
        if (accessKey == null) {
            accessKey = (String) config.get("accessKey");
        }

        this.checkAndStartBrowserStackLocal(capabilities, accessKey);

        driver = new RemoteWebDriver(
                new URL("https://" + username + ":" + accessKey + "@" + config.get("server") + "/wd/hub"), capabilities);
    }

    public void checkAndStartBrowserStackLocal(MutableCapabilities capabilities, String accessKey) throws Exception {
        if (bsLocal != null) {
            return;
        }
        JSONObject localCaps = new JSONObject(bstackOptions);

        synchronized (lock) {
            parallels++;
            if ((bsLocal == null || !bsLocal.isRunning()) && capabilities.getCapability("bstack:options") != null
                    && localCaps.get("local") != null
                    && ((Boolean) localCaps.get("local")) == true) {
                bsLocal = new Local();
                Map<String, String> options = new HashMap<String, String>();
                options.put("key", accessKey);
                try {
                    bsLocal.start(options);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @After
    public void tearDown() throws Exception {
        synchronized (lock){
            parallels--;
            driver.quit();
            if (bsLocal != null && parallels == 0) bsLocal.stop();
        }
    }
}
