package runners;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.SetupLocalTesting;

import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Stream;

public class BstackRunner implements TestTemplateInvocationContextProvider {
    public WebDriver driver;
    public String username, accesskey, server;
    private JSONObject mainConfig;
    private JSONArray platformConfig;
    private HashMap<String, Object> commonCapsConfig;

    public BstackRunner() {
        this.username = setupCredsAndServer().get("username");
        this.accesskey = setupCredsAndServer().get("accesskey");
        this.server = setupCredsAndServer().get("server");
    }

    public HashMap<String, String> setupCredsAndServer() {
        try {
            if (System.getProperty("config") != null) {
                JSONParser parser = new JSONParser();
                mainConfig = (JSONObject) parser.parse(new FileReader("src/test/resources/conf/" + System.getProperty("config")));
            }
            server = (String) mainConfig.get("server");
            username = System.getenv("BROWSERSTACK_USERNAME");
            if (username == null) {
                username = (String) mainConfig.get("user");
            }
            accesskey = System.getenv("BROWSERSTACK_ACCESS_KEY");
            if (accesskey == null) {
                accesskey = (String) mainConfig.get("key");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        HashMap<String, String> creds = new HashMap<>();
        creds.put("username", username);
        creds.put("accesskey", accesskey);
        creds.put("server", server);
        return creds;
    }

    @Override
    public boolean supportsTestTemplate(ExtensionContext extensionContext) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext extensionContext) {
        List<TestTemplateInvocationContext> desiredCapsInvocationContexts = new ArrayList<>();

        try {
            platformConfig = (JSONArray) mainConfig.get("environments");
            commonCapsConfig = (HashMap<String, Object>) mainConfig.get("capabilities");

            for (Object platform : platformConfig) {
                DesiredCapabilities capabilities = new DesiredCapabilities();
                Map<String, String> envCapabilities = (Map<String, String>) platform;
                capabilities.setCapability("browserstack.source", "junit-5:sample-selenium-3:v1.0");
                Iterator commonCapsIterator = commonCapsConfig.entrySet().iterator();
                while (commonCapsIterator.hasNext()) {
                    Map.Entry capsName = (Map.Entry) commonCapsIterator.next();
                    capabilities.setCapability((String) capsName.getKey(), capsName.getValue());
                }
                Iterator envCapsIterator = envCapabilities.entrySet().iterator();
                while (envCapsIterator.hasNext()) {
                    Map.Entry pair = (Map.Entry) envCapsIterator.next();
                    capabilities.setCapability((String) pair.getKey(), pair.getValue());
                }
                //Initializing local testing connection
                if (capabilities.getCapability("browserstack.local") != null && capabilities.getCapability("browserstack.local").toString().equals("true")) {
                    HashMap<String, String> localOptions = new HashMap<>();
                    localOptions.put("key", accesskey);
                    //Add more local options here, e.g. forceLocal, localIdentifier, etc.
                    SetupLocalTesting.createInstance(localOptions);
                }
                desiredCapsInvocationContexts.add(invocationContext(capabilities));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return desiredCapsInvocationContexts.stream();
    }

    private TestTemplateInvocationContext invocationContext(DesiredCapabilities caps) {
        return new TestTemplateInvocationContext() {

            @Override
            public List<Extension> getAdditionalExtensions() {

                return Collections.singletonList(new ParameterResolver() {
                    @Override
                    public boolean supportsParameter(ParameterContext parameterContext,
                                                     ExtensionContext extensionContext) {
                        return parameterContext.getParameter().getType().equals(WebDriver.class);
                    }

                    @Override
                    public Object resolveParameter(ParameterContext parameterContext,
                                                   ExtensionContext extensionContext) {
                        try {
                            driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + "@" + server + "/wd/hub"), caps);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        return driver;
                    }
                });
            }
        };
    }
}
