package com.example.quizz.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.room.Transaction;
import androidx.room.TypeConverter;

import com.example.quizz.Model.Question;
import com.example.quizz.Model.Quizz;
import com.example.quizz.api.QuizzService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizzRepository {
    private QuizzService quizzService;

    ApplicationDatabase database;
    private QuizzDAO quizzDAO;
    private QuestionDAO questionDAO;

    private LiveData<List<Quizz>> allQuizzs;

    public QuizzRepository(Application application) {
        database = ApplicationDatabase.getInstance(application);
        quizzDAO = database.quizzDAO();
        questionDAO = database.questionDAO();

        allQuizzs = Transformations.map(quizzDAO.getAllQuizzs(), new Function<List<QuizzWithQuestions>, List<Quizz>>() {
            @Override
            public List<Quizz> apply(List<QuizzWithQuestions> input) {
                List<Quizz> result = new ArrayList<>();
                for (QuizzWithQuestions quizzWithQuestions : input) {
                    result.add(TypesConverter.QuizzWithQuestionsToQuizz(quizzWithQuestions));
                }
                return result;
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dept-info.univ-fcomte.fr/joomla/images/CR0700/JSON/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        quizzService = retrofit.create(QuizzService.class);
    }

    public LiveData<List<Quizz>> getAllQuizzs() {

        quizzService.quizzExample().enqueue(new retrofit2.Callback<Quizz>() {
            @Override
            public void onResponse(Call<Quizz> call, Response<Quizz> response) {
                if(!response.isSuccessful()) {
                    Log.println(Log.DEBUG, "QuizzRepository", "QuizzRepository() : error response from quizz API. HTTP Code : " + response.code());
                    return;
                }

                Quizz quizz = response.body();
                quizz.setTitle("Animaux");

                addQuizz(quizz);
            }

            @Override
            public void onFailure(Call<Quizz> call, Throwable t) {
                Log.println(Log.DEBUG, "QuizzRepository", "QuizzRepository() : quizzService.quizzExample().enqueue failure : " + t.toString());
                // no response
            }
        });

        return allQuizzs;
    }

    public void addQuizz(Quizz quizz) {
        new AddQuizzAsyncTask(database).execute(quizz);
    }

    private static class AddQuizzAsyncTask extends AsyncTask<Quizz, Void, Void> {
        private ApplicationDatabase database;

        private AddQuizzAsyncTask(ApplicationDatabase database) {
            this.database = database;
        }

        @Override
        protected Void doInBackground(Quizz... quizzes) {
            database.runInTransaction(new Runnable() {
                @Override
                public void run() {
                    Quizz quizz = quizzes[0];
                    long quizzId = database.quizzDAO().insert(new QuizzEntity(quizz.getTitle()));

                    for(Question question : quizz.getQuestions()) {
                        database.questionDAO().insert(new QuestionEntity(quizzId, question.getQuestion(), question.getAnwser()));
                    }
                }
            });
            return null;
        }
    }
}
