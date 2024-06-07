package Application.service.statuses;

import Application.service.statuses.EnumTelegramStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserStatus {

    private final Map<String, EnumTelegramStatus> userStatusMap = new HashMap<String, EnumTelegramStatus>();

    public String saveUserStatusMap(String chatId, EnumTelegramStatus status) {
        try {
            userStatusMap.put(chatId, status);
            return "true";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public boolean isUserStatusMap(String chatId) {
        return userStatusMap.containsKey(chatId);
    }

    public String getUserStatusMap(String chatId){
        return userStatusMap.get(chatId).name();
    }
}
