package com.example.quizz.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.quizz.Model.Quizz;
import com.example.quizz.database.QuizzRepository;

import java.util.List;

public class QuizzSelectionViewModel extends AndroidViewModel {

    private QuizzRepository quizzRepository;
    private LiveData<List<Quizz>> quizzes;

    public QuizzSelectionViewModel(@NonNull Application application) {
        super(application);
        quizzRepository = new QuizzRepository(application);
        quizzes = quizzRepository.getAllQuizzs();
    }

    public LiveData<List<Quizz>> getAllQuizzes() {
        return quizzes;
    }

    public void deleteQuizz(Quizz quizz) {
        quizzRepository.deleteQuizz(quizz);
    }

    public void addQuizz(Quizz quizz) {
        quizzRepository.addQuizz(quizz);
    }

    public void loadFromUrl(String url) {
        quizzRepository.loadQuizzFromUrl(url);
    }
}
