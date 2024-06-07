package Application.service;

import Application.controller.ExercisesController;
import Application.controller.UsersController;
import Application.controller.WorkoutController;
import Application.gui.Keyboard;
import Application.gui.KeyboardEndWorkout;
import Application.model.Workout;
import Application.service.statuses.AssignmentWorkoutStatus;
import Application.service.statuses.EnumTelegramStatus;
import Application.service.statuses.UserStatus;
import Application.service.statuses.WorkoutStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import javax.persistence.Column;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Objects;

@Service
public class ParseMessageBot {

    @Autowired
    private UserStatus userStatus;

    @Autowired
    private WorkoutStatus workoutStatus;

    @Autowired
    private AssignmentWorkoutStatus assignmentWorkoutStatus;

    @Autowired
    private WorkoutController workoutController;

    @Autowired
    private UsersController usersController;

    @Autowired
    private ExercisesController exercisesController;

    private Keyboard keyboard = new Keyboard();

    private KeyboardEndWorkout keyboardEndWorkout = new KeyboardEndWorkout();

    public String parseMessage(String chatId, String message) {
        String returnText = "";
        if (message.equals("/start") || message.equals("Привет") || message.equals("Домой")) {
            returnText = "Выберите действие из вариантов ниже";
            userStatus.saveUserStatusMap(chatId, EnumTelegramStatus.Expect);
            return returnText;
        } else if ((message.equals("/get0") || message.equals("Создать тренировку"))) {
            returnText = "Создание тренировки:\n" +
                    "Введите наименование тренировки: \n";
            userStatus.saveUserStatusMap(chatId, EnumTelegramStatus.WorkoutName);
            workoutStatus.saveUserStatusMap(chatId, new Workout());
            return returnText;
        } else if ((message.equals("/get1") || message.equals("Посмотреть сохраненные тренировки"))) {
            String workouts = workoutController.controllerGetWorkout(chatId);
            returnText = "Сохраненные тренировки: \n" + workouts + "\n";
            return returnText;
        } else if ((message.equals("/get2") || message.equals("Провести тренировку"))) {
            String workouts = workoutController.controllerGetWorkout(chatId);
            returnText = "Сохраненные тренировки (укажите номер тренировки): \n" + workouts + "\n";
            userStatus.saveUserStatusMap(chatId, EnumTelegramStatus.ChoosingWorkout);
            return returnText;
        } else if ((message.equals("/get3") || message.equals("Назначить тренировку пользователю"))) {
            String users = usersController.controllerFindUsers(chatId);
            returnText = "Какому пользователю назначить тренировку (укажите номер пользователя): \n" + users;
            userStatus.saveUserStatusMap(chatId, EnumTelegramStatus.AssignmentWorkout1);
            return returnText;
        } else if ((message.equals("/get4")) || message.equals("Изменить статус активности")) {
            String status = usersController.controllerActiveStatus(chatId);
            String[] statuses = status.split(" ");
            if (Objects.equals(statuses[0], "true")) {
                returnText = "Статус успешно изменен. Текущий статус: " + statuses[1] + "\n";
            } else {
                returnText = "Ошибка: " + statuses[1] + "\n";
            }
            return returnText;
        } else if ((message.equals("/get5")) || message.equals("Показать пояснение к упражнению")) {
            exercisesController
            returnText = "К какому упражнению показать поясняющие изображения (укажите номер упражнениия):\n";
            userStatus.saveUserStatusMap(chatId, EnumTelegramStatus.SearchExercisePicture);
            return returnText;
        }
        workoutStatus.saveReplyKeyboardMarkupMap(chatId, keyboard.getKeyboard());
        return "Ошибка, недопустимый аргумент.\nВыберите действие из вариантов ниже";
    }

