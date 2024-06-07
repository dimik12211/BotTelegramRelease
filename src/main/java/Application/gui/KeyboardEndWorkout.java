package Application.gui;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

public class KeyboardEndWorkout {
    public ReplyKeyboardMarkup getKeyboard() {
        try {
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setOneTimeKeyboard(false);
            ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();

            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(new KeyboardButton("Конец тренировки"));
            keyboardRows.add(keyboardRow);

            replyKeyboardMarkup.setKeyboard(keyboardRows);
            return replyKeyboardMarkup;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
