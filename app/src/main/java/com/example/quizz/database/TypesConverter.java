package com.example.quizz.database;

import androidx.room.TypeConverter;

import com.example.quizz.Model.Question;
import com.example.quizz.Model.Quizz;

import java.util.ArrayList;
import java.util.List;

public class TypesConverter {

    public static Quizz QuizzWithQuestionsToQuizz(QuizzWithQuestions quizzWithQuestions) {
        List<Question> questions = new ArrayList<>();
        for(QuestionEntity questionEntity : quizzWithQuestions.questions) {
            questions.add(new Question(questionEntity.id, questionEntity.sentence, questionEntity.anwser));
        }

        Quizz result = new Quizz(quizzWithQuestions.quizz.id, quizzWithQuestions.quizz.title, questions);
        return result;
    }


    /*
    public static QuizzWithQuestions QuizzToQuizzWithQuestions(Quizz quizz) {

    }*/

    public static Question QuestionEntityToQuestion(QuestionEntity questionEntity) {
        Question result = new Question(questionEntity.id, questionEntity.sentence, questionEntity.anwser, questionEntity.order);
        return result;
    }
}
