package Application.controller;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class UsersController {

    public String controllerAddUserInGroup(String chatIdTrener, String chatId){
        try {
            HttpPost httpPost = new HttpPost("http://localhost:8080/AddUserInGroup");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("chatIdTrener", chatIdTrener));
            params.add(new BasicNameValuePair("chatId", chatId));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String returnText = EntityUtils.toString(entity);
            return returnText;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public boolean controllerFindIsTrener(String chatId){
        try {
            HttpPost httpPost = new HttpPost("http://localhost:8080/FindIsTrener");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("chatId", chatId));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(httpPost);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            return Objects.equals(bufferedReader.readLine(), "true");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String controllerFindUsers(String chatId) {
        try {
            HttpPost httpPost = new HttpPost("http://localhost:8080/FindUsers");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("chatId", chatId));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            //String returnText = bufferedReader.readLine();
            String returnText = EntityUtils.toString(entity);
            return returnText;
        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка при запросе возможных пользователей для назначения тренировки" + e.getMessage();
        }
    }
    public String controllerFindAllUsersAddGroup(String chatId) {
        try {
            HttpPost httpPost = new HttpPost("http://localhost:8080/FindAllUsersAddGroup");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("chatId", chatId));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String returnText = EntityUtils.toString(entity);
            return returnText;
        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка при запросе возможных пользователей для назначения тренировки" + e.getMessage();
        }
    }

    public String controllerFindAllUsers(){
        try {
            HttpPost httpPost = new HttpPost("http://localhost:8080/FindAllUsers");
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            //String returnText = bufferedReader.readLine();
            String returnText = EntityUtils.toString(entity);
            return returnText;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String controllerActiveStatus(String chatId){
        try {
            HttpPost httpPost = new HttpPost("http://localhost:8080/ActiveStatus");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("chatId", chatId));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String returnText = EntityUtils.toString(entity);
            return returnText;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
