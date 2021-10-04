package com.example.quizz.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "questions")
public class QuestionEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public long quizzId;

    public String sentence;

    public boolean anwser;


    public QuestionEntity(long quizzId, String sentence, boolean anwser) {
        this.quizzId = quizzId;
        this.sentence = sentence;
        this.anwser = anwser;
    }
}
