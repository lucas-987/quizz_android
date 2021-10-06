package com.example.quizz.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizz.Model.Quizz;
import com.example.quizz.R;
import com.example.quizz.viewModel.GameSelectionViewModel;
import com.example.quizz.viewModel.GameViewModel;

import java.util.List;

public class GameSelectionFragment extends Fragment {

    private RecyclerView recyclerView;
    private GameSelectionAdapter adapter;

    private GameSelectionViewModel gameSelectionViewModel;

    private GameSelectionAdapter.GameSelectionAdapterListener gameSelectionAdapterListener = new GameSelectionAdapter.GameSelectionAdapterListener() {
        @Override
        public void onButtonClicked(View v, Quizz quizz) {
            GameViewModel gameViewModel = new ViewModelProvider(getActivity()).get(GameViewModel.class);
            gameViewModel.setSelectedQuizz(quizz);

            Navigation.findNavController(getView()).navigate(R.id.action_gameSelectionFragment_to_gameFragment);
        }
    };

    private Observer<List<Quizz>> quizzListChanged = new Observer<List<Quizz>>() {
        @Override
        public void onChanged(List<Quizz> quizzes) {
            adapter.setData(quizzes);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new GameSelectionAdapter(gameSelectionAdapterListener);

        gameSelectionViewModel = new ViewModelProvider(getActivity()).get(GameSelectionViewModel.class);
        gameSelectionViewModel.getAllQuizzes().observe(getActivity(), quizzListChanged);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_selection, container, false);

        recyclerView = view.findViewById(R.id.gameSelectionFragment_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }


}