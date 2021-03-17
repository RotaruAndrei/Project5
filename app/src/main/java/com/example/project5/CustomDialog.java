package com.example.project5;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.project5.GroceryItemActivity.GROCERY_ITEM_KEY;

public class CustomDialog extends DialogFragment {

    //create a call back interface
    // that will send the item to the parent activity
    // and from there we save it in our shared preferences
    // dont forget to use try catch block for class casting
    public interface AddReviewInterface {
        void addNewReview(Review review);
    }

    private AddReviewInterface addingReview;
    private TextView itemName, warning;
    private EditText userName, reviewBody;
    private Button btn_addReview;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog,null);
        initViews(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);


        Bundle bundle = getArguments();
        if (bundle != null){
            GroceryModel item = bundle.getParcelable(GROCERY_ITEM_KEY);
            if (item != null){
                itemName.setText(item.getName());
                btn_addReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String user = userName.getText().toString();
                        String review = reviewBody.getText().toString();
                        String date = getDate();

                        if (user.equals("") || review.equals("")){
                            warning.setText(R.string.warning_text);
                            warning.setVisibility(View.VISIBLE);
                        }else {
                            warning.setVisibility(View.GONE);
                            try {
                                addingReview = (AddReviewInterface) getActivity();
                                addingReview.addNewReview(new Review(item.getId(),user,review,date));
                                dismiss();

                            }catch (ClassCastException e){
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }

        return builder.create();

    }

    // method to get the current date
    // simple as possible
    private String getDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        return format.format(calendar.getTime());
    }

    private void initViews(View view) {

        itemName = view.findViewById(R.id.dialog_itemName);
        warning = view.findViewById(R.id.dialog_warning);
        userName = view.findViewById(R.id.dialog_userName);
        reviewBody = view.findViewById(R.id.dialog_reviewBody);
        btn_addReview = view.findViewById(R.id.dialog_btn);
    }
}
