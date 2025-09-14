package com.ithema.cold.common.utils;

import com.sun.deploy.net.HttpUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-08-19  17:14
 */
public class HttpUtlConnectionExample {
    public static void main(String[] args) throws Exception {
        restTemplatePost();
    }

    private static void restTemplatePost() {
        RestTemplate restTemplate = new RestTemplate();
        Map<String,Object> requestBody = new HashMap<>();
        requestBody.put("title","foo");
        requestBody.put("body","bar");
        requestBody.put("userId",1);
        String result = restTemplate.postForObject("https://jsonplaceholder.typicode.com/posts", requestBody, String.class);
        System.out.println(result);
    }

    private static void restTemplateGet() {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts/1", String.class);
        System.out.println(result);
    }

    private static void responseHandlerMethod() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("https://jsonplaceholder.typicode.com/posts/1");
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = httpClient.execute(request,responseHandler);
        System.out.println("Response:"+responseBody);
    }

    private static void apacheHttpClientPost() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://jsonplaceholder.typicode.com/posts");
        httpPost.setHeader("Content-type","application/json");
        String jsonInput = "{\"title\": \"foo\", \"body\": \"bar\", \"userId\": 1}";
        httpPost.setEntity(new StringEntity(jsonInput));

        CloseableHttpResponse response = httpClient.execute(httpPost);
        System.out.println("Status code:"+response.getStatusLine().getStatusCode()+"=========================");
        System.out.println("Response"+EntityUtils.toString(response.getEntity()));
    }

    private static void apacheHttpClientGet() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("https://jsonplaceholder.typicode.com/posts/1");
        CloseableHttpResponse response = httpClient.execute(request);

        System.out.println("status code:"+response.getStatusLine().getStatusCode());
        System.out.println("Response:"+ EntityUtils.toString(response.getEntity()));
    }

    private static void javaPost() throws IOException {
        URL url = new URL("https://jsonplaceholder.typicode.com/posts");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(5000);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        String jsonInput = "{\"title\": \"foo\", \"body\": \"bar\", \"userId\": 1}";

        OutputStream os = connection.getOutputStream();
        byte[] bytes = jsonInput.getBytes(StandardCharsets.UTF_8);
        os.write(bytes,0,bytes.length);

        int response = connection.getResponseCode();
        System.out.println("ResponseCode:"+response);
        connection.disconnect();
    }

    private static void getMethod() throws IOException {
        URL url = new URL("https://jsonplaceholder.typicode.com/posts/1");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.setReadTimeout(5000);

        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode==HttpURLConnection.HTTP_OK){
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line ;
            StringBuilder response = new StringBuilder();
            while ((line=reader.readLine())!=null){
                response.append(line);
            }
            reader.close();
            System.out.println("Response"+response.toString());

        }
        httpURLConnection.disconnect();
    }
}
