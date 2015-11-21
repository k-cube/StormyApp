package com.keivannorouzi.stormy.UI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import com.keivannorouzi.stormy.R;

/**
 * Created by keivannorouzi on 15-10-18.
 */
public class AlertDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(getString(R.string.error_title))
                .setMessage(getString(R.string.error_body))
                .setPositiveButton(getString(R.string.Positive_button), null);

        AlertDialog dialog = builder.create();
        return dialog;



    }
}
