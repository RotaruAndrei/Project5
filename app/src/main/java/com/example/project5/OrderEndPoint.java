package com.example.project5;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrderEndPoint {

    @POST("posts")
   Call<OrderModel> newOrder (@Body OrderModel orderModel);

}
