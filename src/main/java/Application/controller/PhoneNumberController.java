package Application.controller;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class PhoneNumberController {

    public boolean controllerExistPhoneNumber(String chatId) {
        try {
            HttpPost httpPost = new HttpPost("http://localhost:8080/ExistPhoneNumber");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("chatId", chatId));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(httpPost);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            return bufferedReader.readLine().equals("true");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean controllerSavePhoneNumber(String chatId, String phoneNumber) {
        try {
            HttpPost httpPost = new HttpPost("http://localhost:8080/SavePhoneNumber");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("chatId", chatId));
            params.add(new BasicNameValuePair("phoneNumber", phoneNumber));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(httpPost);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            return bufferedReader.readLine().equals("true");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
