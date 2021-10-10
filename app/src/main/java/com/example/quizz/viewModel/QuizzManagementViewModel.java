package com.example.quizz.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.quizz.Model.Question;
import com.example.quizz.Model.Quizz;
import com.example.quizz.database.QuizzRepository;

import java.util.List;

public class QuizzManagementViewModel extends AndroidViewModel {

    QuizzRepository quizzRepository;

    private long _quizzId;
    private LiveData<List<Question>> _questions;

    public QuizzManagementViewModel(@NonNull Application application) {
        super(application);
        quizzRepository = new QuizzRepository(application);
    }

    public void addQuestion(Question question) {
        // TODO check that quizz is not null, handle failure
        quizzRepository.addQuestion(question, _quizzId);
    }

    public void deleteQuestion(Question question) {
        // TODO check that quizz is not null, handle failure
        quizzRepository.deleteQuestion(question, _quizzId);
    }

    public void updateQuestionsPosition(List<Question> questions) {
        quizzRepository.updateQuestionsPositions(questions);
    }

    public LiveData<List<Question>> getQuestions() {
        return this._questions;
    }

    public long getQuizzId() {
        return _quizzId;
    }

    public void setQuizzId(long quizzId) {
        this._quizzId = quizzId;
        _questions = quizzRepository.getQuestionsFromQuizz(quizzId);
    }
}
