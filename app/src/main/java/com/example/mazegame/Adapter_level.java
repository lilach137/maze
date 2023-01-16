package com.example.mazegame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class Adapter_level extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public interface LevelListener {
        void click(Level level, int position);
    }
    private Activity activity;
    private ArrayList<Level> levels;
    private LevelListener levelListener;


    public Adapter_level(Activity activity, ArrayList<Level> levels) {
        this.activity = activity;
        this.levels = levels;
    }

    public Adapter_level setLevelClickListener(LevelListener levelListener) {
        this.levelListener = levelListener;
        return this;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_level, parent, false);
        LevelHolder holder = new LevelHolder(view);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        final LevelHolder holder = (LevelHolder) viewHolder;
        Level level = getLevel(position);


        holder.menuLevel_LBL_lockedLevel.setText("level " + level.getNum());
        holder.topic_name.setText("level " + level.getNum());
    }


    @Override
    public int getItemCount() {
        return levels.size();
    }

    public Level getLevel(int position){
        return levels.get(position);
    }

    class LevelHolder extends RecyclerView.ViewHolder {

        private MaterialTextView menuLevel_LBL_lockedLevel;
        private MaterialTextView topic_name;


        public LevelHolder(@NonNull View itemView) {
            super(itemView);
            menuLevel_LBL_lockedLevel =itemView.findViewById(R.id.menuLevel_LBL_lockedLevel);
            topic_name = itemView.findViewById(R.id.topic_name);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (levelListener != null) {
                        levelListener.click(getLevel(getAdapterPosition()), getAdapterPosition());
                    }
                }
            });

        }
    }
}