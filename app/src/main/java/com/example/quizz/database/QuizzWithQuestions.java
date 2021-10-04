package com.example.quizz.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class QuizzWithQuestions {
    @Embedded public QuizzEntity quizz;

    @Relation(
        parentColumn = "id",
        entityColumn = "quizzId"
    )
    public List<QuestionEntity> questions;
}
