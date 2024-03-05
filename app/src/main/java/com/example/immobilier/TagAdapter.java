package com.example.immobilier;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.stream.Collectors;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagViewHolder> {

    private List<Tag> tags;

    public TagAdapter(List<Tag> tags) {
        this.tags = tags;
    }

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag, parent, false);
        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        Tag tag = tags.get(position);
        holder.checkBox.setText(tag.getName());
        holder.checkBox.setChecked(tag.isSelected());
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tag.setSelected(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public List<String> getTags() {
        return tags.stream()
                .filter(Tag::isSelected)
                .map(Tag::getName)
                .collect(Collectors.toList());
    }

    public static class TagViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;

        public TagViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBoxTag);
        }
    }
}
