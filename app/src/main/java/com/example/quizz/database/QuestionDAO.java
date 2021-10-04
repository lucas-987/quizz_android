package com.example.quizz.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface QuestionDAO {

    @Insert
    void insert(QuestionEntity question);

    @Update
    void update(QuestionEntity question);

    @Delete
    void delete(QuestionEntity question);
}
