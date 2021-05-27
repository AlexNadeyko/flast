package com.example.flast.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flast.Adapter.TagAdapter;
import com.example.flast.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hendraanggrian.appcompat.widget.SocialAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

public class SearchPostsFragment extends Fragment {

    private SocialAutoCompleteTextView searchBar;
    private RecyclerView recyclerViewTags;
    private List<String> hashTags;
    private List<String> hashTagsNumbers;
    private TagAdapter tagAdapter;

    public SearchPostsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_posts, container, false);

        recyclerViewTags = view.findViewById(R.id.recycler_view_tags);
        recyclerViewTags.setHasFixedSize(true);
        recyclerViewTags.setLayoutManager(new LinearLayoutManager(getContext()));

        hashTags = new ArrayList<>();
        hashTagsNumbers = new ArrayList<>();
        tagAdapter = new TagAdapter(getContext(), hashTags, hashTagsNumbers);
        recyclerViewTags.setAdapter(tagAdapter);

        SearchFragment parent = (SearchFragment) getParentFragment();
        searchBar = parent.getView().findViewById(R.id.search_bar);

        readTags();

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterTags(s.toString());
            }
        });

        return view;
    }

    private void readTags() {
        FirebaseDatabase.getInstance().getReference().child("HashTags").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hashTags.clear();
                hashTagsNumbers.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    hashTags.add(dataSnapshot.getKey());
                    hashTagsNumbers.add(dataSnapshot.getChildrenCount() + "");
                }

                tagAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void filterTags(String query){
        List<String> searchTags = new ArrayList<>();
        List<String> searchtTagsNumbers = new ArrayList<>();

        for(String hashTag : hashTags){
            if (hashTag.toLowerCase().contains(query.toLowerCase())){
                searchTags.add(hashTag);
                searchtTagsNumbers.add(hashTagsNumbers.get(hashTags.indexOf(hashTag)));
            }
        }

        tagAdapter.filter(searchTags, searchtTagsNumbers);
    }

}
