package Application.service.statuses;

import Application.model.Workout;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AssignmentWorkoutStatus {

    private final Map<String, String> assigmentWorkoutChatId = new HashMap<String, String>();

    public String saveAssigmentWorkoutChatIdMap(String chatId, String toChatId) {
        try {
            assigmentWorkoutChatId.put(chatId, toChatId);
            return "true";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public boolean isAssigmentWorkoutChatIdMap(String chatId) {
        return assigmentWorkoutChatId.containsKey(chatId);
    }

    public String getAssigmentWorkoutChatIdMap(String chatId){
        return assigmentWorkoutChatId.get(chatId);
    }


}
