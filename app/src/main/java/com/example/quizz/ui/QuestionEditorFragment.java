package com.example.quizz.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quizz.Model.Question;
import com.example.quizz.R;
import com.example.quizz.viewModel.ModifyQuestionViewModel;
import com.example.quizz.viewModel.QuizzManagementViewModel;

public class QuestionEditorFragment extends Fragment {

    private boolean isNewQuestion;

    private EditText questionEditText;
    private EditText answerEditText;
    private Button confirmButton;

    private QuizzManagementViewModel quizzManagementViewModel;
    private ModifyQuestionViewModel modifyQuestionViewModel;

    private View.OnClickListener confirmButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String question = questionEditText.getText().toString();
            String answerString = answerEditText.getText().toString();

            if(question.isEmpty() || answerString.isEmpty()) {
                Toast.makeText(getContext(), "", Toast.LENGTH_LONG).show();
                return;
            }

            boolean answer = Boolean.valueOf(answerString);

            if(isNewQuestion)
                quizzManagementViewModel.addQuestion(new Question(question, answer));
            else
                Toast.makeText(getContext(), "TODO UPDATE", Toast.LENGTH_SHORT); // TODO implement update // TODO catch backbutton action on all fragment to navigate to previous destination

            Navigation.findNavController(getView()).navigate(R.id.action_questionEditorFragment_to_quizzManagementFragment);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewModelProvider viewModelProvider = new ViewModelProvider(getActivity());
        quizzManagementViewModel = viewModelProvider.get(QuizzManagementViewModel.class);
        modifyQuestionViewModel = viewModelProvider.get(ModifyQuestionViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_question_editor, container, false);

        confirmButton = view.findViewById(R.id.questionEditor_add_button);
        confirmButton.setOnClickListener(confirmButtonClicked);

        questionEditText = view.findViewById(R.id.questionEditor_question_editText);
        answerEditText = view.findViewById(R.id.questionEditor_answer_editText);

        isNewQuestion = QuestionEditorFragmentArgs.fromBundle(getArguments()).getNewQuestion();

        if(!isNewQuestion) {
            Question questionToUpdate = modifyQuestionViewModel.getQuestion();
            questionEditText.setText(questionToUpdate.getQuestion());
            answerEditText.setText(String.valueOf(questionToUpdate.getAnwser()));
        }

        return view;
    }
}