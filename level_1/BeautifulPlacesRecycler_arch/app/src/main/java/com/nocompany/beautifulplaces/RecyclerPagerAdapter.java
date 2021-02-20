package com.nocompany.beautifulplaces;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class RecyclerPagerAdapter extends RecyclerView.Adapter<PageViewHolder> {

    private List<Place> places;

    RecyclerPagerAdapter(List<Place> places) {
        this.places = places;
    }

    @Override
    public PageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.page, parent, false);
        return new PageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PageViewHolder holder, int position) {
        Place p = places.get(position);
        holder.setPlace(p);
    }

    @Override
    public int getItemCount() {
        return places.size();
    }
}
