package com.misht.shakepaws.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.misht.shakepaws.R;
import com.misht.shakepaws.home.models.Pet;

import java.util.List;

/**
 * Created by Heikki Lintulahti on 5/5/2018.
 */

public class FPetAdapter extends RecyclerView.Adapter<FPetAdapter.PetViewHolder> {
    private List<Pet> petList;
    private Context context;
    private Pet pet;
    private FPetAdapter.FPetAdapterListener listener;

    public FPetAdapter(Context context, List<Pet> petList, FPetAdapter.FPetAdapterListener listener) {
        this.context = context;
        this.petList = petList;
        this.listener = listener;
    }

    @Override
    public FPetAdapter.PetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pet_item, parent, false);
        return new FPetAdapter.PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FPetAdapter.PetViewHolder holder, int position) {
        pet = petList.get(holder.getAdapterPosition());
        Glide.with(context).load(pet.getImageUrl()).into(holder.petImage);
        holder.petName.setText(pet.getName());
        holder.petAge.setText(pet.getAge() + " yrs");
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    class PetViewHolder extends RecyclerView.ViewHolder {

        TextView petName, petAge;
        ImageView petImage;

        PetViewHolder(View view) {
            super(view);
            petImage = view.findViewById(R.id.pet_image);
            petName = view.findViewById(R.id.pet_name);
            petAge = view.findViewById(R.id.pet_age);
        }
    }

    public interface FPetAdapterListener {
        void onPetSelected(Pet pet);
    }
}
