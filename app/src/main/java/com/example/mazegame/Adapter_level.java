package com.example.mazegame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class Adapter_level extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public interface LevelListener {
        void click(Level level, int position,boolean premium);
    }
    private Activity activity;
    private ArrayList<Level> levels;
    private LevelListener levelListener;
    private boolean premium;
    private int lastLevel;

    public Adapter_level(Activity activity, ArrayList<Level> levels, boolean premium, int lastLevel) {
        this.activity = activity;
        this.levels = levels;
        this.premium = premium;
        this.lastLevel = lastLevel;
    }

    public void setLevelClickListener(LevelListener levelListener) {
        this.levelListener = levelListener;
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

        holder.disablePremiumCards(position, premium ,lastLevel);
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

        private LinearLayoutCompat menuLevel_LAY_locked;


        public LevelHolder(@NonNull View itemView) {
            super(itemView);
            menuLevel_LBL_lockedLevel =itemView.findViewById(R.id.menuLevel_LBL_lockedLevel);
            topic_name = itemView.findViewById(R.id.topic_name);
            menuLevel_LAY_locked = itemView.findViewById(R.id.menuLevel_LAY_locked);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (levelListener != null) {
                        if (menuLevel_LAY_locked.getVisibility()==(View.INVISIBLE))
                            levelListener.click(getLevel(getAdapterPosition()), getAdapterPosition(),premium);
                    }
                }
            });

        }
        public void disablePremiumCards(int position, boolean premium ,int lastLevel) {
            if (!premium && position < lastLevel) {
                menuLevel_LAY_locked.setVisibility(View.INVISIBLE);
            } else if (premium && position < 4){
                menuLevel_LAY_locked.setVisibility(View.INVISIBLE);
            }

        }
    }
}