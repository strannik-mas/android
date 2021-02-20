package com.example.alex.dialogsample;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;


public class DialogFragment1 extends DialogFragment {

    public DialogFragment1() {
        super();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_dialog)
                .setItems(MainActivity.ENGINE_NAMES, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*Activity a  = getActivity();
                        if (a instanceof BrowserCall){
                            ((BrowserCall) a).callBrowser(which);
                        } else {
                            Toast.makeText(a, "Sorry, uable to call browser", Toast.LENGTH_LONG).show();
                        }*/

                        Intent intent = new Intent(MainActivity.ACTION_ENGINE_SELECTED);
                        intent.putExtra(MainActivity.KEY_IDX, which);
                        getActivity().sendBroadcast(intent);
                    }
                });
        return builder.create();
    }
}
