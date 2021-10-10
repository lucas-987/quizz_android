package com.example.quizz.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizz.Model.Quizz;
import com.example.quizz.R;

import java.util.ArrayList;
import java.util.List;

public class QuizzManagementSelectionAdapter extends RecyclerView.Adapter<QuizzManagementSelectionAdapter.QuizzManagementSelectionHolder> {

    private List<Quizz> quizzes = new ArrayList<>();
    private QuizzManagementSelectionAdapterListener quizzManagementSelectionAdapterListener;

    public interface QuizzManagementSelectionAdapterListener {

        void onButtonClicked(View v, Quizz quizz);
    }

    public QuizzManagementSelectionAdapter(QuizzManagementSelectionAdapterListener quizzManagementSelectionAdapterListener) {
        this.quizzManagementSelectionAdapterListener = quizzManagementSelectionAdapterListener;
    }

    @NonNull
    @Override
    public QuizzManagementSelectionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quizz_management_selection_quizz_item, parent, false);
        return new QuizzManagementSelectionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizzManagementSelectionHolder holder, int position) {
        Quizz currentQuizz = quizzes.get(position);

        holder.titleTextView.setText(currentQuizz.getTitle());
    }

    @Override
    public int getItemCount() {
        return quizzes.size();
    }

    public void setData(List<Quizz> quizzes) {
        this.quizzes = quizzes;
        notifyDataSetChanged();
    }

    class QuizzManagementSelectionHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private Button manageButton;

        public QuizzManagementSelectionHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.quizzManagementSelectionItem_quizzTitle_TextView);

            manageButton = itemView.findViewById(R.id.quizzManagementSelectionItem_manage_button);
            manageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Quizz quizz = quizzes.get(getAdapterPosition());

                    quizzManagementSelectionAdapterListener.onButtonClicked(view, quizz);
                }
            });
        }
    }
}
