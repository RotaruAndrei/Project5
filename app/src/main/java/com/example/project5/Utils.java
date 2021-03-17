package com.example.project5;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.EditTextPreference;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {

    private static final String TAG = "Utils";

    private static int ID = 0;
    private static int OrderID = 0;
    private static final String ALL_ITEMS_KEY1 = "all_items";
    private static final String DATABASE_FAKE = "fake_database";
    public static final String CART_ITEM_KEY = "cart_item";
    private static Gson gson = new Gson();
    private static Type token = new TypeToken<ArrayList<GroceryModel>>(){}.getType();


    // method where i will initiate my data source
    public static void initSharedPrefer(Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_FAKE,Context.MODE_PRIVATE);
        ArrayList<GroceryModel> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY1,null),token);

        if (allItems == null){
            initAllItems(context);
        }
    }

    public static int getOrderID() {
        OrderID++;
        return OrderID;
    }

    // method to clear the shared preference instead of uninstalling the app
    public static void clearSharedPreference (Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_FAKE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    //method where i will initiate all of my items
    // then add them to my sharedpreferences list
    private static void initAllItems(Context context) {

        ArrayList<GroceryModel> allItems = new ArrayList<>();

        GroceryModel milk = new GroceryModel("Milk","Fresh milk from the country side","https://igashop.com.au/wp-content/uploads/2020/05/liddells-full-cream-long-life-milk-lactose-free-1l.jpg",
                "Drink",1,20);
        allItems.add(milk);

        GroceryModel iceCream = new GroceryModel("Ice cream","Best icecream that can be found in whole wonderland","https://i.pinimg.com/originals/ce/35/a3/ce35a3d4a8cb86cdd49a0deafd74b750.jpg",
                "Desert",2.1,10);
        allItems.add(iceCream);

        GroceryModel soda = new GroceryModel("Soda","Dont drink to much soda, even if is good as this one","https://www.myamericanmarket.com/873/coca-cola-classic.jpg",
                "Drink",0.99,20);
        allItems.add(soda);

        GroceryModel peanut = new GroceryModel("Peanut","high value of protein, recomended to use at least 2 days a week","https://i.pinimg.com/originals/44/a3/6f/44a36ff7e3e2558f43141ffe8c77b775.jpg",
                "Snack",1.5,10);
        allItems.add(peanut);

        GroceryModel bread = new GroceryModel("Bread","fresh bread every day, with love created by our bakers from Britiania","https://www.kitchensanctuary.com/wp-content/uploads/2020/06/Artisan-Bread-square-FS-46.jpg",
                "Food",0.5,20);
        allItems.add(bread);


        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_FAKE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ALL_ITEMS_KEY1,gson.toJson(allItems));
        editor.commit();

    }

    // getter for all items
    public static ArrayList<GroceryModel> getAllItems(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_FAKE,Context.MODE_PRIVATE);
        ArrayList<GroceryModel> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY1, null),token);
        return allItems;
    }

    //getter that auto generate an id for the item
    public static int getID() {
        ID ++;
        return ID;
    }

    // method to change the rate of a grocery item
    // this method will take alot of time in real world so thats why shared preferance is not good for structured date
    // but for this project that is not that big will do fine
    public static void getRate (Context context, int itemId, int newRate){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_FAKE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<GroceryModel> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY1,null),token);
        if (allItems != null){
            ArrayList<GroceryModel> newItems = new ArrayList<>();
            for (GroceryModel s: allItems){
                if (s.getId() == itemId){
                    s.setRate(newRate);
                    newItems.add(s);
                }else {
                    newItems.add(s);
                }
            }

            editor.remove(ALL_ITEMS_KEY1);
            editor.putString(ALL_ITEMS_KEY1,gson.toJson(newItems));
            editor.commit();
        }

    }

    // adding review to the grocery item
    public static void addReview (Context context, Review review) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_FAKE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<GroceryModel> allItems = getAllItems(context);

        if (allItems != null){
            ArrayList<GroceryModel> newItems =  new ArrayList<>();

            for (GroceryModel s: allItems){
                if (s.getId() == review.getId()){
                    ArrayList<Review> reviews = s.getReviews();
                    reviews.add(review);
                    s.setReviews(reviews);
                    newItems.add(s);
                 }else {
                    newItems.add(s);
                }
            }

            editor.remove(ALL_ITEMS_KEY1);
            editor.putString(ALL_ITEMS_KEY1,gson.toJson(newItems));
            editor.commit();
        }

    }

    // getter for the reviews
    public static ArrayList<Review> getReviewsById (Context context, int itemId) {
        ArrayList<GroceryModel> allItems= getAllItems(context);

        if (allItems != null){
            for (GroceryModel s: allItems){
                if (s.getId() == itemId){
                    ArrayList<Review> reviewsss = s.getReviews();
                    return reviewsss;
                }
            }
        }
        return null;
    }

    //method to add items to cart
    public static void addItemToCarT(Context context, GroceryModel item){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_FAKE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<GroceryModel> cartItem = gson.fromJson(sharedPreferences.getString(CART_ITEM_KEY,null),token);

        if (cartItem == null){
            cartItem = new ArrayList<>();
        }
        cartItem.add(item);
        editor.remove(CART_ITEM_KEY);
        editor.putString(CART_ITEM_KEY,gson.toJson(cartItem));
        editor.commit();
    }

    // getter for the cartItem
    public static ArrayList<GroceryModel> getCartItem (Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_FAKE,Context.MODE_PRIVATE);
        ArrayList<GroceryModel> cartItem = gson.fromJson(sharedPreferences.getString(CART_ITEM_KEY,null),token);
        if (cartItem != null){
            return cartItem;
        }
        return null;
    }

    //method to search in our shared preferences
    // but seach for 2 part the whole name of the item or
    // item name split
    // and avoid duplication
    public static ArrayList<GroceryModel> searchItems (Context context, String itemName){
        ArrayList<GroceryModel> allItems = getAllItems(context);
        if (allItems != null){
            ArrayList<GroceryModel> items = new ArrayList<>();
            for (GroceryModel s: allItems){
                if (s.getName().equalsIgnoreCase(itemName)){
                    items.add(s);
                }

                String[] itemSplit = s.getName().split(" ");
                for (int i = 0 ; i < itemSplit.length; i++){
                    if (itemSplit[i].equalsIgnoreCase(itemName)){
                        if (!items.contains(s)){
                            items.add(s);
                        }
                    }
                }
            }

            return items;
        }
        return null;
    }

    //method where i can gather all the items categories
    public static ArrayList<String> getCategories(Context context){
        ArrayList<GroceryModel> allItems = getAllItems(context);
        if (allItems != null){
            ArrayList<String> categories = new ArrayList<>();
            for (GroceryModel s: allItems){
                if (!categories.contains(s.getCategory())){
                    categories.add(s.getCategory());
                }
            }
            return categories;
        }
        return null;
    }


    //method to get all the items by categories
    public static ArrayList<GroceryModel> getItemsByCat (Context context, String categories){
        ArrayList<GroceryModel> allItems = getAllItems(context);
        if (allItems != null){
            ArrayList<GroceryModel> categoryItems = new ArrayList<>();
            for (GroceryModel s: allItems){
                if (s.getCategory().equalsIgnoreCase(categories)){
                        categoryItems.add(s);
                }
            }
            return categoryItems;
        }
        return null;
    }


    //method to delete items from the cart
    public static void deleteCartItems (Context context,GroceryModel item){
        ArrayList<GroceryModel> cartItems = getCartItem(context);
        if (cartItems != null){
            ArrayList<GroceryModel> newItems = new ArrayList<>();
            for (GroceryModel s: cartItems){
                if (s.getId() != item.getId()){
                    newItems.add(s);
                }
            }

            SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_FAKE,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(CART_ITEM_KEY);
            editor.putString(CART_ITEM_KEY,gson.toJson(newItems));
            editor.commit();
        }

    }


    //method to clear my cart items
    public static void clearCartItems (Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_FAKE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(CART_ITEM_KEY);
        editor.commit();
    }

    //method to increase popularity points of items bought by the user
    public static void increasePopularityPoints (Context context, GroceryModel item, int i){

        ArrayList<GroceryModel> allItems = getAllItems(context);
        if (allItems != null){
            ArrayList<GroceryModel> newItems = new ArrayList<>();
            for (GroceryModel s: allItems){
                if (s.getId() == item.getId()){
                    s.setPopularityPoint(s.getPopularityPoint() + i);
                    newItems.add(s);
                }else {
                    newItems.add(s);
                }
            }

            SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_FAKE,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(ALL_ITEMS_KEY1);
            Gson gson = new Gson();
            editor.putString(ALL_ITEMS_KEY1,gson.toJson(newItems));
            editor.commit();
        }


    }


    //with this method i will change every item userPoints based on:
    //if the user check/visit one of the items - 1 point
    //if the user search one of the items - 1 point
    // if the user rate the item - is rate * 2 points
    //if the user review the item - 3 points
    // if the user buy the item - 4 points
    // if the user read the item - 1 point each minute the user spends on the item

    public static void changeUserPoints (Context context, GroceryModel item, int pointNumber){
        Log.d(TAG, "changeUserPoints: " + "adding " + pointNumber + " to " + item.getName());
        ArrayList<GroceryModel> allItems = getAllItems(context);

        if (allItems != null){
            ArrayList<GroceryModel> newItems = new ArrayList<>();
            for (GroceryModel s: allItems){

                if (s.getId() == item.getId()){
                    s.setUserPoint(s.getUserPoint() + pointNumber);
                }

                newItems.add(s);
            }
            SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_FAKE,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(ALL_ITEMS_KEY1);
            editor.putString(ALL_ITEMS_KEY1,gson.toJson(newItems));
            editor.commit();

        }
    }

    public static String getLicences (){
        String licences = "";

        //Gson
        licences += "Gson\n" +
                    "Copyright 2008 Google Inc.\n" +
                    "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                    "you may not use this file except in compliance with the License.\n" +
                    "You may obtain a copy of the License at \n\n" +
                    "http://www.apache.org/licenses/LICENSE-2.0 \n\n" +
                    "Unless required by applicable law or agreed to in writing, software\n" +
                    "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                    "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                    "See the License for the specific language governing permissions and\n" +
                    "limitations under the License.\n\n";

        licences += "Glide\n" +
                    "The MIT License (MIT)\n\n" +
                    "Copyright (c) 2015 Jonathan Reinink <jonathan@reinink.ca>\n\n" +
                    "Permission is hereby granted, free of charge, to any person obtaining a copy\n" +
                    "of this software and associated documentation files (the \"Software\"), to deal\n" +
                    "in the Software without restriction, including without limitation the rights\n" +
                    "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" +
                    "copies of the Software, and to permit persons to whom the Software is\n" +
                    "furnished to do so, subject to the following conditions:\n\n" +
                    "The above copyright notice and this permission notice shall be included in\n" +
                    "all copies or substantial portions of the Software.\n\n" +
                    "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" +
                    "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" +
                    "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" +
                    "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" +
                    "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" +
                    "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN\n" +
                    "THE SOFTWARE.";

        licences += "Retrofit\n" +
                    "Copyright 2013 Square, Inc.\n\n" +
                    "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                    "you may not use this file except in compliance with the License.\n" +
                    "You may obtain a copy of the License at:\n\n" +
                    "http://www.apache.org/licenses/LICENSE-2.0\n\n" +
                    "Unless required by applicable law or agreed to in writing, software\n" +
                    "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                    "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                    "See the License for the specific language governing permissions and\n" +
                    "limitations under the License.";
        return licences;
    }
}
