package com.example.quizz.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quizz.Model.Question;
import com.example.quizz.Model.Quizz;

public class GameViewModel extends ViewModel {

    private Quizz _quizz;
    private int _selectedQuestionIndex = 0;
    private boolean _anwserViewed = false;
    private int _score = 0;

    public void setSelectedQuizz(Quizz quizz) {
        this._quizz = quizz;
        _selectedQuestionIndex = 0;
    }

    public Quizz getSelectedQuizz() {
        return this._quizz;
    }

    public int getSelectedQuestionIndex() {
        return _selectedQuestionIndex;
    }

    public void setSelectedQuestionIndex(int selectedQuestionIndex) {
        this._selectedQuestionIndex = selectedQuestionIndex;
    }

    public boolean isAnwserViewed() {
        return _anwserViewed;
    }

    public void setAnwserViewed(boolean anwserViewed) {
        this._anwserViewed = anwserViewed;
    }

    public int getScore() {
        return _score;
    }

    public void setScore(int score) {
        this._score = score;
    }

    public void reset() {
        this._quizz = null;
        this._selectedQuestionIndex = 0;
        this._anwserViewed = false;
        this._score = 0;
    }
}
