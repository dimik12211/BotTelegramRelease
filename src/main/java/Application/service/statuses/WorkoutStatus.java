package Application.service.statuses;

import Application.gui.KeyboardEndWorkout;
import Application.model.Workout;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.Map;

@Service
public class WorkoutStatus {

    private final Map<String, Workout> workoutStatusMap = new HashMap<String, Workout>();

    private final Map<String, Long> workoutTime = new HashMap<String, Long>();

    private final Map<String, String> chatIdWorkoutId = new HashMap<String, String>();
    private final Map<String, ReplyKeyboardMarkup> chatIdReplyKeyboardMarkup = new HashMap<String, ReplyKeyboardMarkup>();

    public String saveUserStatusMap(String chatId, Workout workout) {
        try {
            workoutStatusMap.put(chatId, workout);
            return "true";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public boolean isUserStatusMap(String chatId) {
        return workoutStatusMap.containsKey(chatId);
    }

    public Workout getUserStatusMap(String chatId){
        return workoutStatusMap.get(chatId);
    }

    public String controlTimeWorkout(String chatId){
        long start = System.currentTimeMillis() / 1000 / 60;
        workoutTime.put(chatId, start);
        return String.valueOf(start);
    }

    public String timeWorkout(String chatId){
        long finish = System.currentTimeMillis() / 1000 / 60;
        long timeElapsed = finish - workoutTime.get(chatId);
        return String.valueOf(timeElapsed);
    }

    public String getWorkoutTime(String chatId){
        return String.valueOf(workoutTime.get(chatId));
    }

    public String saveChatIdWorkoutIdMap(String chatId, String workoutId) {
        try {
            chatIdWorkoutId.put(chatId, workoutId);
            return "true";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public String getChatIdWorkoutIdMap(String chatId){
        return chatIdWorkoutId.get(chatId);
    }

    public String saveReplyKeyboardMarkupMap(String chatId, ReplyKeyboardMarkup replyKeyboardMarkup) {
        try {
            chatIdReplyKeyboardMarkup.put(chatId, replyKeyboardMarkup);
            return "true";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public String deleteReplyKeyboardMarkupMap(String chatId) {
        try {
            chatIdReplyKeyboardMarkup.remove(chatId);
            return "true";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public boolean isReplyKeyboardMarkupMap(String chatId) {
        return chatIdReplyKeyboardMarkup.containsKey(chatId);
    }

    public ReplyKeyboardMarkup getReplyKeyboardMarkupMap(String chatId){
        return chatIdReplyKeyboardMarkup.get(chatId);
    }
}
