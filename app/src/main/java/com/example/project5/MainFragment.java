package com.example.project5;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class MainFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private RecyclerView newItemsRecycler, popularItemsRecycler, suggestedItemsRecycler;
    private GroceryItemAdapter newItemsAdapter, popularItemsAdapter, suggestedItemsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        initStartPage(view);
        optionSelection();
//        Utils.clearSharedPreference(getActivity());
        return view;


    }

    @Override
    public void onResume() {
        super.onResume();
        initRecyclers();
    }

    private void initRecyclers() {
        newItemsAdapter = new GroceryItemAdapter(getActivity());
        newItemsRecycler.setAdapter(newItemsAdapter);
        newItemsRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));

        popularItemsAdapter = new GroceryItemAdapter(getActivity());
        popularItemsRecycler.setAdapter(popularItemsAdapter);
        popularItemsRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));

        suggestedItemsAdapter = new GroceryItemAdapter(getActivity());
        suggestedItemsRecycler.setAdapter(suggestedItemsAdapter);
        suggestedItemsRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));


        // create comparison between items to find which is newest, popular or suggested

        ArrayList<GroceryModel> newItemList = Utils.getAllItems(getActivity());
        if (newItemList != null){

            Comparator<GroceryModel> newItemsComparator = new Comparator<GroceryModel>() {
                @Override
                public int compare(GroceryModel o1, GroceryModel o2) {
                    return o1.getId() - o2.getId();
                }
            };

            Comparator<GroceryModel> reverseComparator = Collections.reverseOrder(newItemsComparator);
            Collections.sort(newItemList,reverseComparator);
            newItemsAdapter.setItems(newItemList);
        }


        ArrayList<GroceryModel> popularityItemList = Utils.getAllItems(getActivity());
        if (popularityItemList != null){
            Comparator<GroceryModel> popularityItemComparator = new Comparator<GroceryModel>() {
                @Override
                public int compare(GroceryModel o1, GroceryModel o2) {
                    return o1.getPopularityPoint() - o2.getPopularityPoint();
                }
            };

            Collections.sort(popularityItemList, Collections.reverseOrder(popularityItemComparator));
            popularItemsAdapter.setItems(popularityItemList);
        }


        ArrayList<GroceryModel> suggestedItemList = Utils.getAllItems(getActivity());
        if (suggestedItemList != null){
            Comparator<GroceryModel> suggestedItemComparator = new Comparator<GroceryModel>() {
                @Override
                public int compare(GroceryModel o1, GroceryModel o2) {
                    return o1.getUserPoint() - o2.getUserPoint();
                }
            };

            Collections.sort(suggestedItemList,Collections.reverseOrder(suggestedItemComparator));
            suggestedItemsAdapter.setItems(suggestedItemList);
        }
    }

    // method that compare items between them
    // for order showing purpose
    private int newItemComparator (GroceryModel first, GroceryModel second) {
        if (first.getId() > second.getId()){
            return 1;
        }else  if (first.getId() < second.getId()){
            return -1;
        }else {
            return 0;
        }
    }

    // method where views are initiated
    // and hard code the starting page
    private void initStartPage(View v){
        bottomNavigationView = v.findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_home);
        newItemsRecycler = v.findViewById(R.id.fragment_new_item_recycler_view);
        popularItemsRecycler = v.findViewById(R.id.fragment_popular_items_recycler_view);
        suggestedItemsRecycler = v.findViewById(R.id.fragment_suggested_items_recycler_view);
    }


    private void optionSelection (){
        // TODO: 16/02/2021 finish this

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottom_nav_search:
                        Intent intent = new Intent(getActivity(),Search.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.bottom_nav_home:
                        Toast.makeText(getActivity(), "Home selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.bottom_nav_myCart:
                        Intent cartIntent = new Intent(getActivity(),CartActivity.class);
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

}
