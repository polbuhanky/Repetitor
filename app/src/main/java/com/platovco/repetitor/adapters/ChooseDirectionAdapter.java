package com.platovco.repetitor.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.platovco.repetitor.R;
import com.platovco.repetitor.fragments.general.AddTutorInformation.AddTutorInformationFragment;

import java.util.ArrayList;

public class ChooseDirectionAdapter extends RecyclerView.Adapter<ChooseDirectionAdapter.ViewHolder> {
    ArrayList<String> brands = new ArrayList<>();
    Context context;
    LayoutInflater inflater;
    AddTutorInformationFragment fragment;


    public ChooseDirectionAdapter(Context context, ArrayList<String> brands, AddTutorInformationFragment fragment) {
        this.brands = brands;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ChooseDirectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_tip, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String brand = brands.get(position);
        holder.tipTV.setText(brand);
        holder.tipTV.setOnClickListener(view -> {
            Bundle result = new Bundle();
            result.putString("direction", brand);
            fragment.getParentFragmentManager().setFragmentResult("modelKey", result);
        });
    }

    @Override
    public int getItemCount() {
        return brands.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tipTV;

        public ViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);
            tipTV = itemView.findViewById(R.id.tipTV);
        }
    }
}
