package com.example.project5;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.project5.CartSecondFragment.ORDER_KEY;

public class PaymentFragment extends Fragment {
    private ImageView img;
    private Button button;
    private TextView confirmation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_payment,container,false);

        initViews(view);

        Bundle bundle = getArguments();
        if (bundle != null){
            String incomingOrder = bundle.getString(ORDER_KEY);
            if (incomingOrder != null){
                Gson gson = new Gson();
                Type type = new TypeToken<OrderModel>(){}.getType();
                OrderModel orderModel = gson.fromJson(incomingOrder,type);
                if (orderModel != null){
                    if (orderModel.isSuccess()){
                        confirmation.setText("Payment was successful\n The order will arrive in 2 days");
                        // TODO: 12/03/2021 delete the cart items in the sharepreferences u dont need it after the transaction was successful
                        Utils.clearCartItems(getActivity());
                        // TODO: 12/03/2021 increase popularity points for the items that were bought by the client
                        for (GroceryModel item: orderModel.getItems()){
                            Utils.increasePopularityPoints(getActivity(),item,1);
                            Utils.changeUserPoints(getActivity(),item,4);
                        }
                        // TODO: 12/03/2021 increase user point
                    }else {
                        confirmation.setText("Something went wrong\n Please try again");
                    }
                }
            }else {
                confirmation.setText("Something went wrong\n Please try again");
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        return view;


    }

    private void initViews(View view) {

        button = view.findViewById(R.id.payment_btn);
        confirmation = view.findViewById(R.id.payment_confirmation);
    }


}
