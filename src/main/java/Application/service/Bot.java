package Application.service;

import Application.controller.PhoneNumberController;
import Application.controller.UsersController;
import Application.gui.Keyboard;
import Application.service.statuses.EnumTelegramStatus;
import Application.service.statuses.UserStatus;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class Bot extends TelegramLongPollingBot {

    @Autowired
    private PhoneNumberController phoneNumberController;

    @Autowired
    private UsersController usersController;

    @Autowired
    private ParseMessageBot parseMessageBot;

    @Autowired
    private ParsePhoneNumberBot parsePhoneNumberBot;

    private Keyboard keyboard = new Keyboard();

    private final String BOT_TOKEN = "5772427413:AAG1_QUBw3FaYFpHZQh4wGFe2qmJGX5Szhc";

    private final String BOT_NAME = "KuznetsovMOSm221bot";

    private UserStatus userStatus;

    private boolean firstStart = true;

    @Autowired
    private void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public Bot() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if(firstStart){ //При каждом перезапуске приложения для всех зареганых ранее пользователей устанавливается статус ожидания
                setStatuses();
                firstStart = false;
            }
            if (update.hasMessage() && update.getMessage().hasText()) {
                Message inMess = update.getMessage();
                String chatId = inMess.getChatId().toString();
                SendMessage outMess = new SendMessage();
                if (phoneNumberController.controllerExistPhoneNumber(chatId) || Objects.equals(userStatus.getUserStatusMap(chatId), EnumTelegramStatus.ExpectPhoneNumber)) {
                    if(!userStatus.isUserStatusMap(chatId)){
                        userStatus.saveUserStatusMap(chatId, EnumTelegramStatus.Expect);
                    }
                    if (Objects.equals(userStatus.getUserStatusMap(chatId), "ExpectPhoneNumber")) {
                        if (parsePhoneNumberBot.parseMessage(inMess.getText())) {
                            phoneNumberController.controllerSavePhoneNumber(chatId, inMess.getText());
                            userStatus.saveUserStatusMap(chatId, EnumTelegramStatus.Expect);
                            //outMess.setText("Номер телефона успешно сохранен, вы авторизированы.");///////////////////////////////////////////////////////////
                            outMess.setReplyMarkup(keyboard.getKeyboard());
                            outMess.setChatId(chatId);
                            execute(outMess);
                        } else {
                            outMess.setText("Номер телефона не соответствует требованиям.\nВозможные варианты ввода: +7**********, +7 *** *** ** **, 8**********, 8 *** *** ** **");
                            outMess.setChatId(chatId);
                            outMess.setReplyMarkup(parseMessageBot.getReplyKeyboardMarkup(chatId));
                            execute(outMess);
                        }
                    } else {
                        if (userStatus.getUserStatusMap(chatId).equals("Expect")) {
                            String request = parseMessageBot.parseMessage(chatId, inMess.getText());
                            outMess.setReplyMarkup(parseMessageBot.getReplyKeyboardMarkup(chatId));
                            outMess.setText(request);
                            outMess.setChatId(chatId);
                        } else {
                            String request = parseMessageBot.parseMessageStatus(chatId, inMess.getText(), userStatus.getUserStatusMap(chatId));
                            outMess.setText(request);
                            outMess.setReplyMarkup(parseMessageBot.getReplyKeyboardMarkup(parseMessageBot.getChatId(chatId)));
                            outMess.setChatId(parseMessageBot.getChatId(chatId));
                        }
                    }
                } else {
                    userStatus.saveUserStatusMap(chatId, EnumTelegramStatus.ExpectPhoneNumber);
                    outMess.setReplyMarkup(parseMessageBot.getReplyKeyboardMarkup(chatId));
                    outMess.setText("Добрый день! Для продолжения работы необходимо указать номер телефона. \nВозможные варианты ввода: +7**********, +7 *** *** ** **, 8**********, 8 *** *** ** **");
                    outMess.setChatId(chatId);
                }
                execute(outMess);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setStatuses(){
        String users = usersController.controllerFindAllUsers();
        List<String> usersList = Arrays.asList(users.split(" "));
        for(int i = 0; i < usersList.size(); i++){
            userStatus.saveUserStatusMap(usersList.get(i), EnumTelegramStatus.Expect);
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}

