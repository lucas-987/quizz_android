package com.example.quizz.Model;

import com.google.gson.annotations.SerializedName;

public class Question {
    private long _id;

    @SerializedName("question")
    private String _sentence;

    @SerializedName("reponse")
    private boolean _anwser;

    private int _order;

    public Question(String sentence, boolean anwser) {
        this._sentence = sentence;
        this._anwser = anwser;
    }

    public Question(long id, String sentence, boolean anwser) {
        _id = id;
        _sentence = sentence;
        _anwser = anwser;
    }

    public Question(long id, String sentence, boolean anwser, int order) {
        _id = id;
        _sentence = sentence;
        _anwser = anwser;
        _order = order;
    }

    public String getQuestion() {
        return _sentence;
    }

    public void setQuestion(String sentence) {
        this._sentence = sentence;
    }

    public boolean getAnwser() {
        return _anwser;
    }

    public void setAnwser(boolean anwser) {
        this._anwser = anwser;
    }

    public long getId() {
        return _id;
    }

    public void setId(long id) {
        this._id = id;
    }

    public int getOrder() {
        return _order;
    }

    public void setOrder(int order) {
        this._order = order;
    }
}
