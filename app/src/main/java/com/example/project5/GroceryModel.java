package com.example.project5;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class GroceryModel implements Parcelable {

    private int id;
    private String name;
    private String description;
    private String imageURL;
    private String category;
    private double price;
    private int availableAmount;
    private int rate;
    private int userPoint;
    private int popularityPoint;
    private ArrayList<Review> reviews;


    public GroceryModel(String name, String description, String imageURL, String category, double price, int availableAmount) {
        this.id = Utils.getID();
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.category = category;
        this.price = price;
        this.availableAmount = availableAmount;
        this.rate = 0;
        this.userPoint = 0;
        this.popularityPoint = 0;
        reviews = new ArrayList<>();
    }

    protected GroceryModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        imageURL = in.readString();
        category = in.readString();
        price = in.readDouble();
        availableAmount = in.readInt();
        rate = in.readInt();
        userPoint = in.readInt();
        popularityPoint = in.readInt();
    }

    public static final Creator<GroceryModel> CREATOR = new Creator<GroceryModel>() {
        @Override
        public GroceryModel createFromParcel(Parcel in) {
            return new GroceryModel(in);
        }

        @Override
        public GroceryModel[] newArray(int size) {
            return new GroceryModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(int availableAmount) {
        this.availableAmount = availableAmount;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getUserPoint() {
        return userPoint;
    }

    public void setUserPoint(int userPoint) {
        this.userPoint = userPoint;
    }

    public int getPopularityPoint() {
        return popularityPoint;
    }

    public void setPopularityPoint(int popularityPoint) {
        this.popularityPoint = popularityPoint;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "GroceryModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", availableAmount=" + availableAmount +
                ", rate=" + rate +
                ", userPoint=" + userPoint +
                ", popularityPoint=" + popularityPoint +
                ", reviews=" + reviews +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(imageURL);
        dest.writeString(category);
        dest.writeDouble(price);
        dest.writeInt(availableAmount);
        dest.writeInt(rate);
        dest.writeInt(userPoint);
        dest.writeInt(popularityPoint);
    }
}
