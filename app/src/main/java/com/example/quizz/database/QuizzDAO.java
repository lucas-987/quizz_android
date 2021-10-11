package com.example.quizz.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.quizz.Model.Quizz;

import java.util.List;

import retrofit2.http.GET;

@Dao
public interface QuizzDAO {

    @Insert
    long insert(QuizzEntity quizz);

    @Update
    void update(QuizzEntity quizz);

    @Delete
    void delete(QuizzEntity quizz);

    @Transaction
    @Query("DELETE FROM quizzs WHERE url = :url")
    void deleteByUrl(String url);

    @Transaction
    @Query("SELECT * FROM quizzs")
    LiveData<List<QuizzWithQuestions>> getAllQuizzs();
}
