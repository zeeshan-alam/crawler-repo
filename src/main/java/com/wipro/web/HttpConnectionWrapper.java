package com.wipro.web;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.wipro.web.CrawlerConstants.*;

public class HttpConnectionWrapper {

    private String url;
    private HttpURLConnection connection;


    public HttpConnectionWrapper(String url) {
        this.url = url;
    }

    public String getPageContent() {
        StringBuilder response = new StringBuilder();
        try {
            initialiseConnection();

            connection.setRequestMethod(HTTP_METHOD);
            connection.setRequestProperty(USER_AGENT, USER_AGENT_VALUE);
            int responseCode = connection.getResponseCode();

            if (responseCode != -1) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    private void initialiseConnection() throws IOException {
        if (this.connection == null) {
            URL obj = new URL(this.url);
            connection = (HttpURLConnection) obj.openConnection();
        }
    }

    protected void setConnection(HttpURLConnection connection) {
        this.connection = connection;
    }
}
