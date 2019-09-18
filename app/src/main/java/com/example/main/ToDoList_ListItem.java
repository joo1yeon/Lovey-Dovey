package com.example.main;

public class ToDoList_ListItem {
    private String content;        //투두 내용
    private String date;           //완료한 날짜

    public void setContent(String text) {
        this.content = text;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getContent(){
        return this.content;
    }

    public String getDate(){
        return this.date;
    }
}
