package runners;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.SetupLocalTesting;

import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Stream;

public class BstackRunner implements TestTemplateInvocationContextProvider {
    public WebDriver driver;
    public String username, accessKey, server;
    private JSONObject mainConfig;
    private JSONArray platformConfig;
    private Map<String, Object> commonCapsConfig;


    public BstackRunner() {
        this.username = setupCredsAndServer().get("username");
        this.accessKey = setupCredsAndServer().get("accesskey");
        this.server = setupCredsAndServer().get("server");
    }

    public HashMap<String, String> setupCredsAndServer() {
        try {
            if (System.getProperty("config") != null) {
                JSONParser parser = new JSONParser();
                mainConfig = (JSONObject) parser
                        .parse(new FileReader("src/test/resources/conf/" + System.getProperty("config")));
            }
            server = (String) mainConfig.get("server");
            username = System.getenv("BROWSERSTACK_USERNAME");
            if (username == null) {
                username = (String) mainConfig.get("userName");
            }
            accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
            if (accessKey == null) {
                accessKey = (String) mainConfig.get("accessKey");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        HashMap<String, String> creds = new HashMap();
        creds.put("username", username);
        creds.put("accesskey", accessKey);
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
            commonCapsConfig = (Map<String, Object>) mainConfig.get("capabilities");

            for (Object platform : platformConfig) {
                MutableCapabilities capabilities = new MutableCapabilities();
                HashMap<String, Object> bstackOptions = new HashMap<>();
                Map<String, Object> envCapabilities = (Map<String, Object>) platform;

                Iterator<Map.Entry<String, Object>> it = envCapabilities.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, Object> pair = it.next();
                    if ("bstack:options".equals(pair.getKey())) {
                        bstackOptions.putAll((Map<? extends String, ? extends String>) pair.getValue());
                    } else {
                        capabilities.setCapability(pair.getKey(), pair.getValue());
                    }
                }

                it = commonCapsConfig.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, Object> pair = it.next();
                    if ("bstack:options".equals(pair.getKey())) {
                        bstackOptions.putAll((Map<? extends String, ? extends String>) pair.getValue());
                    } else {
                        capabilities.setCapability(pair.getKey(), pair.getValue());
                    }
                }
                //Initializing local testing connection
                if (bstackOptions.containsKey("local")) {
                    HashMap<String, String> localOptions = new HashMap<>();
                    localOptions.put("key", accessKey);
                    //Add more local options here, e.g. forceLocal, localIdentifier, etc.
                    SetupLocalTesting.createInstance(localOptions);
                }
                bstackOptions.put("source","junit-5:sample-master:v1.1");
                capabilities.setCapability("bstack:options", bstackOptions);

                desiredCapsInvocationContexts.add(invocationContext(capabilities));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return desiredCapsInvocationContexts.stream();
    }

    private TestTemplateInvocationContext invocationContext(MutableCapabilities caps) {
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
                            driver = new RemoteWebDriver(new URL("https://" + username + ":" + accessKey + "@" + server + "/wd/hub"), caps);
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
