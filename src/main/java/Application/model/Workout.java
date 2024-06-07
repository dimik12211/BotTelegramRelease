package Application.model;

import javax.persistence.*;

public class Workout {
    private String chatId, Name, Repetitions, Quantity, Comment, ringWork, Comment2;

    public Workout() {

    }

    public Workout(String chatId, String Name, String Repetitions, String Quantity, String Comment, String ringWork, String Comment2) {
        this.chatId = chatId;
        this.Name = Name;
        this.Repetitions = Repetitions;
        this.Quantity = Quantity;
        this.Comment = Comment;
        this.ringWork = ringWork;
        this.Comment2 = Comment2;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRepetitions() {
        return Repetitions;
    }

    public void setRepetitions(String repetitions) {
        Repetitions = repetitions;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getRingWork() {
        return ringWork;
    }

    public void setRingWork(String ringWork) {
        this.ringWork = ringWork;
    }

    public String getComment2() {
        return Comment2;
    }

    public void setComment2(String comment2) {
        Comment2 = comment2;
    }
}
