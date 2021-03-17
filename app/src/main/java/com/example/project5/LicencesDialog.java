package com.example.project5;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class LicencesDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_licences,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view);

        TextView licences = view.findViewById(R.id.tvLicences);
        Button dismiss = view.findViewById(R.id.licences_dismissbtn);

        licences.setText(Utils.getLicences());
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return builder.create();
    }
}
