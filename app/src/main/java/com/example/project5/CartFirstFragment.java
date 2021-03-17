package com.example.project5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartFirstFragment extends Fragment implements CartFragment1Adapter.DeleteItem, CartFragment1Adapter.TotalSumCalc {


    @Override
    public void onResultDelete(GroceryModel item) {
        Utils.deleteCartItems(getActivity(),item);
        ArrayList<GroceryModel> items = Utils.getCartItem(getActivity());
        if (items != null){
            if (!(items.size() > 0)){
                noItemsLayout.setVisibility(View.GONE);
                noItems.setVisibility(View.VISIBLE);
            }else {
                noItemsLayout.setVisibility(View.VISIBLE);
                noItems.setVisibility(View.GONE);
                adapter.setItems(items);
            }

        }else {

            noItemsLayout.setVisibility(View.GONE);
            noItems.setVisibility(View.VISIBLE);
        }
    }

    private RecyclerView recyclerView;
    private TextView noItems, cartSum;
    private Button button;
    private RelativeLayout noItemsLayout;
    private CartFragment1Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart_first,container,false);
        initViews(view);
        adapter = new CartFragment1Adapter(getActivity(),this);
        ArrayList<GroceryModel> items = Utils.getCartItem(getActivity());
        if (items != null){
            if (!(items.size() > 0)){
                noItemsLayout.setVisibility(View.GONE);
                noItems.setVisibility(View.VISIBLE);
            }else {
                adapter.setItems(items);
                noItemsLayout.setVisibility(View.VISIBLE);
                noItems.setVisibility(View.GONE);
            }

        }else {

            noItemsLayout.setVisibility(View.GONE);
            noItems.setVisibility(View.VISIBLE);
        }

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.cart_container, new CartSecondFragment());
                fragmentTransaction.commit();
            }
        });



        return view;
    }

    private void initViews(View view) {

        recyclerView = view.findViewById(R.id.fragment1_recyclerView);
        noItems = view.findViewById(R.id.fragment1_noItemsText);
        cartSum = view.findViewById(R.id.fragment1_sum);
        button = view.findViewById(R.id.fragment1_btn);
        noItemsLayout = view.findViewById(R.id.fragment1_relativeLayout);
    }


    @Override
    public void totalSum(double price) {
        cartSum.setText(String.valueOf(price));
    }
}
