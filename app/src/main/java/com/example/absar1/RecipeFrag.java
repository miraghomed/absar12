package com.example.absar1;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeFrag extends Fragment {

    private EditText etName,etCalories,etPTime,etInstructions,etIngredients;
    private ImageView imgRecipe;
    private Button btnADD;
    private FirebaseServices fbs;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecipeFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment recipeFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeFrag newInstance(String param1, String param2) {
        RecipeFrag fragment = new RecipeFrag();
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
        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        connectComponents();
    }
    private void connectComponents() {
        etName =getView().findViewById(R.id.etNameRF);
        etIngredients =getView().findViewById(R.id.etIngredientsRF);
        etCalories=getView().findViewById(R.id.etCaloriesRF);
        etPTime=getView().findViewById(R.id.etPTimeRF);
        etInstructions=getView().findViewById(R.id.etInstructionRF);
        btnADD=getView().findViewById(R.id.btnAddRF);
        fbs = FirebaseServices.getInstance() ;
        //imgRecipe=getView().findViewById(R.id.imgRecipeRF);
        btnADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=etName.getText().toString();
                String ingredients=etIngredients.getText().toString();
                String calories=etCalories.getText().toString();
                String time=etPTime.getText().toString();
                String instructions=etInstructions.getText().toString();
                //String img=imgRecipe.getText().toString();
                Recipe re = new Recipe(name,ingredients,calories,time,instructions);
                Map<String, Recipe> Re= new HashMap<>();

                fbs.getFire().collection("recipes").add(re)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                            }
                        });
            }
        });
    }

}