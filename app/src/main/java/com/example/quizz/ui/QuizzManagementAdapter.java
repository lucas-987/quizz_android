package com.example.quizz.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizz.Model.Question;
import com.example.quizz.Model.Quizz;
import com.example.quizz.R;

import java.util.ArrayList;
import java.util.List;

public class QuizzManagementAdapter extends RecyclerView.Adapter<QuizzManagementAdapter.QuizzManagementHolder> {

    List<Question> questions = new ArrayList<>();

    @NonNull
    @Override
    public QuizzManagementHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quizz_management_question_item, parent, false);
        return new QuizzManagementHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizzManagementHolder holder, int position) {
        Question currentQuestion = questions.get(position);

        holder.questionTextView.setText(currentQuestion.getQuestion());
        holder.answerTextView.setText(String.valueOf(currentQuestion.getAnwser()));
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public void setData(List<Question> questions) {
        this.questions = questions;
        notifyDataSetChanged();
    }

    class QuizzManagementHolder extends RecyclerView.ViewHolder {

        TextView questionTextView;
        TextView answerTextView;

        public QuizzManagementHolder(@NonNull View itemView) {
            super(itemView);

            questionTextView = itemView.findViewById(R.id.quizzManagementItem_question_textView);
            answerTextView = itemView.findViewById(R.id.quizzManagementItem_answer_textView);
        }
    }
}
