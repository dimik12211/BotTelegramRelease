package Application.service.statuses;

import Application.model.Exercises;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExercisesStatus {

    private final Map<String, Exercises> exceptionStatusMap = new HashMap<String, Exercises>();

    public String saveexercisesStatusMap(String chatId, Exercises exception) {
        try {
            exceptionStatusMap.put(chatId, exception);
            return "true";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public boolean isexercisesStatusMap(String chatId) {
        return exceptionStatusMap.containsKey(chatId);
    }

    public Exercises getexercisesStatusMap(String chatId){
        return exceptionStatusMap.get(chatId);
    }
}
