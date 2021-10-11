package com.example.quizz.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quizz.Model.Quizz;
import com.example.quizz.R;
import com.example.quizz.database.QuizzRepository;
import com.example.quizz.viewModel.QuizzManagementViewModel;
import com.example.quizz.viewModel.QuizzSelectionViewModel;

public class AddQuizzDialog extends AppCompatDialogFragment {

    QuizzSelectionViewModel quizzSelectionViewModel;

    EditText urlEditText;
    EditText defaultTitleEditText;
    EditText newQuizzTitleEditText;

    Button loadFromUrlButton;
    Button createNewQuizzButton;

    private View.OnClickListener loadFromUrlButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String url = urlEditText.getText().toString();
            String defaultTitle = defaultTitleEditText.getText().toString();

            if(url.isEmpty()) {
                Toast.makeText(getContext(), getString(R.string.url_empty_error), Toast.LENGTH_LONG).show();
                return;
            }

            quizzSelectionViewModel.loadFromUrl(url, defaultTitle);
            dismiss();
        }
    };

    private View.OnClickListener createNewQuizzButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String title = newQuizzTitleEditText.getText().toString();

            if(title.isEmpty()) {
                Toast.makeText(getContext(), getString(R.string.title_empty_error), Toast.LENGTH_LONG).show();
                return;
            }

            Quizz quizz = new Quizz(title);
            quizzSelectionViewModel.addQuizz(quizz);
            dismiss();
        }
    };

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        View view = getLayoutInflater().inflate(R.layout.dialog_add_quizz, null);

        urlEditText = view.findViewById(R.id.addQuizzDialog_url_editText);
        defaultTitleEditText = view.findViewById(R.id.addQuizzDialog_defaultTitle_editText);
        newQuizzTitleEditText = view.findViewById(R.id.addQuizzDialog_createNew_editText);

        loadFromUrlButton = view.findViewById(R.id.addQuizzDialog_validateUrl_button);
        loadFromUrlButton.setOnClickListener(loadFromUrlButtonClicked);

        createNewQuizzButton = view.findViewById(R.id.addQuizzDialog_createNew_Button);
        createNewQuizzButton.setOnClickListener(createNewQuizzButtonClicked);

        builder.setView(view)
                .setTitle(R.string.add_quizz_dialog_title);

        quizzSelectionViewModel = new ViewModelProvider(getActivity()).get(QuizzSelectionViewModel.class);

        return builder.create();
    }
}
