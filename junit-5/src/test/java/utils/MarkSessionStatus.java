package utils;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.openqa.selenium.remote.SessionId;
import runners.BstackRunner;

import java.net.URI;
import java.util.ArrayList;

public class MarkSessionStatus extends BstackRunner {
    SessionId sessionId;

    public MarkSessionStatus(SessionId sessionId) {
        this.sessionId = sessionId;
    }

    public void markTestStatus(String status, String reason) {
        try {
            URI uri = new URI("https://" + username + ":" + accesskey + "@api.browserstack.com/automate/sessions/" + sessionId + ".json");
            HttpPut putRequest = new HttpPut(uri);

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add((new BasicNameValuePair("status", status)));
            nameValuePairs.add((new BasicNameValuePair("reason", reason)));
            putRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpClientBuilder.create().build().execute(putRequest);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
