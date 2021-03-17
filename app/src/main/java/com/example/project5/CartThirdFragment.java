package com.example.project5;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.project5.CartSecondFragment.ORDER_KEY;

public class CartThirdFragment extends Fragment {
    private static final String TAG = "CartThirdFragment";

    private TextView itemsName, totalPrice, orderAddress, orderPhone;
    private Button btn_back, btn_checkout;
    private RadioGroup rbButtons;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart_third,container,false);

        initViews(view);


        Bundle bundle = getArguments();
        if (bundle != null){
            Gson gson = new Gson();
            Type type = new TypeToken<OrderModel>(){}.getType();
            String incomingOrder = bundle.getString(ORDER_KEY);
            if (incomingOrder != null){
                OrderModel order = gson.fromJson(incomingOrder,type);
                if (order != null){
                    String items = "";
                    for (GroceryModel s: order.getItems()){
                        items += "\n \t" + s.getName();
                    }

                    itemsName.setText(items);
                    totalPrice.setText(String.valueOf(order.getTotalPrice()));
                    orderAddress.setText(order.getAddress());
                    orderPhone.setText(order.getPhone());



                    btn_back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle backBundle = new Bundle();
                            backBundle.putString(ORDER_KEY,incomingOrder);
                            CartSecondFragment cartSecondFragment = new CartSecondFragment();
                            cartSecondFragment.setArguments(backBundle);
                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.cart_container,cartSecondFragment);
                            fragmentTransaction.commit();
                        }
                    });

                    btn_checkout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (rbButtons.getCheckedRadioButtonId()){
                                case R.id.cart_fragment3_rbPayPal:
                                    order.setPaymentMethod("Paypal");
                                    break;
                                case R.id.cart_fragment3_rbCreditCard:
                                    order.setPaymentMethod("Credit Card");
                                    break;
                                default:
                                    order.setPaymentMethod("Unknown");
                                    break;
                            }

                            order.setSuccess(true);

                            // TODO: 11/03/2021 send the request with retrofit

                            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                                    .setLevel(HttpLoggingInterceptor.Level.BODY);

                            OkHttpClient client = new OkHttpClient.Builder()
                                    .addInterceptor(interceptor)
                                    .build();

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("https://jsonplaceholder.typicode.com/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .client(client)
                                    .build();


                            OrderEndPoint endPoint = retrofit.create(OrderEndPoint.class);
                            Call<OrderModel> sendOrder = endPoint.newOrder(order);
                            sendOrder.enqueue(new Callback<OrderModel>() {
                                @Override
                                public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                                    Log.d(TAG, "onResponse: code: " + response.code() );
                                    if (response.isSuccessful()){
                                        // TODO: 11/03/2021 navigate the user to payment result
                                        Bundle sendOrder = new Bundle();
                                        sendOrder.putString(ORDER_KEY,gson.toJson(response.body()));
                                        PaymentFragment paymentFragment = new PaymentFragment();
                                        paymentFragment.setArguments(sendOrder);
                                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.cart_container,paymentFragment);
                                        fragmentTransaction.commit();

                                    }else {
                                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.cart_container,new PaymentFragment());
                                        fragmentTransaction.commit();
                                    }
                                }

                                @Override
                                public void onFailure(Call<OrderModel> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                        }
                    });
                }
            }
        }




        return view;
    }

    private void initViews(View view) {
        itemsName = view.findViewById(R.id.cart_fragment3_itemNames);
        totalPrice = view.findViewById(R.id.cart_fragment3_price);
        orderAddress = view.findViewById(R.id.cart_fragment3_address);
        orderPhone = view.findViewById(R.id.cart_fragment3_phone);
        btn_back = view.findViewById(R.id.cart_fragment3_btnBack);
        btn_checkout = view.findViewById(R.id.cart_fragment3_tbnCheckout);
        rbButtons = view.findViewById(R.id.cart_fragment3_rgPaymentMethod);
    }
}
