package com.strannik.beautifulplacesrecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<PageViewHolder> {

    private List<Place> places;

    public RecyclerAdapter(List<Place> places) {
        this.places = places;
    }

    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.page, parent, false);

        return new PageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PageViewHolder holder, int position) {
        Place p = places.get(position);

        holder.setPlace(p);
    }

    @Override
    public int getItemCount() {
        return places.size();
    }
}
