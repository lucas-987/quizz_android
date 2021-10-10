package com.example.quizz.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.quizz.Model.Question;

public class ModifyQuestionViewModel extends ViewModel {
    private Question _question;

    public Question getQuestion() {
        return _question;
    }

    public void setQuestion(Question question) {
        this._question = question;
    }
}
