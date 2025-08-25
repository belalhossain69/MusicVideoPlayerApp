package com.example.ultimatemusicplaylist.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ultimatemusicplaylist.PlayerActivity;
import com.example.ultimatemusicplaylist.R;
import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {

    private Context context;
    private List<MusicModel> musicList;

    public MusicAdapter(Context context, List<MusicModel> musicList) {
        this.context = context;
        this.musicList = musicList;
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_music, parent, false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        MusicModel music = musicList.get(position);
        holder.title.setText(music.getTitle());
        holder.thumbnail.setImageResource(music.getImageResId());
        holder.favorite.setVisibility(music.isFavorite() ? View.VISIBLE : View.GONE);

        // 🔹 Open video in PlayerActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PlayerActivity.class);
            intent.putExtra("VIDEO_URL", music.getYoutubeUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    static class MusicViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail, favorite;
        TextView title;

        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.music_thumbnail);
            title = itemView.findViewById(R.id.music_title);
            favorite = itemView.findViewById(R.id.favorite_icon);
        }
    }
}

