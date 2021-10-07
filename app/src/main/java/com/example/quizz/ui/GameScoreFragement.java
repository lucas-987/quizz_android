package com.example.quizz.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quizz.R;
import com.example.quizz.viewModel.GameViewModel;

public class GameScoreFragement extends Fragment {

    GameViewModel gameViewModel;

    TextView scoreTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameViewModel = new ViewModelProvider(getActivity()).get(GameViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_game_score, container, false);

        scoreTextView = view.findViewById(R.id.gameScore_score_textView);
        scoreTextView.setText(String.valueOf(gameViewModel.getScore()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_UP && i == KeyEvent.KEYCODE_BACK) {
                    gameViewModel.reset();
                    Navigation.findNavController(getView()).navigate(R.id.action_gameScoreFragement_to_homeFragment);
                    return true;
                }
                return false;
            }
        });
    }
}