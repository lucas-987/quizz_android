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

public class GameSelectionAdapter extends RecyclerView.Adapter<GameSelectionAdapter.GameSelectionHolder> {

    private List<Quizz> quizzes = new ArrayList<>();
    private GameSelectionAdapterListener gameSelectionAdapterListener;

    public interface GameSelectionAdapterListener {

        void onButtonClicked(View v, Quizz quizz);
    }

    public GameSelectionAdapter(GameSelectionAdapterListener gameSelectionAdapterListener) {
        this.gameSelectionAdapterListener = gameSelectionAdapterListener;
    }

    @NonNull
    @Override
    public GameSelectionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.game_selection_quizz_item, parent, false);
        return new GameSelectionAdapter.GameSelectionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GameSelectionHolder holder, int position) {
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

    class GameSelectionHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private Button playButton;

        public GameSelectionHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.gameSelectionItem_quizzTitle_TextView);

            playButton = itemView.findViewById(R.id.gameSelectionItem_play_button);
            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Quizz quizz = quizzes.get(getAdapterPosition());

                    gameSelectionAdapterListener.onButtonClicked(view, quizz);
                }
            });
        }
    }
}
