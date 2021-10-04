package com.example.quizz.Model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Quizz {

    private long _id;

    @SerializedName("title")
    private String _title;

    @SerializedName("quizz")
    private List<Question> _questions;


    public Quizz(String title) {
        this._title = title;
    }

    public Quizz(String title, List<Question> questions) {
        this._questions = questions;
    }


    public long getId() {
        return _id;
    }

    public String getTitle() {
        return _title;
    }

    public void setTitle(String title) {
        this._title = title;
    }

    public List<Question> getQuestions() {
        return _questions;
    }

    public void setQuestions(List<Question> questions) {
        this._questions = questions;
    }
}
