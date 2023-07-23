package com.platovco.repetitor.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.platovco.repetitor.R;
import com.platovco.repetitor.fragments.tutor.TutorChoiceDirection.TutorChoiceDirectionFragment;

import java.util.ArrayList;

public class ChooseDirectionAdapter extends RecyclerView.Adapter<ChooseDirectionAdapter.ViewHolder> {
    ArrayList<String> brands = new ArrayList<>();
    Context context;
    LayoutInflater inflater;
    TutorChoiceDirectionFragment fragment;


    public ChooseDirectionAdapter(Context context, ArrayList<String> brands, TutorChoiceDirectionFragment fragment) {
        this.brands = brands;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ChooseDirectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_direction, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String brand = brands.get(position);
        holder.name.setText(brand);
        holder.cardView.setOnClickListener(view -> {
            Bundle result = new Bundle();
            result.putString("direction", brand);
            fragment.getParentFragmentManager().setFragmentResult("modelKey", result);
            Navigation.findNavController(view).navigateUp();
        });
    }

    @Override
    public int getItemCount() {
        return brands.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final CardView cardView;

        public ViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            cardView = itemView.findViewById(R.id.card);
        }
    }
}
