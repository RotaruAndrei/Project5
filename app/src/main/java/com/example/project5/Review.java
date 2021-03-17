package com.example.project5;

public class Review {

    private int id;
    private String userName;
    private String text;
    private String date;


    public Review(int id, String userName, String text, String date) {
        this.id = id;
        this.userName = userName;
        this.text = text;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", text='" + text + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
