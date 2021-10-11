package com.example.quizz.Model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Quizz {

    private long _id;

    @SerializedName("title")
    private String _title;

    private String _url;

    @SerializedName("quizz")
    private List<Question> _questions;


    public Quizz(String title) {
        this._title = title;
    }

    public Quizz(String title, List<Question> questions) {
        this._questions = questions;
        _title = title;
    }

    public Quizz(long id, String title, List<Question> questions) {
        this._questions = questions;
        _title = title;
        _id = id;
    }


    public long getId() {
        return _id;
    }

    public void setId(long id) {
        _id = id;
    }

    public String getTitle() {
        return _title;
    }

    public void setTitle(String title) {
        this._title = title;
    }

    public String getUrl() {
        return _url;
    }

    public void setUrl(String url) {
        this._url = url;
    }

    public List<Question> getQuestions() {
        return _questions;
    }

    public void setQuestions(List<Question> questions) {
        this._questions = questions;
    }
}
