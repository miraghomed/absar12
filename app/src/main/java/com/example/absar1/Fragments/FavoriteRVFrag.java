package com.example.absar1.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.absar1.R;
import com.example.absar1.classes.Recipe;

import java.util.ArrayList;

public class FavoriteRVFrag extends RecyclerView.Adapter<FavoriteRVFrag.MyViewHolder> {

    Context context;
    ArrayList<Recipe> recipeArrayList;

    public FavoriteRVFrag(Context context, ArrayList<Recipe> recipeArrayList) {
        this.context = context;
        this.recipeArrayList = recipeArrayList;
    }

    @NonNull
    @Override
    public FavoriteRVFrag.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.recipeicon,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Recipe recipe=recipeArrayList.get(position);

        holder.recipeName.setText(recipe.getName());
        holder.calories.setText(recipe.getCalories());
        holder.ingredients.setText(recipe.getIngredient());
        holder.pTime.setText(recipe.getPtime());
        holder.instructions.setText(recipe.getInstructions());
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

