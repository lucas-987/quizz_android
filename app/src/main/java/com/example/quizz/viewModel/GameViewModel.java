package com.example.quizz.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quizz.Model.Question;
import com.example.quizz.Model.Quizz;

public class GameViewModel extends ViewModel {

    private Quizz _quizz;
    private int _selectedQuestionIndex = 0;
    private boolean _anwserViewed = false;

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
}
