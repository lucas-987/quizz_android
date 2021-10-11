package com.example.quizz.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "questions", foreignKeys = @ForeignKey(entity = QuizzEntity.class, parentColumns = "id", childColumns = "quizzId", onDelete = CASCADE))
public class QuestionEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public long quizzId;

    public String sentence;

    public boolean anwser;

    public int order;

    public QuestionEntity(long quizzId, String sentence, boolean anwser, int order) {
        this.quizzId = quizzId;
        this.sentence = sentence;
        this.anwser = anwser;
        this.order = order;
    }
}
