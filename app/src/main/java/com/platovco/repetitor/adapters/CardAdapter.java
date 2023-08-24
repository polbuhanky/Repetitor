package com.platovco.repetitor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.persistentOrderedSet.PersistentOrderedSetIterator;
import androidx.recyclerview.widget.RecyclerView;

import com.platovco.repetitor.databinding.CardBinding;
import com.platovco.repetitor.models.card;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {

    List<card> cardList = new ArrayList<>();

    public CardAdapter(List<card> cardList) {
        this.cardList = cardList;
    }

    @NonNull
    @Override
    public CardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CardBinding binding = CardBinding.inflate(li);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.MyViewHolder holder, int position) {
        card cardItem = cardList.get(position);
        holder.binding.content.setText(cardItem.getName());
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardBinding binding;
        public MyViewHolder(@NonNull CardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
