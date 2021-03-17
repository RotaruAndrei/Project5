package com.example.project5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import static com.example.project5.AllCategoriesDialog.ALL_CATEGORIES;
import static com.example.project5.AllCategoriesDialog.CALLING_ACTIVITY;
import static com.example.project5.AllCategoriesDialog.PUT_CATEGORY;

public class Search extends AppCompatActivity implements AllCategoriesDialog.GetCategory {
    private static final String TAG = "Search activity";
    private MaterialToolbar toolbar;
    private EditText searchBox;
    private ImageView searchIcon;
    private TextView cat1, cat2, cat3,allCat;
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private GroceryItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initViews();
        navigtionOptions();
        setSupportActionBar(toolbar);
        adapter = new GroceryItemAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        //receive the intent from dialog
        Intent intent = getIntent();
        if (intent != null){
            String category = intent.getStringExtra(PUT_CATEGORY);
            if (category != null){
                ArrayList<GroceryModel> items = Utils.getItemsByCat(this,category);
                if (items != null){
                    adapter.setItems(items);
                }
            }

        }

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSearch();
            }
        });

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                initSearch();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ArrayList<String> categories = Utils.getCategories(this);
        if (categories != null){
            if (categories.size() > 0){
                if (categories.size() == 1){
                    showCategories(categories,1);
                }else if (categories.size() == 2){
                    showCategories(categories,2);
                }else {
                    showCategories(categories,3);
                }
            }
        }

        allCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllCategoriesDialog dialog = new AllCategoriesDialog();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(ALL_CATEGORIES,categories);
                bundle.putString(CALLING_ACTIVITY,"search_activity");
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(),"all categories dialog");
            }
        });
    }

    //use this method in more than 1 place to increase our user points
    private void increaseUserPoints (ArrayList<GroceryModel> items){
        for (GroceryModel s: items){
            Utils.changeUserPoints(this,s,1);
        }
    }

    private void showCategories(ArrayList<String> categories, int i) {
        switch (i){
            case 1:
                cat1.setVisibility(View.VISIBLE);
                cat1.setText(categories.get(0));
                cat2.setVisibility(View.GONE);
                cat3.setVisibility(View.GONE);
                cat1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryModel> items = Utils.getItemsByCat(Search.this,categories.get(0));
                        if (items != null){
                            adapter.setItems(items);
                        }
                    }
                });
                break;
            case 2:
                cat1.setVisibility(View.VISIBLE);
                cat1.setText(categories.get(0));
                cat2.setVisibility(View.VISIBLE);
                cat2.setText(categories.get(1));
                cat3.setVisibility(View.GONE);
                cat1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryModel> items = Utils.getItemsByCat(Search.this,categories.get(0));
                        if (items != null){
                            adapter.setItems(items);
                        }
                    }
                });

                cat2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryModel> items = Utils.getItemsByCat(Search.this,categories.get(1));
                        if (items != null){
                            adapter.setItems(items);
                        }
                    }
                });
                break;
            case 3:
                cat1.setVisibility(View.VISIBLE);
                cat1.setText(categories.get(0));
                cat2.setVisibility(View.VISIBLE);
                cat2.setText(categories.get(1));
                cat3.setVisibility(View.VISIBLE);
                cat3.setText(categories.get(2));
                cat1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryModel> items = Utils.getItemsByCat(Search.this,categories.get(0));
                        if (items != null){
                            adapter.setItems(items);
                        }
                    }
                });

                cat2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryModel> items = Utils.getItemsByCat(Search.this,categories.get(1));
                        if (items != null){
                            adapter.setItems(items);
                        }
                    }
                });

                cat3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<GroceryModel> items = Utils.getItemsByCat(Search.this,categories.get(2));
                        if (items != null){
                            adapter.setItems(items);
                        }
                    }
                });
                break;
            default:
                cat1.setVisibility(View.GONE);
                cat2.setVisibility(View.GONE);
                cat3.setVisibility(View.GONE);
                break;
        }
    }

    private void initSearch(){
        if (!searchBox.getText().toString().equals(" ")){
            // TODO: 02/03/2021  get items
            String name = searchBox.getText().toString();
            ArrayList<GroceryModel> items = Utils.searchItems(this,name);
            if (items != null){
                adapter.setItems(items);
                increaseUserPoints(items);
            }

        }
    }

    private void navigtionOptions() {
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_search);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottom_nav_search:
                        break;
                    case R.id.bottom_nav_home:
                        Intent intent = new Intent(Search.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.bottom_nav_myCart:
                        Intent cartIntent = new Intent(Search.this,CartActivity.class);
                        cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cartIntent);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

    }

    private void initViews() {
        toolbar = findViewById(R.id.search_toolbar);
        searchBox = findViewById(R.id.search_searchBox);
        searchIcon = findViewById(R.id.serach_icon);
        cat1 = findViewById(R.id.serach_category1);
        cat2 = findViewById(R.id.serach_category2);
        cat3 = findViewById(R.id.serach_category3);
        allCat = findViewById(R.id.serach_seeAllCategories);
        bottomNavigationView = findViewById(R.id.search_bottomNav);
        recyclerView = findViewById(R.id.search_recycleView);
    }

    @Override
    public void getCategoryResult(String category) {
        ArrayList<GroceryModel>  items = Utils.getItemsByCat(this,category);
        if (items != null){
            adapter.setItems(items);
        }
    }
}