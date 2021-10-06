package com.example.quizz.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.quizz.Model.Question;
import com.example.quizz.Model.Quizz;

@Database(entities = {QuizzEntity.class, QuestionEntity.class}, version = 2)
public abstract class ApplicationDatabase extends RoomDatabase {

    private static ApplicationDatabase instance;

    public abstract QuizzDAO quizzDAO();
    public abstract QuestionDAO questionDAO();

    public static synchronized ApplicationDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                        ApplicationDatabase.class, "database")
                        .fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
