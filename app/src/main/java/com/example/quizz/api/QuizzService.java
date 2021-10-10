package com.example.quizz.api;

import com.example.quizz.Model.Quizz;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface QuizzService {
    @GET("quizz_monde_animal.json")
    Call<Quizz> quizzExample();

    @GET
    Call<Quizz> quizzFromUrl(@Url String url);
}
