package com.example.absar1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Recipe> recipeArrayList;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipeArrayList) {
        this.context = context;
        this.recipeArrayList = recipeArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return recipeArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
       TextView recipeName,ingredients,calories,pTime,instructions;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeName=itemView.findViewById(R.id.RecipeNameTVcard);
            ingredients=itemView.findViewById(R.id.IngredientsTVcard);
            calories=itemView.findViewById(R.id.CaloriesTVcard);
            pTime=itemView.findViewById(R.id.PTimeTVcard);
            instructions=itemView.findViewById(R.id.InstructionsTVcard);
        }
    }
}

