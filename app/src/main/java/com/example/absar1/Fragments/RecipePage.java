package com.example.absar1.Fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.absar1.R;
import com.example.absar1.classes.FirebaseServices;
import com.example.absar1.classes.Recipe;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.StorageReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipePage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView etName,etCalories,etPTime,etInstructions,etIngredients;
    ImageView imgRecipe;
    private FirebaseServices fbs;
    String path;
    Recipe recipe;
    public RecipePage(String path) {
        this.path =path;
    }
    public RecipePage() {
        int i;
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipeHome.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipePage newInstance(String param1, String param2) {
        RecipePage fragment = new RecipePage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_page, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        fbs = FirebaseServices.getInstance() ;
        DocumentReference recipeRef=fbs.getFire().collection("recipes").document(path);
        recipeRef.get()
                .addOnSuccessListener((DocumentSnapshot documentSnapshot) -> {
                    if(documentSnapshot.exists()){
                        recipe=documentSnapshot.toObject(Recipe.class);
                        connectcomp();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

    private void connectcomp() {
        etName =getView().findViewById(R.id.RecipeNameTVcard);
        etIngredients =getView().findViewById(R.id.IngredientsTVcard);
        etCalories=getView().findViewById(R.id.CaloriesTVcard);
        etPTime=getView().findViewById(R.id.PTimeTVcard);
        etInstructions=getView().findViewById(R.id.InstructionsTVcard);
        imgRecipe=getView().findViewById(R.id.RecipeImage);
        StorageReference storageRef= fbs.getStorage().getReference().child(recipe.getImage());
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getActivity())
                        .load(uri)
                        .into(imgRecipe);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle any errors that occur when downloading the image
            }
        });
        etName.setText(recipe.getName());
        etIngredients.setText(recipe.getIngredient());
        etCalories.setText(recipe.getCalories());
        etPTime.setText(recipe.getPtime());
        etInstructions.setText(recipe.getInstructions());
    }
}