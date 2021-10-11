package com.example.quizz.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizz.Model.Question;
import com.example.quizz.R;
import com.example.quizz.viewModel.GameViewModel;

import java.util.List;

public class GameFragment extends Fragment {

    // TODO : display question number (ex : 3/6)

    private GameViewModel gameViewModel;

    private TextView questionTextView;
    private TextView questionNumberTextView;
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button showAnwserButton;

    private View.OnClickListener trueButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            checkAnwser(true);
        }
    };

    private View.OnClickListener falseButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            checkAnwser(false);
        }
    };

    private View.OnClickListener nextButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            nextQuestion();
        }
    };

    private View.OnClickListener showAnwserButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Navigation.findNavController(getView()).navigate(R.id.action_gameFragment_to_gameShowAnswerFragment);
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

        View view = inflater.inflate(R.layout.fragment_game, container, false);

        questionTextView = view.findViewById(R.id.game_question_textView);
        int currentQuestionIndex = gameViewModel.getSelectedQuestionIndex();
        questionTextView.setText(gameViewModel.getSelectedQuizz().getQuestions().get(currentQuestionIndex).getQuestion());

        questionNumberTextView = view.findViewById(R.id.game_questionNumber_textView);
        questionNumberTextView.setText((gameViewModel.getSelectedQuestionIndex() + 1) + "/" + gameViewModel.getSelectedQuizz().getQuestions().size());

        trueButton = view.findViewById(R.id.game_true_button);
        trueButton.setOnClickListener(trueButtonClicked);

        falseButton = view.findViewById(R.id.game_false_button);
        falseButton.setOnClickListener(falseButtonClicked);

        nextButton = view.findViewById(R.id.game_next_button);
        nextButton.setOnClickListener(nextButtonClicked);

        showAnwserButton = view.findViewById(R.id.game_showAnwser_button);
        showAnwserButton.setOnClickListener(showAnwserButtonClicked);

        return view;
    }

    private void checkAnwser(boolean anwser) {
        if(gameViewModel.isAnwserViewed()) {
            Toast.makeText(getContext(), getString(R.string.anwser_viewed_message), Toast.LENGTH_LONG).show();
            return;
        }

        int selectedQuestionIndex = gameViewModel.getSelectedQuestionIndex();
        Question currentQuestion = gameViewModel.getSelectedQuizz().getQuestions().get(selectedQuestionIndex);

        if(currentQuestion.getAnwser() == anwser)
            gameViewModel.setScore(gameViewModel.getScore() + 1);
        else
            Toast.makeText(getContext(), R.string.wrong_anwser, Toast.LENGTH_LONG);

        nextQuestion();
    }

    private void nextQuestion() {
        int currentQuestionIndex = gameViewModel.getSelectedQuestionIndex();
        currentQuestionIndex++;
        gameViewModel.setSelectedQuestionIndex(currentQuestionIndex);
        int nbQuestions = gameViewModel.getSelectedQuizz().getQuestions().size();

        if(currentQuestionIndex == nbQuestions) {
            Navigation.findNavController(getView()).navigate(R.id.action_gameFragment_to_gameScoreFragement);
        }
        else {
            this.questionTextView.setText(gameViewModel.getSelectedQuizz().getQuestions().get(currentQuestionIndex).getQuestion());
            gameViewModel.setAnwserViewed(false);
            this.questionNumberTextView.setText((currentQuestionIndex + 1) + "/" + nbQuestions);
        }
    }
}