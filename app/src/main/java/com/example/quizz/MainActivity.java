package com.example.quizz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.os.Bundle;

import com.example.quizz.Model.Quizz;
import com.example.quizz.database.QuizzRepository;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        QuizzRepository rep = new QuizzRepository(getApplication());
        LiveData<List<Quizz>> quizzs = rep.getAllQuizzs();

        /*List<Quizz> test = quizzs.getValue();
        rep.addQuizz(quizzs.getValue().get(0));*/
    }
}