package com.example.quizz.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.quizz.Model.Question;

import java.util.List;

@Dao
public abstract class QuestionDAO {

    @Insert
    abstract void insert(QuestionEntity question);

    @Update
    abstract void update(QuestionEntity question);

    @Delete
    abstract void delete(QuestionEntity question);

    @Query("SELECT * FROM questions WHERE quizzId = :quizzId ORDER BY `order`")
    abstract LiveData<List<QuestionEntity>> getQuestionFromQuizz(long quizzId);

    @Query("UPDATE questions SET `order` = :position WHERE id = :questionId ")
    abstract void updatePosition(long questionId, int position);

    /**
     *
     * @param questions
     */
    @Transaction
    void updatePositions(List<Question> questions) {
        for (int i = 0; i<questions.size(); i++) {
            Question question = questions.get(i);
            updatePosition(question.getId(), i);
        }
    }
}
