package com.example.quizz.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizz.Model.Question;
import com.example.quizz.Model.Quizz;
import com.example.quizz.R;
import com.example.quizz.viewModel.QuizzManagementViewModel;
import com.example.quizz.viewModel.QuizzSelectionViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class QuizzManagementSelectionFragment extends Fragment {

    // TODO update quizz : if click on quizz item, dialog to modify title

    private RecyclerView recyclerView;
    private QuizzManagementSelectionAdapter adapter;

    private QuizzSelectionViewModel quizzSelectionViewModel;

    private FloatingActionButton addQuizzFAB;

    private Observer<List<Quizz>> quizzListChanged = new Observer<List<Quizz>>() {
        @Override
        public void onChanged(List<Quizz> quizzes) {
            adapter.setData(quizzes);
        }
    };

    private QuizzManagementSelectionAdapter.QuizzManagementSelectionAdapterListener quizzManagementSelectionAdapterListener = new QuizzManagementSelectionAdapter.QuizzManagementSelectionAdapterListener() {
        @Override
        public void onButtonClicked(View v, Quizz quizz) {
            QuizzManagementViewModel quizzManagementViewModel = new ViewModelProvider(getActivity()).get(QuizzManagementViewModel.class);
            quizzManagementViewModel.setQuizzId(quizz.getId());

            Navigation.findNavController(getView()).navigate(R.id.action_quizzManagementSelectionFragment_to_quizzManagementFragment);
        }
    };

    private View.OnClickListener addQuizzFABClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new AddQuizzDialog().show(getParentFragmentManager(), "AddQuizzDialog");
        }
    };

    private ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int index = viewHolder.getAdapterPosition();
            Quizz quizz = quizzSelectionViewModel.getAllQuizzes().getValue().get(index);
            quizzSelectionViewModel.deleteQuizz(quizz);
        }
    });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new QuizzManagementSelectionAdapter(quizzManagementSelectionAdapterListener);

        quizzSelectionViewModel = new ViewModelProvider(getActivity()).get(QuizzSelectionViewModel.class);
        quizzSelectionViewModel.getAllQuizzes().observe(getActivity(), quizzListChanged);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_quizz_management_selection, container, false);

        recyclerView = view.findViewById(R.id.quizzManagementSelection_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        addQuizzFAB = view.findViewById(R.id.quizzManagementSelection_addQuizz_FAB);
        addQuizzFAB.setOnClickListener(addQuizzFABClicked);

        return view;
    }
}