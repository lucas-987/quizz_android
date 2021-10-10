package com.example.quizz.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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
import com.example.quizz.api.BooleanDeserializer;
import com.example.quizz.api.QuizzService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

        Gson customGson = new GsonBuilder()
                                .registerTypeAdapter(Boolean.TYPE, new BooleanDeserializer())
                                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dept-info.univ-fcomte.fr/joomla/images/CR0700/JSON/")
                .addConverterFactory(GsonConverterFactory.create(customGson))
                .build();

        quizzService = retrofit.create(QuizzService.class);
    }

    public void loadQuizzFromUrl(String url) {
        quizzService.quizzFromUrl(url).enqueue(new retrofit2.Callback<Quizz>() {

            @Override
            public void onResponse(Call<Quizz> call, Response<Quizz> response) {
                if(!response.isSuccessful()) {
                    Log.println(Log.DEBUG, "QuizzRepository", "QuizzRepository() : error response from quizz API (url : " + url + "). HTTP Code : " + response.code());
                    return;
                }

                Quizz quizz;
                try{
                    quizz = response.body();
                }catch (Exception e) {
                    Log.println(Log.ERROR, "QuizzRepository", "QuizzRepository.loadQuizzFromUrl() : quizzService.quizzFromUrl() retrieving body error : " + e.toString());
                    return;
                }

                String title = quizz.getTitle();
                if(title == null || title.isEmpty())
                    quizz.setTitle(url);

                addQuizz(quizz);
            }

            @Override
            public void onFailure(Call<Quizz> call, Throwable t) {
                Log.println(Log.DEBUG, "QuizzRepository", "QuizzRepository.loadQuizzFromUrl() : quizzService.quizzFromUrl().enqueue failure : " + t.toString());
            }
        });
    }

    public LiveData<List<Quizz>> getAllQuizzs() {

        quizzService.quizzExample().enqueue(new retrofit2.Callback<Quizz>() {
            @Override
            public void onResponse(Call<Quizz> call, Response<Quizz> response) {
                if(!response.isSuccessful()) {
                    Log.println(Log.DEBUG, "QuizzRepository", "QuizzRepository.getAllQuizzs() : error response from quizz API. HTTP Code : " + response.code());
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

                    List<Question> questions = quizz.getQuestions();
                    if(questions != null) {
                        for(Question question : questions) {
                            database.questionDAO().insert(new QuestionEntity(quizzId, question.getQuestion(), question.getAnwser(), question.getOrder()));
                        }
                    }
                }
            });
            return null;
        }
    }

    public void deleteQuizz(Quizz quizz) {
        List<QuestionEntity> questionEntities = new ArrayList<>();
        for(Question question : quizz.getQuestions()) {
            QuestionEntity questionEntity = new QuestionEntity(quizz.getId(), question.getQuestion(), question.getAnwser(), question.getOrder());
            questionEntity.id = question.getId();
            questionEntities.add(questionEntity);
        }

        QuizzEntity quizzEntity = new QuizzEntity(quizz.getTitle());
        quizzEntity.id = quizz.getId();

        new DeleteQuizzAsyncTask(database).execute(new QuizzWithQuestions(quizzEntity, questionEntities));
    }

    private static class DeleteQuizzAsyncTask extends AsyncTask<QuizzWithQuestions, Void, Void> {

        private ApplicationDatabase database;

        private DeleteQuizzAsyncTask(ApplicationDatabase database) {
            this.database = database;
        }

        @Override
        protected Void doInBackground(QuizzWithQuestions... quizzWithQuestions) {
            database.runInTransaction(new Runnable() {
                @Override
                public void run() {
                    for(QuestionEntity question : quizzWithQuestions[0].questions) {
                        database.questionDAO().delete(question);
                    }

                    database.quizzDAO().delete(quizzWithQuestions[0].quizz);
                }
            });

            return null;
        }
    }

    public LiveData<List<Question>> getQuestionsFromQuizz(long quizzId) {
        return Transformations.map(questionDAO.getQuestionFromQuizz(quizzId), new Function<List<QuestionEntity>, List<Question>>() {
            @Override
            public List<Question> apply(List<QuestionEntity> input) {
                List<Question> result = new ArrayList<>();

                for(QuestionEntity questionEntity : input) {
                    result.add(TypesConverter.QuestionEntityToQuestion(questionEntity));
                }

                return result;
            }
        });
    }

    public void addQuestion(Question question, long quizzId) {
        QuestionEntity questionEntity = new QuestionEntity(quizzId, question.getQuestion(), question.getAnwser(), question.getOrder());
        new AddQuestionAsyncTask(database).execute(questionEntity);
    }

    private static class AddQuestionAsyncTask extends AsyncTask<QuestionEntity, Void, Void> {

        private ApplicationDatabase database;

        private AddQuestionAsyncTask(ApplicationDatabase database) {
            this.database = database;
        }


        @Override
        protected Void doInBackground(QuestionEntity... questions) {
            database.questionDAO().insert(questions[0]);
            return null;
        }
    }

    public void deleteQuestion(Question question, long quizzId) {
        QuestionEntity questionEntity = new QuestionEntity(quizzId, question.getQuestion(), question.getAnwser(), question.getOrder());
        questionEntity.id = question.getId();
        new DeleteQuestionAsyncTask(database).execute(questionEntity);
    }

    private static class DeleteQuestionAsyncTask extends AsyncTask<QuestionEntity, Void, Void> {

        private ApplicationDatabase database;

        private DeleteQuestionAsyncTask(ApplicationDatabase database) {
            this.database = database;
        }


        @Override
        protected Void doInBackground(QuestionEntity... questions) {
            database.questionDAO().delete(questions[0]);
            return null;
        }
    }

    public void updateQuestionsPositions(List<Question> questions) {
        new UpdateQuestionsPositionsAsyncTask(database).execute(questions);
    }

    private static class UpdateQuestionsPositionsAsyncTask extends AsyncTask<List<Question>, Void, Void> {
        private ApplicationDatabase database;

        private UpdateQuestionsPositionsAsyncTask(ApplicationDatabase database) {
            this.database = database;
        }

        @Override
        protected Void doInBackground(List<Question>... questions) {
            database.questionDAO().updatePositions(questions[0]);
            return null;
        }
    }

    // TODO split this repo in 2 (put question related operations on a QuestionRepository)
}
