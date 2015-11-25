package droidcon.cart.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ContentDownloader {

    public String fetchResponse(String endpoint) {

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(endpoint);
        String strJSONData;

        HttpResponse resp = null;
        try {
            resp = httpClient.execute(httpGet);
            strJSONData = processEntity(resp.getEntity());

			return strJSONData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

	private String processEntity(HttpEntity entity)
            throws IllegalStateException, IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(entity
                .getContent()));
        String line, result = "";

        while ((line = br.readLine()) != null)
            result += line;
        br.close();
        return result;
    }
}
