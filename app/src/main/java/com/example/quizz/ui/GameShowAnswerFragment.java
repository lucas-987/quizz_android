package com.example.quizz.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizz.R;
import com.example.quizz.viewModel.GameViewModel;

public class GameShowAnswerFragment extends Fragment {

    private GameViewModel gameViewModel;

    private TextView answerTextView;
    private Button showAnswerButton;

    private View.OnClickListener showAnswerButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            answerTextView.setVisibility(View.VISIBLE);
            gameViewModel.setAnwserViewed(true);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameViewModel = new ViewModelProvider(getActivity()).get(GameViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_game_show_answer, container, false);

        answerTextView = view.findViewById(R.id.showAnswer_answer_textView);
        boolean answer = gameViewModel.getSelectedQuizz().getQuestions().get(gameViewModel.getSelectedQuestionIndex()).getAnwser();
        answerTextView.setText(String.valueOf(answer));

        showAnswerButton = view.findViewById(R.id.showAnswer_show_Button);
        showAnswerButton.setOnClickListener(showAnswerButtonClicked);

        return view;
    }
}