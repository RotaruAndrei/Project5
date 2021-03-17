package com.example.project5;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import static com.example.project5.GroceryItemActivity.GROCERY_ITEM_KEY;


public class GroceryItemAdapter extends RecyclerView.Adapter<GroceryItemAdapter.ViewHolder> {

    private ArrayList<GroceryModel> items = new ArrayList<>();
    private Context context;

    public GroceryItemAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.card_name.setText(items.get(position).getName());
        holder.card_price.setText((String.valueOf(items.get(position).getPrice())) + " $");

        Glide.with(context)
                .asBitmap()
                .load(items.get(position).getImageURL())
                .into(holder.card_img);


        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,GroceryItemActivity.class);
                intent.putExtra(GROCERY_ITEM_KEY, items.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<GroceryModel> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private MaterialCardView card;
        private ImageView card_img;
        private TextView card_price, card_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.parentCard);
            card_img = itemView.findViewById(R.id.card_img);
            card_price = itemView.findViewById(R.id.card_price);
            card_name = itemView.findViewById(R.id.card_name);
        }
    }
}
