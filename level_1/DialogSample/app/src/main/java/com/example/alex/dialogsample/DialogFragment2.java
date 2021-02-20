package com.example.alex.dialogsample;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

public class DialogFragment2  extends DialogFragment {
    int mIdx;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog
                .Builder(getActivity());
        builder.setTitle("Select search enjine")
                .setSingleChoiceItems(MainActivity.ENGINE_NAMES, -1,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int
                                    idx) {
                                mIdx = idx;
                            }
                        }).setPositiveButton("Select",
                new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int
                            which) {
                        Activity a  = getActivity();
                        if (a instanceof BrowserCall){
                            Toast.makeText(a, "which = " + which, Toast.LENGTH_LONG).show();
                            Toast.makeText(a, "mIdx = " + mIdx, Toast.LENGTH_LONG).show();
                            ((BrowserCall) a).callBrowser(mIdx);
                        } else {
                            Toast.makeText(a, "Sorry, uable to call browser", Toast.LENGTH_LONG).show();
                        }
                    }
                }).setNegativeButton("Not now",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int
                            which) {
                        // Выливаем в пропасть
                    }
                });

        return builder.create();
    }
}
