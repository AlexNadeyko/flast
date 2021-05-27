package com.example.flast.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flast.R;

import java.util.List;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder>{

    private Context context;
    private List<String> tags;
    private List<String> tagsNumbers;

    public TagAdapter(Context context, List<String> tags, List<String> tagsNumbers) {
        this.context = context;
        this.tags = tags;
        this.tagsNumbers = tagsNumbers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tag_item, parent, false);

        return new TagAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tag.setText("# " + tags.get(position));
        holder.numberOfPosts.setText(tagsNumbers.get(position) + " posts");
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tag;
        public TextView numberOfPosts;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tag = itemView.findViewById(R.id.hash_tag);
            numberOfPosts = itemView.findViewById(R.id.number_of_posts);
        }
    }

    public void filter(List<String> filterTags, List<String> filterTagsCount){
        this.tags = filterTags;
        this.tagsNumbers = filterTagsCount;

        notifyDataSetChanged();
    }
}
