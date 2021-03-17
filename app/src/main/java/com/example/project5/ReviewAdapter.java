package com.example.project5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{
    private static final String TAG = "ReviewAdapter";
    ArrayList<Review> reviewList = new ArrayList<>();

    public ReviewAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.userName.setText(reviewList.get(position).getUserName());
        holder.reviewContent.setText(reviewList.get(position).getText());
        holder.date.setText(reviewList.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public void setReviewList(ArrayList<Review> reviewList) {
        this.reviewList = reviewList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView userName, reviewContent, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.review_userName);
            reviewContent = itemView.findViewById(R.id.review_body);
            date = itemView.findViewById(R.id.review_date);
        }
    }
}
