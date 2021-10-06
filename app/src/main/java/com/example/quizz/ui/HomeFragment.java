package com.example.quizz.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.quizz.R;

public class HomeFragment extends Fragment {

    private Button playButton;
    private Button gameManagementButton;

    private View.OnClickListener playButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_gameSelectionFragment);
        }
    };

    private View.OnClickListener gameManagementButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_quizzManagementFragment);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        playButton = view.findViewById(R.id.homeFragment_play_button);
        playButton.setOnClickListener(playButtonClicked);

        gameManagementButton = view.findViewById(R.id.homeFragment_quizzManagement_button);
        gameManagementButton.setOnClickListener(gameManagementButtonClicked);

        return view;
    }
}