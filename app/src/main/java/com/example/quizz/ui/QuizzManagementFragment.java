package com.example.quizz.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizz.Model.Question;
import com.example.quizz.R;
import com.example.quizz.viewModel.ModifyQuestionViewModel;
import com.example.quizz.viewModel.QuizzManagementViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizzManagementFragment extends Fragment {

    private QuizzManagementViewModel viewModel;

    private RecyclerView recyclerView;
    private QuizzManagementAdapter adapter;

    FloatingActionButton addQuestionFAB;

    private Observer<List<Question>> questionsChanged = new Observer<List<Question>>() {
        @Override
        public void onChanged(List<Question> questions) {
            adapter.setData(questions);
        }
    };

    private View.OnClickListener addQuestionFABClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Navigation.findNavController(getView()).navigate(R.id.action_quizzManagementFragment_to_questionEditorFragment);
        }
    };

    private ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){

        private List<Question> questionsOrdered = new ArrayList<>();

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            adapter.notifyItemMoved(fromPosition, toPosition);

            List<Question> questions = viewModel.getQuestions().getValue();

            if(questionsOrdered.size() != questions.size())
                questionsOrdered = questions;

            Collections.swap(questions, fromPosition, toPosition);
            return false;
        }

        @Override
        public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            viewModel.updateQuestionsPosition(questionsOrdered);
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int index = viewHolder.getAdapterPosition();
                Question question = viewModel.getQuestions().getValue().get(index);
                viewModel.deleteQuestion(question);
        }
    });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new QuizzManagementAdapter();

        viewModel = new ViewModelProvider(getActivity()).get(QuizzManagementViewModel.class);
        viewModel.getQuestions().observe(getActivity(), questionsChanged);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_quizz_management, container, false);

        recyclerView = view.findViewById(R.id.quizzManagement_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        addQuestionFAB = view.findViewById(R.id.quizzManagement_add_floatingActionButton);
        addQuestionFAB.setOnClickListener(addQuestionFABClicked);

        return view;
    }
}