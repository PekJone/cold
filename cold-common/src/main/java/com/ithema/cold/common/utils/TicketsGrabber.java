package com.ithema.cold.common.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-08-19  16:38
 */
public class TicketsGrabber {
    private static final String LOGIN_URL = "www.login.com";
    private static final String QUERY_URL = "www.query.com";

    private static final String SUBMIT_URL = "www.SUBMIT.COM";

    private static String authToken = "";
    private static HttpClient httpClient = HttpClients.createDefault();

    public static void main(String[] args) {
        if (login("username","password")){
            System.out.println("登录成功");
            Timer timer = new Timer();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    checkTickets();
                }
            },0,3000);
        }else {
            System.out.println("登录失败");
        }
    }

    private static boolean login(String username, String password) {
        try {
            HttpPost request = new HttpPost(LOGIN_URL);
            JSONObject credential = new JSONObject();
            credential.put("username",username);
            credential.put("password",password);
            request.setEntity(new StringEntity(credential.toString()));
            request.setHeader("Content-type","application/json");

            HttpResponse response = httpClient.execute(request);
            String responseBody = EntityUtils.toString(response.getEntity());
            if (response.getStatusLine().getStatusCode()==200){
                JSONObject jsonObject = new JSONObject(responseBody);
                authToken = jsonObject.getString("token");
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private static void checkTickets() {
        try {
            HttpGet request = new HttpGet(QUERY_URL+"?date=2025-08-19&type=1");
            request.setHeader("Authorization","Bearer"+authToken);

            HttpResponse httpResponse = httpClient.execute(request);
            String responseBody = EntityUtils.toString(httpResponse.getEntity());
            JSONObject jsonObject = new JSONObject(responseBody);

            int remaining = jsonObject.getInt("remaining");

            if(remaining>0){
                System.out.println("检测到有余票，尝试下单");
                submitOrder();
            }else {
                System.out.println("无语票，继续监控");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void submitOrder() {

        try {
            HttpPost request = new HttpPost(SUBMIT_URL);
            JSONObject order = new JSONObject();
            order.put("date","2025-08-19");
            order.put("type",1);
            order.put("count",2);
            request.setEntity(new StringEntity(order.toString()));
            request.setHeader("Content-type","application/json");
            request.setHeader("Authorization",authToken);
            HttpResponse response = httpClient.execute(request);
            String responseBody = EntityUtils.toString(response.getEntity());

            if(response.getStatusLine().getStatusCode()==200){
                System.out.println("下单成功");
                System.exit(0);
            }else {
                System.out.println("下单失败");
            }

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
