package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class GroceryItemActivity extends AppCompatActivity implements CustomDialog.AddReviewInterface {
    private static final String TAG = "GroceryItemActivity";

    private boolean isBound;
    private TrackUserTime myService;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TrackUserTime.LocalBinder binder = (TrackUserTime.LocalBinder) service;
            myService = binder.getService();
            isBound = true;
            myService.setItem(incomingItem);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };


    public static final String GROCERY_ITEM_KEY = "groceryItemKey";

    @Override
    public void addNewReview(Review review) {
        Log.d(TAG, "addNewReview: adding review" + review);
        Utils.addReview(this,review);
        Utils.changeUserPoints(this,incomingItem,3);
        ArrayList<Review> reviews = Utils.getReviewsById(this,review.getId());
        if (reviews != null){
            adapter.setReviewList(reviews);
        }

    }

    private RecyclerView reviewRecycler;
    private TextView grocery_name, grocery_price, grocery_description, grocery_addReview;
    private Button grocery_btn;
    private RelativeLayout firstStarLayout, secondStarLayout, thirdStarLayout;
    private ImageView grocery_image, first_filledStar, first_emptyStar, second_filledStar, second_emptyStar, third_filledStar, third_emptyStar;
    private MaterialToolbar toolbar;

    private GroceryModel incomingItem;
    private ReviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_item);

        initViews();
        setSupportActionBar(toolbar);
        adapter = new ReviewAdapter();


        Intent intent = getIntent();
        if (intent != null){
            incomingItem = intent.getParcelableExtra(GROCERY_ITEM_KEY);
            if (incomingItem != null){
                Utils.increasePopularityPoints(this,incomingItem,1);
                grocery_name.setText(incomingItem.getName());
                grocery_price.setText(String.valueOf(incomingItem.getPrice()));
                grocery_description.setText(incomingItem.getDescription());

                Glide.with(this)
                        .asBitmap()
                        .load(incomingItem.getImageURL())
                        .into(grocery_image);


                ArrayList<Review> reviews = Utils.getReviewsById(this,incomingItem.getId());
                reviewRecycler.setAdapter(adapter);
                reviewRecycler.setLayoutManager(new LinearLayoutManager(this));

                if (reviews != null){
                    if (reviews.size() > 0){
                        adapter.setReviewList(reviews);
                    }
                }


                grocery_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.addItemToCarT(GroceryItemActivity.this,incomingItem);
                        Intent cartIntent = new Intent(GroceryItemActivity.this,CartActivity.class);
                        cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cartIntent);
                    }
                });

                grocery_addReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       CustomDialog dialog = new CustomDialog();
                       Bundle bundle = new Bundle();
                       bundle.putParcelable(GROCERY_ITEM_KEY,incomingItem);
                       dialog.setArguments(bundle);
                       dialog.show(getSupportFragmentManager(),"add review");
                    }
                });
                
                handleTheRating();

            }
        }

    }

    // method to handle tha star rating
    private void handleTheRating() {
        switch (incomingItem.getRate()){

            case 0:
                first_emptyStar.setVisibility(View.VISIBLE);
                first_filledStar.setVisibility(View.GONE);
                second_emptyStar.setVisibility(View.VISIBLE);
                second_filledStar.setVisibility(View.GONE);
                third_emptyStar.setVisibility(View.VISIBLE);
                third_filledStar.setVisibility(View.GONE);
                break;

            case 1:
                first_emptyStar.setVisibility(View.GONE);
                first_filledStar.setVisibility(View.VISIBLE);
                second_emptyStar.setVisibility(View.VISIBLE);
                second_filledStar.setVisibility(View.GONE);
                third_emptyStar.setVisibility(View.VISIBLE);
                third_filledStar.setVisibility(View.GONE);
                break;

            case 2:
                first_emptyStar.setVisibility(View.GONE);
                first_filledStar.setVisibility(View.VISIBLE);
                second_emptyStar.setVisibility(View.GONE);
                second_filledStar.setVisibility(View.VISIBLE);
                third_emptyStar.setVisibility(View.VISIBLE);
                third_filledStar.setVisibility(View.GONE);
                break;

            case 3:
                first_emptyStar.setVisibility(View.GONE);
                first_filledStar.setVisibility(View.VISIBLE);
                second_emptyStar.setVisibility(View.GONE);
                second_filledStar.setVisibility(View.VISIBLE);
                third_emptyStar.setVisibility(View.GONE);
                third_filledStar.setVisibility(View.VISIBLE);
                break;

            default:
                break;
        }


        firstStarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incomingItem.getRate() != 1){
                    Utils.getRate(GroceryItemActivity.this,incomingItem.getId(),1);

                    // here is where we change the user points of an item but that depends of the rate
                    //if is item rate was 2 before and now the user decide to make 1
                    // we need to downgrade the userpoints as well
                    Utils.changeUserPoints(GroceryItemActivity.this,incomingItem,(1 - incomingItem.getUserPoint()) *2);

                    // because we use shared preferences the incoming item will be the same
                    // so we have to update it here
                    incomingItem.setRate(1);
                    handleTheRating();
                }
            }
        });

        secondStarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incomingItem.getRate() != 2){
                    Utils.getRate(GroceryItemActivity.this,incomingItem.getId(),2);

                    Utils.changeUserPoints(GroceryItemActivity.this,incomingItem,(2 - incomingItem.getUserPoint()) *2);
                    incomingItem.setRate(2);
                    handleTheRating();
                }
            }
        });

        thirdStarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incomingItem.getRate() != 3){
                    Utils.getRate(GroceryItemActivity.this,incomingItem.getId(),3);

                    Utils.changeUserPoints(GroceryItemActivity.this,incomingItem,(3 - incomingItem.getUserPoint()) *2);
                    incomingItem.setRate(3);
                    handleTheRating();
                }
            }
        });
    }

    private void initViews(){
        reviewRecycler = findViewById(R.id.reviews_recyclerView);
        grocery_name = findViewById(R.id.grocery_item_firstRLayout_name);
        grocery_price = findViewById(R.id.grocery_item_firstRLayoutPrice);
        grocery_description = findViewById(R.id.grocery_item_description);
        grocery_addReview = findViewById(R.id.grocery_item_add_review);
        grocery_btn = findViewById(R.id.grocery_item_button);
        firstStarLayout = findViewById(R.id.firstStarRelativeLayout);
        first_filledStar = findViewById(R.id.first_filled_star);
        first_emptyStar = findViewById(R.id.first_empty_star);
        secondStarLayout = findViewById(R.id.secondStarRelativeLayout);
        second_filledStar = findViewById(R.id.second_filled_star);
        second_emptyStar = findViewById(R.id.second_empty_star);
        thirdStarLayout = findViewById(R.id.thirdStarRelativeLayout);
        third_filledStar = findViewById(R.id.third_filled_star);
        third_emptyStar = findViewById(R.id.third_empty_star);
        grocery_image = findViewById(R.id.grocery_item_image);
        toolbar = findViewById(R.id.grocery_item_toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent  = new Intent(this,TrackUserTime.class);
        bindService(intent,connection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound){
            unbindService(connection);
        }
    }
}