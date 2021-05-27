package com.example.flast.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.flast.Fragments.SearchPostsFragment;
import com.example.flast.Fragments.SearchUsersFragment;

public class TabsAdapter extends FragmentPagerAdapter {

    private Context context;
    private int totalTabs;

    public TabsAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public TabsAdapter(Context context, @NonNull FragmentManager fm, int totalTabs) {
        super(fm, totalTabs);
        this.context = context;
        this.totalTabs = totalTabs;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                SearchPostsFragment searchPostsFragment = new SearchPostsFragment();
                return searchPostsFragment;
            case 1:
                SearchUsersFragment searchUsersFragment = new SearchUsersFragment();
                return searchUsersFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
