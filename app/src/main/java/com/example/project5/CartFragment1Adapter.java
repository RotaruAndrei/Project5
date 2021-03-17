package com.example.project5;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class CartFragment1Adapter extends RecyclerView.Adapter<CartFragment1Adapter.ViewHolder>{

    public interface DeleteItem{
        void onResultDelete(GroceryModel item);
    }

    public interface TotalSumCalc{
        void totalSum(double price);
    }


    private TotalSumCalc totalSumCalc;
    private DeleteItem deleteItem;
    private ArrayList<GroceryModel> items = new ArrayList<>();
    private Context context;
    private Fragment fragment;

    public CartFragment1Adapter(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.itemName.setText(items.get(position).getName());
        holder.price.setText(String.valueOf(items.get(position).getPrice()));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Deleting..")
                        .setMessage("Are you sure you want to delete this ?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                
                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            deleteItem = (DeleteItem) fragment;
                            deleteItem.onResultDelete(items.get(position));
                        }catch (ClassCastException e){
                            e.printStackTrace();
                        }
                    }
                });
                builder.create().show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<GroceryModel> items) {
        this.items = items;
        calculateTotal();
        notifyDataSetChanged();
    }

    private void calculateTotal(){
        double price = 0;
        for (GroceryModel s: items){
            price += s.getPrice();
        }

        price = Math.round(price*100.0)/100.0;

        try {
            totalSumCalc = (TotalSumCalc) fragment;
            totalSumCalc.totalSum(price);
        }catch (ClassCastException e){
            e.printStackTrace();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView itemName, delete, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.cart_card_itemName);
            delete = itemView.findViewById(R.id.cart_card_delete);
            price = itemView.findViewById(R.id.cart_card_price);
        }
    }
}
