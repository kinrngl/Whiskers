package com.example.whiskersapp.petwhiskers.ViewHolder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.whiskersapp.petwhiskers.R;

public class FindPetViewHolder extends RecyclerView.ViewHolder{

        public static TextView petBreed, petStatus, petPrice;
        String currency = "₱";
        public static CardView cardView;
        public FindPetViewHolder(View itemView) {
            super(itemView);
            petBreed = (TextView)itemView.findViewById(R.id.breedTV);
            petPrice = (TextView)itemView.findViewById(R.id.priceTV);
            petStatus = (TextView)itemView.findViewById(R.id.statusTV);
            cardView = (CardView)itemView.findViewById(R.id.findPetCV);
        }
        public void setPetBreed(String petBreedText){
            petBreed.setText(petBreedText);
        }
        public void setPetPrice(String petPriceText){
            if(petPriceText.equals("Free") || petPriceText.equals("free")){
                currency = "";
            }
            petPrice.setText(currency+petPriceText);
        }
        public void setPetStatus(String petStatusText){
            petStatus.setText(petStatusText);
        }


}