    public String parseMessageStatus(String chatId, String message, String status) throws IOException {
        String returnText = "";
        switch (status) {
            case ("WorkoutName"):
                returnText = "Укажите количество повторений: \n";
                workoutStatus.getUserStatusMap(chatId).setChatId(chatId);
                workoutStatus.getUserStatusMap(chatId).setName(message);
                userStatus.saveUserStatusMap(chatId, EnumTelegramStatus.WorkoutRepetitions);
                break;
            case ("WorkoutRepetitions"):
                returnText = "Укажите количество подходов: \n";
                workoutStatus.getUserStatusMap(chatId).setRepetitions(message);
                userStatus.saveUserStatusMap(chatId, EnumTelegramStatus.WorkoutQuantity);
                break;
            case ("WorkoutQuantity"):
                returnText = "Укажите коментарий №1: \n";
                workoutStatus.getUserStatusMap(chatId).setQuantity(message);
                userStatus.saveUserStatusMap(chatId, EnumTelegramStatus.WorkoutComment);
                break;
            case ("WorkoutComment"):
                returnText = "Укажите коментарий №2: \n";
                workoutStatus.getUserStatusMap(chatId).setComment(message);
                userStatus.saveUserStatusMap(chatId, EnumTelegramStatus.WorkoutRingWork);
                break;
            case ("WorkoutRingWork"):
                returnText = "Укажите коментарий №3: \n";
                workoutStatus.getUserStatusMap(chatId).setRingWork(message);
                userStatus.saveUserStatusMap(chatId, EnumTelegramStatus.WorkoutComment2);
                break;
            case ("WorkoutComment2"):
                workoutStatus.getUserStatusMap(chatId).setComment2(message);
                Workout workout = workoutStatus.getUserStatusMap(chatId);

                StringWriter writer = new StringWriter();
                ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(writer, workout);
                String result = writer.toString();

                workoutController.controllerSaveWorkout(chatId, result);
                String format = "%-50s %-15s";
                returnText = "Тренировка создана и сохранена.\n" +
                        "Информация по тренировке: \n" +
                        String.format(format, "Наименование: ", workout.getName()) + "\n" +
                        String.format(format, "Количество повторений: ", workout.getRepetitions()) + "\n" +
                        String.format(format, "Количество подходов: ", workout.getQuantity()) + "\n" +
                        String.format(format, "Коментарий №1: ", workout.getComment()) + "\n" +
                        String.format(format, "Коментарий №2: ", workout.getRingWork()) + "\n" +
                        String.format(format, "Коментарий №3: ", workout.getComment2()) + "\n";

                userStatus.saveUserStatusMap(chatId, EnumTelegramStatus.Expect);
                break;
            case ("ChoosingWorkout"):
                String timeStart = workoutStatus.controlTimeWorkout(chatId);
                workoutStatus.saveChatIdWorkoutIdMap(chatId, message);
                String workoutChoosingWorkout = workoutController.controllerChoosingWorkout(message, timeStart, chatId); // получаю с БД тренировку котогрую надо провести
                returnText = "Тренировка (Нажмите 'конец тренировки' когда закончите последнее упражнение): \n" + workoutChoosingWorkout;
                userStatus.saveUserStatusMap(chatId, EnumTelegramStatus.WaitingEndWorkout);

                workoutStatus.saveReplyKeyboardMarkupMap(chatId, keyboardEndWorkout.getKeyboard());
                ///////////////////////////////////////////////Добавить клавиатуру с кнопкой "Конец тренировки"
                break;
            case ("WaitingEndWorkout"):
                String time = workoutStatus.timeWorkout(chatId);
                workoutController.controllerSetTimeWorkout(workoutStatus.getChatIdWorkoutIdMap(chatId), time, chatId, workoutStatus.getWorkoutTime(chatId));
                returnText = "Тренировка завершена за " + time + " минут. \n";
                userStatus.saveUserStatusMap(chatId, EnumTelegramStatus.Expect);

                workoutStatus.saveReplyKeyboardMarkupMap(chatId, keyboard.getKeyboard());
                //Вернуть обычную клавиатуру
                break;
            case ("AssignmentWorkout1"):
                String chatIdFromUserchat = workoutController.controllerFindChatId(message);
                assignmentWorkoutStatus.saveAssigmentWorkoutChatIdMap(chatId, chatIdFromUserchat);
                String workouts = workoutController.controllerGetWorkout(chatId);
                returnText = "Какую тренировку назначить? (укажите номер тренировки): \n" + workouts + "\n";
                userStatus.saveUserStatusMap(chatId, EnumTelegramStatus.AssignmentWorkout2);
                break;
            case ("AssignmentWorkout2"):
                userStatus.saveUserStatusMap(chatId, EnumTelegramStatus.Expect);
                userStatus.saveUserStatusMap(getChatIdNotStatus(chatId), EnumTelegramStatus.WaitingEndWorkout);
                String timeStartAssignmentWorkout2 = workoutStatus.controlTimeWorkout(getChatIdNotStatus(chatId));
                workoutStatus.saveChatIdWorkoutIdMap(getChatIdNotStatus(chatId), message);
                String workoutChoosingWorkoutAssignmentWorkout2 = workoutController.controllerChoosingWorkout(message, timeStartAssignmentWorkout2, getChatIdNotStatus(chatId));
                returnText = "Вам назначена тренировка.\n(Нажмите 'Конец тренировки' когда закончите последнее упражнение): \n" + workoutChoosingWorkoutAssignmentWorkout2;

                workoutStatus.saveReplyKeyboardMarkupMap(getChatIdNotStatus(chatId), keyboardEndWorkout.getKeyboard());
                ///////////////////////////////////////////////Добавить клавиатуру с кнопкой "Конец тренировки" для getChatId(chatId)
                break;
        }
        return returnText;
    }

    public String getChatId(String chatId) {
        String responseChatId = "";
        if (assignmentWorkoutStatus.isAssigmentWorkoutChatIdMap(chatId) && userStatus.getUserStatusMap(assignmentWorkoutStatus.getAssigmentWorkoutChatIdMap(chatId)).equals("WaitingEndWorkout")) {
            responseChatId = assignmentWorkoutStatus.getAssigmentWorkoutChatIdMap(chatId);
        } else {
            responseChatId = chatId;
        }
        return responseChatId;
    }

    public String getChatIdNotStatus(String chatId) {
        String responseChatId = "";
        if (assignmentWorkoutStatus.isAssigmentWorkoutChatIdMap(chatId)) {
            responseChatId = assignmentWorkoutStatus.getAssigmentWorkoutChatIdMap(chatId);
            //}
        } else {
            responseChatId = chatId;
        }
        return responseChatId;
    }

    public ReplyKeyboardMarkup getReplyKeyboardMarkup(String chatId) {
        if (workoutStatus.isReplyKeyboardMarkupMap(chatId)) {
            return workoutStatus.getReplyKeyboardMarkupMap(chatId);
        } else {
            return keyboard.getKeyboard();
        }
    }
}
