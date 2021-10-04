package com.example.quizz.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "quizzs")
public class QuizzEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String title;


    public QuizzEntity(String title) {
        this.title = title;
    }
}
