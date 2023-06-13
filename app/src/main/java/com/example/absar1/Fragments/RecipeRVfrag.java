package com.example.absar1.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.absar1.R;
import com.example.absar1.classes.FirebaseServices;
import com.example.absar1.classes.Recipe;
import com.example.absar1.classes.RecipeAdapter;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeRVfrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeRVfrag extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Recipe> recipeArrayList;
    ImageView favorite;
    ArrayList<String> recipepathArrayList;

    RecipeAdapter recipeAdapter;
    FirebaseServices db;
    ProgressDialog progressDialog;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecipeRVfrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Reciperv.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeRVfrag newInstance(String param1, String param2) {
        RecipeRVfrag fragment = new RecipeRVfrag();
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
    public void onStart() {
        super.onStart();
/*
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching com.example.absar1.Data...");
        progressDialog.show(); */


            recyclerView =getView().findViewById(R.id.recyclerview);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            db= FirebaseServices.getInstance();
            recipeArrayList=new ArrayList<Recipe>();
            recipepathArrayList=new ArrayList<String>();
            recipeAdapter= new RecipeAdapter(getActivity(),recipeArrayList,recipepathArrayList);
            recyclerView.setAdapter(recipeAdapter);
            favorite=getView().findViewById(R.id.GotoFav);
            EventChangeListener();

            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoFavorite();
                }
            });
    }

    private void gotoFavorite() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayoutMain, new FavoriteRVFrag());
        ft.commit();
    }

    private void EventChangeListener() {

        //orderBy("recipeName", Query.Direction.ASCENDING)
        db.getFire().collection("recipes").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
/*
                        if (error != null) {
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("Firestore error", error.getMessage());
                            return;
                        } */

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                recipeArrayList.add(dc.getDocument().toObject(Recipe.class));
                                recipepathArrayList.add(dc.getDocument().getId());
                            }

                            recipeAdapter.notifyDataSetChanged();
                            /*
                            if (progressDialog.isShowing())
                                progressDialog.dismiss(); */
                        }
                    }
                    });
       }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reciperv, container, false);
    }
}