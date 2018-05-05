package com.misht.shakepaws.home.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.misht.shakepaws.R;
import com.misht.shakepaws.home.DetailActivity;
import com.misht.shakepaws.home.adapter.FPetAdapter;
import com.misht.shakepaws.home.adapter.PetAdapter;
import com.misht.shakepaws.home.models.Pet;
import com.misht.shakepaws.utils.ClickListener;
import com.misht.shakepaws.utils.Constants;
import com.misht.shakepaws.utils.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements FPetAdapter.FPetAdapterListener {

    private RecyclerView recyclerView;
    private FPetAdapter adapter;
    private List<Pet> petList;

    private DatabaseReference mDatabase;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.pet_list_recyclerview);

        petList = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_SAVE);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Pet pet = snapshot.getValue(Pet.class);
                    petList.add(pet);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        petList = showSamplePets();
        adapter = new FPetAdapter(getContext(), petList, this);
        adapter.notifyDataSetChanged();

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Pet pet = petList.get(position);
                onPetSelected(pet);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    @Override
    public void onPetSelected(Pet pet) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("pet_object", pet);
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private List<Pet> showSamplePets() {
        List<Pet> petList = new ArrayList<>();

        int[] pics = new int[]{
                R.mipmap.a,
                R.mipmap.b,
                R.mipmap.c,
                R.mipmap.d,
                R.mipmap.e,
                R.mipmap.f
        };

        Pet a = new Pet(1,"Agility", "1", "+358413298321", pics[0]);
        petList.add(a);

        Pet b = new Pet(2,"True Romance", "0.3", "+358413298321", pics[1]);
        petList.add(b);

        Pet c = new Pet(3,"Pretty", "1.2", "+358413298321", pics[2]);
        petList.add(c);

        Pet d = new Pet(4,"Maroon", "0.4", "+358413298321", pics[3]);
        petList.add(d);

        Pet e = new Pet(5,"Honey", "0.9", "+358413298321", pics[4]);
        petList.add(e);

        Pet f = new Pet(6,"Cutie Cutie", "0.4", "413298321", pics[5]);
        petList.add(f);

        Pet g = new Pet(7,"Baby Juicy", "0.3", "+358413298321", pics[1]);
        petList.add(b=g);

        Pet h = new Pet(8,"Tomkin", "1.2", "+358413298321", pics[2]);
        petList.add(h);

        Pet i = new Pet(9,"Truce", "0.4", "+358413298321", pics[3]);
        petList.add(i);

        Pet j = new Pet(10,"Deedee", "0.9", "+358413298321", pics[4]);
        petList.add(j);

        Pet k = new Pet(11,"Baby", "0.4", "+358413298321", pics[5]);
        petList.add(k);

        return petList;
    }
}
