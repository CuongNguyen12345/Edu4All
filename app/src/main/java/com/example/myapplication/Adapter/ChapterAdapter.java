package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Chapter;
import com.example.myapplication.R;

import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder> {

    private List<Chapter> chapterList;
    private OnLearnClickListener listener;

    // Interface callback để Activity nhận sự kiện click
    public interface OnLearnClickListener {
        void onLearnClick(int position);
    }

    public void setOnLearnClickListener(OnLearnClickListener listener) {
        this.listener = listener;
    }

    public ChapterAdapter(List<Chapter> chapterList) {
        this.chapterList = chapterList;
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_chapter, parent, false);
        return new ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        Chapter chapter = chapterList.get(position);

        holder.tvChapterTitle.setText(chapter.getTitle());
        holder.tvLessonCount.setText(chapter.getLessonCount());
        holder.progressBar.setProgress(chapter.getProgress());

        // Sự kiện click nút Học Ngay
        holder.btnLearnNow.setOnClickListener(v -> {
            if (listener != null) {
                listener.onLearnClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public static class ChapterViewHolder extends RecyclerView.ViewHolder {
        TextView tvChapterTitle, tvLessonCount, btnLearnNow;
        ProgressBar progressBar;

        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChapterTitle = itemView.findViewById(R.id.tvChapterTitle);
            tvLessonCount = itemView.findViewById(R.id.tvLessonCount);
            progressBar = itemView.findViewById(R.id.progressBar);
            btnLearnNow = itemView.findViewById(R.id.btnLearnNow); // ← Thêm dòng này
        }
    }
}
