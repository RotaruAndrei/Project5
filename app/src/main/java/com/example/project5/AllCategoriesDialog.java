package com.example.project5;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class AllCategoriesDialog extends DialogFragment {

    public interface GetCategory{
        void getCategoryResult(String category);
    }

    private GetCategory getCategory;
    public static final String ALL_CATEGORIES = "categories";
    public static final String CALLING_ACTIVITY = "calling_activity";
    public static final String PUT_CATEGORY = "category";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_all_categories,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        Bundle bundle = getArguments();
        if (bundle != null){
            String callingActivity = bundle.getString(CALLING_ACTIVITY);
            ArrayList<String> categories = bundle.getStringArrayList(ALL_CATEGORIES);
            if (categories != null){
                ListView listView = view.findViewById(R.id.listViewId);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,categories);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (callingActivity){
                            case "main_activity":
                                Intent intent = new Intent(getActivity(), Search.class);
                                intent.putExtra(PUT_CATEGORY,categories.get(position));
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                getActivity().startActivity(intent);
                                dismiss();
                                break;
                            case "search_activity":
                                try {
                                    getCategory = (GetCategory) getActivity();
                                    getCategory.getCategoryResult(categories.get(position));
                                    dismiss();
                                }catch (ClassCastException e){
                                    e.printStackTrace();
                                }

                                break;

                            default:
                                break;
                        }
                    }
                });
            }
        }

        return builder.create();
    }
}