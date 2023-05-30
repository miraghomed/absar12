package com.example.absar1;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;



import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

    Context context;
    FirebaseServices fbs;
    ArrayList<Recipe> recipeArrayList;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipeArrayList) {
        this.context = context;
        this.recipeArrayList = recipeArrayList;
    }

    @NonNull
    @Override
    public RecipeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.recipeicon,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Recipe recipe=recipeArrayList.get(position);
        holder.recipeName.setText(recipe.getName());
        fbs=FirebaseServices.getInstance();
        StorageReference storageRef= fbs.getStorage().getReference().child(recipe.getImage());

        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context)
                        .load(uri)
                        .into(holder.imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle any errors that occur when downloading the image
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
       TextView recipeName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeName=itemView.findViewById(R.id.RecipeN);
            imageView=itemView.findViewById(R.id.RecipePic);
        }
    }
}

