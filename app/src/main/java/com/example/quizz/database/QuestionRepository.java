package com.example.quizz.database;

import android.app.Application;
import android.os.AsyncTask;

import com.example.quizz.Model.Question;

import java.util.List;

public class QuestionRepository {

    private ApplicationDatabase database;
    private QuestionDAO questionDAO;

    public QuestionRepository(Application application) {
        database = ApplicationDatabase.getInstance(application);
        questionDAO = database.questionDAO();
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
}
