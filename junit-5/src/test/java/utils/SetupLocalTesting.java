package utils;

import com.browserstack.local.Local;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import runners.BstackRunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class SetupLocalTesting {
    private static volatile SetupLocalTesting instance;

    private final Local local = new Local();

    private SetupLocalTesting(Map<String, String> localOptions) {
        try {
            local.start(localOptions);
        } catch (Exception e) {
            throw new RuntimeException("Initialization of BrowserStack Local failed.", e);
        }
    }

    public static void createInstance(Map<String, String> args) {
        if (instance == null) {
            synchronized (SetupLocalTesting.class) {
                if (instance == null) {
                    instance = new SetupLocalTesting(args);
                    Runtime.getRuntime().addShutdownHook(new Closer(instance.local));
                }
            }
        }
    }

    public static SetupLocalTesting getInstance() {
        return instance;
    }

    private static class Closer extends Thread {
        private final Local local;

        public Closer(Local local) {
            this.local = local;
        }

        @Override
        public void run() {
            try {
                if (local.isRunning()) {
                    local.stop();
                }
            } catch (Exception e) {
                System.out.println("Error encountered while stopping BrowserStack Local { }"+e);
            }
        }
    }
}
