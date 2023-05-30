package com.example.absar1;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    int SELECT_PICTURE = 200;

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
        imgRecipe=getView().findViewById(R.id.imgRecipeRF);
        imgRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO:open gallery and select image
                openGalleryAndSelectPhoto();
            }
        });
        btnADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=etName.getText().toString();
                String ingredients=etIngredients.getText().toString();
                String calories=etCalories.getText().toString();
                String time=etPTime.getText().toString();
                String instructions=etInstructions.getText().toString();
                String path=UploadImageToFirebase();
                Recipe re = new Recipe(name,ingredients,calories,time,instructions,path);
                Map<String, Recipe> Re= new HashMap<>();
                fbs.getFire().collection("recipes").add(re)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference aVoid) {
                                gotoAddRecipe();
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

    private void openGalleryAndSelectPhoto() {
        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    imgRecipe.setImageURI(selectedImageUri);
                }
            }
        }
    }

    private String UploadImageToFirebase(){
        BitmapDrawable drawable = (BitmapDrawable) imgRecipe.getDrawable();
        Bitmap Image = drawable.getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Image.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[]data= baos.toByteArray();
        StorageReference ref =fbs.getStorage().getReference("RecipePictures"+ UUID.randomUUID().toString());
        UploadTask uploadTask =ref.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error with the picture", e);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            }
        });
        return ref.getPath();
    }

    public void gotoAddRecipe()
    {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayoutMain, new RecipeRVfrag());
        ft.commit();
    }
}