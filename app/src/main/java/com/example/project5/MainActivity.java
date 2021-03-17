package com.example.project5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import static com.example.project5.AllCategoriesDialog.ALL_CATEGORIES;
import static com.example.project5.AllCategoriesDialog.CALLING_ACTIVITY;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private MaterialToolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        // because i disable the them action bar
        // have to add the one that i have created

        //initiate data source
        Utils.initSharedPrefer(this);

        // set up the tool bar because the normal one is disable
        setSupportActionBar(toolbar);

        //create a toggle for drawer menu
        //add the listener to our drawer layout
        // then sync it
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        //create fragment transaction
        //and replace our container with the new fragment
        // than commit
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container,new MainFragment());
        fragmentTransaction.commit();


        // drawer menu listener
        // create a toast for the beigning
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_cart:
                        Intent cartIntent = new Intent(MainActivity.this,CartActivity.class);
                        cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cartIntent);
                        break;
                    case R.id.menu_categories:
                        AllCategoriesDialog dialog = new AllCategoriesDialog();
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList(ALL_CATEGORIES,Utils.getCategories(MainActivity.this));
                        bundle.putString(CALLING_ACTIVITY,"main_activity");
                        dialog.setArguments(bundle);
                        dialog.show(getSupportFragmentManager(),"all categories dialog");
                        break;
                    case R.id.menu_aboutUs:
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("About Us")
                                .setMessage("Design and Developed by Andrei Rotaru")
                                .create().show();
                        break;
                    case R.id.menu_terms:
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Terms")
                                .setMessage("No Terms available at the moment enjoy this content")
                                .setPositiveButton("Dismiss", null).create().show();
                        break;
                    case R.id.menu_licences:
                        LicencesDialog licencesDialog = new LicencesDialog();
                        licencesDialog.show(getSupportFragmentManager(),"licences Dialog");
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }


// this method is to initiate UI elements
    private void initViews(){
        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation_view);
    }
}