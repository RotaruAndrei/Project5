package com.example.project5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CartActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initViews();
        initBottomNav();
        setSupportActionBar(toolbar);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.cart_container,new CartFirstFragment(),"using first Fragment");
        fragmentTransaction.commit();

    }

    private void initBottomNav() {
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_myCart);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottom_nav_myCart:
                        break;

                    case R.id.bottom_nav_search:
                        Intent searchIntent = new Intent(CartActivity.this, Search.class);
                        searchIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(searchIntent);

                        break;

                    case R.id.bottom_nav_home:
                        Intent homeIntent = new Intent(CartActivity.this, MainActivity.class);
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(homeIntent);
                        break;

                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void initViews() {
        toolbar = findViewById(R.id.cart_toolbar);
        bottomNavigationView = findViewById(R.id.cart_bottomNav);
    }
}