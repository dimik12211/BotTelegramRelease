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
public class WorkoutController {

    public String controllerGetStatistic(String chatId) {
        try {
            HttpPost httpPost = new HttpPost("http://localhost:8080/GetStatistic");
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
            return "Ошибка запроса в сервис БД. " + e.getMessage();
        }
    }

    public boolean controllerSaveWorkout(String chatId, String workoutJson) {
        try {
            HttpPost httpPost = new HttpPost("http://localhost:8080/SaveWorkout");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("workoutJson", workoutJson));
            params.add(new BasicNameValuePair("chatId", chatId));
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(httpPost);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            return Objects.equals(bufferedReader.readLine(), "true");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String controllerGetWorkout(String chatId) {
        try {
            HttpPost httpPost = new HttpPost("http://localhost:8080/GetWorkout");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("chatId", chatId));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(httpPost);
            //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            HttpEntity entity = response.getEntity();
            //String returnText = bufferedReader.readLine();
            String returnText = EntityUtils.toString(entity);
            return returnText;
        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка запроса в сервис БД. " + e.getMessage();
        }
    }

    public String controllerChoosingWorkout(String workoutId, String timeStart, String chatId) {
        try {
            HttpPost httpPost = new HttpPost("http://localhost:8080/ChoosingWorkout");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("workoutId", workoutId));
            params.add(new BasicNameValuePair("timeStart", timeStart));
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
            return "Ошибка запроса в сервис БД. " + e.getMessage();
        }
    }

    public String controllerSetTimeWorkout(String workoutId, String time, String chatId, String timeStart) {
        try {
            HttpPost httpPost = new HttpPost("http://localhost:8080/SetTimeWorkout");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("workoutId", workoutId));
            params.add(new BasicNameValuePair("time", time));
            params.add(new BasicNameValuePair("chatId", chatId));
            params.add(new BasicNameValuePair("timeStart", timeStart));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            //String returnText = bufferedReader.readLine();
            String returnText = EntityUtils.toString(entity);
            return returnText;
        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка запроса в сервис БД. " + e.getMessage();
        }
    }

    public String controllerFindChatId(String userId){
        try {
            HttpPost httpPost = new HttpPost("http://localhost:8080/FindChatId");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("userId", userId));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            //String returnText = bufferedReader.readLine();
            String returnText = EntityUtils.toString(entity);
            return returnText;
        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка запроса на получение chatId (кому назначить тренировку) в сервис БД. " + e.getMessage();
        }
    }
}
