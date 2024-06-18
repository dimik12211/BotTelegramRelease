package Application.controller;

import Application.model.Exercises;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Video;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ExercisesController {

    public String controllerFindAllExercises() {
        try {
            HttpPost httpPost = new HttpPost("http://localhost:8080/FindAllExercises");
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String returnText = EntityUtils.toString(entity);
            return returnText;
        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка при запросе упражнений для пояснений" + e.getMessage();
        }
    }

    public String controllerExercisesSave(String videoOrPhoto, String fileId, String chatId, String name) {
        try {
            HttpPost httpPost = new HttpPost("http://localhost:8080/ExercisesSave");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("videoOrPhoto", videoOrPhoto));
            params.add(new BasicNameValuePair("fileId", fileId));
            params.add(new BasicNameValuePair("chatId", chatId));
            params.add(new BasicNameValuePair("name", name));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String returnText = EntityUtils.toString(entity);
            return returnText;
        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка при запросе упражнений для пояснений" + e.getMessage();
        }
    }

    public String controllerVideoOrPhoto(String fileId) {
        try {
            HttpPost httpPost = new HttpPost("http://localhost:8080/VideoOrPhoto");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("fileId", fileId));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String returnText = EntityUtils.toString(entity);
            return returnText;
        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка при запросе информации видео или фото" + e.getMessage();
        }
    }
}
