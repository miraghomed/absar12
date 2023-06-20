package com.example.absar1.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.absar1.R;
import com.example.absar1.classes.FirebaseServices;
import com.example.absar1.classes.Recipe;
import com.example.absar1.classes.RecipeAdapter;
import com.example.absar1.classes.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteRVFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteRVFrag extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Recipe> favoriteArrayList;
    RecipeAdapter recipeAdapter;
    FirebaseServices fbs;
    User user;
    ArrayList<String> recipepathArrayList,finalpaths;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavoriteRVFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteRVFragxml.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteRVFrag newInstance(String param1, String param2) {
        FavoriteRVFrag fragment = new FavoriteRVFrag();
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
        fbs=FirebaseServices.getInstance();
        Query query = fbs.getFire().collection("Users").whereEqualTo("email", fbs.getAuth().getCurrentUser().getEmail());
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();


                for (QueryDocumentSnapshot document : querySnapshot) {
                    this.user = document.toObject(User.class);
                    continueto();
                }
            } else {
                Exception e = task.getException();
                e.printStackTrace();
            }
        });

        recyclerView =getView().findViewById(R.id.recyclerFav);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fbs= FirebaseServices.getInstance();
        favoriteArrayList=new ArrayList<Recipe>();
        recipepathArrayList=new ArrayList<String>();
        EventChangeListener();



    }

    private void continueto() {
        ArrayList<String> paths= user.getFavoriteArrayList();
        int i=0;
        while (paths.size()>i) {
            DocumentReference recipeRef = fbs.getFire().collection("recipes").document(paths.get(i));
            recipeRef.get()
                    .addOnSuccessListener((DocumentSnapshot documentSnapshot) -> {
                        if (documentSnapshot.exists()) {
                            Recipe recipe = documentSnapshot.toObject(Recipe.class);
                            favoriteArrayList.add(recipe);
                            finalpaths.add(documentSnapshot.getId());
                            EventChangeListener();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
            i++;
        }
    }

    private void EventChangeListener() {
        if(finalpaths.size()==user.getFavoriteArrayList().size()) {
            recipeAdapter=new RecipeAdapter(getContext(),favoriteArrayList,finalpaths);
            recyclerView.setAdapter(recipeAdapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_r_v_frag, container, false);
    }
}