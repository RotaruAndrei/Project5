package com.example.project5;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CartSecondFragment extends Fragment {
    public static final String ORDER_KEY = "orderKey";
    private EditText address, postCode, phone, email;
    private Button btn_next, btn_back;
    private TextView warning;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart_second,container,false);
        initViews(view);

        // this bundle is from the third fragment
        Bundle bundle = getArguments();
        if (bundle != null){
            Gson gson = new Gson();
            Type type = new TypeToken<OrderModel>(){}.getType();
            String backItem = bundle.getString(ORDER_KEY);
            if (backItem != null){
                OrderModel order = gson.fromJson(backItem,type);
                if (order != null){
                    address.setText(order.getAddress());
                    postCode.setText(order.getPostCode());
                    phone.setText(order.getPhone());
                    email.setText(order.getEmail());
                }
            }
        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.cart_container,new CartFirstFragment());
                fragmentTransaction.commit();
            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNotEmpty()){
                    warning.setVisibility(View.GONE);
                    ArrayList<GroceryModel> cartItems = Utils.getCartItem(getActivity());
                    if (cartItems != null){
                        OrderModel orderModel = new OrderModel();
                        orderModel.setItems(cartItems);
                        orderModel.setAddress(address.getText().toString());
                        orderModel.setPostCode(postCode.getText().toString());
                        orderModel.setPhone(phone.getText().toString());
                        orderModel.setEmail(email.getText().toString());
                        orderModel.setTotalPrice(calculateTotal(cartItems));

                        Gson gson = new Gson();
                        String json = gson.toJson(orderModel);
                        Bundle bundle = new Bundle();
                        bundle.putString(ORDER_KEY,json);
                        CartThirdFragment cartThirdFragment = new CartThirdFragment();
                        cartThirdFragment.setArguments(bundle);
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.cart_container,cartThirdFragment);
                        fragmentTransaction.commit();

                    }

                }else {
                    warning.setVisibility(View.VISIBLE);
                    warning.setText(R.string.warning_text);
                }
            }
        });

        return view;
    }

    //method to check if any of the fields are empty or not
    private boolean isNotEmpty(){
        if (address.getText().toString().equals("") || postCode.getText().toString().equals("") || phone.getText().toString().equals("")
        || email.getText().toString().equals("")){
            return false;
        }
        return true;
    }

    private void initViews(View view) {
        address = view.findViewById(R.id.cart_fragment2_editAddress);
        postCode = view.findViewById(R.id.cart_fragment2_editPostCode);
        phone = view.findViewById(R.id.cart_fragment2_editPhone);
        email = view.findViewById(R.id.cart_fragment2_editEmail);
        btn_back = view.findViewById(R.id.cart_fragment2_btnBack);
        btn_next = view.findViewById(R.id.cart_fragment2_btnNext);
        warning = view.findViewById(R.id.cart_fragment2_warning);
    }

    // method to calculate the total summ of all the products in the cart
    private double calculateTotal(ArrayList<GroceryModel> items){
        double price = 0;
        for (GroceryModel s: items){
            price += s.getPrice();
        }

        price = Math.round(price*100.0)/100.0;

        return price;
    }


}
