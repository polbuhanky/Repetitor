package com.platovco.repetitor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.platovco.repetitor.databinding.CardBinding;
import com.platovco.repetitor.models.cardStudent;

import java.util.ArrayList;
import java.util.List;

public class CardAdapterTutor extends RecyclerView.Adapter<CardAdapterTutor.MyViewHolder> {

    List<cardStudent> cardList = new ArrayList<>();
    Context context;


    public CardAdapterTutor(List<cardStudent> cardList, Context context) {
        this.cardList = cardList;
        this.context = context;
    }

    @NonNull
    @Override
    public CardAdapterTutor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CardBinding binding = CardBinding.inflate(li);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapterTutor.MyViewHolder holder, int position) {
        cardStudent cardItem = cardList.get(position);
        holder.binding.content.setText(cardItem.getName());
        Glide
                .with(context)
                .load(cardItem.getPhoto())
                .into(holder.binding.image);
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
