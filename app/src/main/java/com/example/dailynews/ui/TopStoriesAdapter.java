package com.example.dailynews.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailynews.R;
import com.example.dailynews.model.NewsItem;
import com.example.dailynews.util.TimeUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TopStoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HERO = 0;
    private static final int TYPE_SECONDARY = 1;

    private final List<NewsItem> items;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(NewsItem item);
    }

    public TopStoriesAdapter(List<NewsItem> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HERO : TYPE_SECONDARY;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_HERO) {
            View v = inflater.inflate(R.layout.item_top_story, parent, false);
            return new HeroViewHolder(v);
        } else {
            View v = inflater.inflate(R.layout.item_secondary_story, parent, false);
            return new SecondaryViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NewsItem item = items.get(position);
        if (holder instanceof HeroViewHolder) {
            ((HeroViewHolder) holder).bind(item);
        } else if (holder instanceof SecondaryViewHolder) {
            ((SecondaryViewHolder) holder).bind(item);
        }
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return Math.min(items.size(), 3);
    }

    static class HeroViewHolder extends RecyclerView.ViewHolder {
        ImageView ivHero;
        TextView tvHeroTitle;
        TextView tvHeroSource;
        TextView tvHeroTime;

        HeroViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHero = itemView.findViewById(R.id.ivHero);
            tvHeroTitle = itemView.findViewById(R.id.tvHeroTitle);
            tvHeroSource = itemView.findViewById(R.id.tvHeroSource);
            tvHeroTime = itemView.findViewById(R.id.tvHeroTime);
        }

        void bind(NewsItem item) {
            tvHeroTitle.setText(item.getTitle());
            tvHeroSource.setText(item.getSource());
            tvHeroTime.setText(TimeUtils.getRelativeTime(item.getPublishedAt()));
            Picasso.get()
                    .load(item.getImageUrl())
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .into(ivHero);
        }
    }

    static class SecondaryViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumb;
        TextView tvTitle;
        TextView tvMeta;

        SecondaryViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumb = itemView.findViewById(R.id.ivThumb);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvMeta = itemView.findViewById(R.id.tvMeta);
        }

        void bind(NewsItem item) {
            tvTitle.setText(item.getTitle());
            String relative = TimeUtils.getRelativeTime(item.getPublishedAt());
            tvMeta.setText(item.getSource() + " • " + relative);
            Picasso.get()
                    .load(item.getImageUrl())
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .into(ivThumb);
        }
    }
}
