package com.example.madee.sga;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class Dialogs {
    public static Dialog LoaderDialog, SuccessDialog, FailedDialog;
    public static Button dbtn, dbtn1;
    public static TextView txt, txtfailed;

    public static void Loader(Context context, int flag) {
        switch (flag) {
            case 0:
                LoaderDialog = new Dialog(context);
                LoaderDialog.requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS);
                LoaderDialog.setContentView(R.layout.wait_loader);
                LoaderDialog.show();
                break;
            case 1:
                LoaderDialog.dismiss();
                break;
            default:
                LoaderDialog.dismiss();
        }
    }

    public static void ShowSuccessDialog(String Message, Context context) {
        SuccessDialog = new Dialog(context);
        SuccessDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        SuccessDialog.setContentView(R.layout.dialog_created);
        txt = SuccessDialog.findViewById(R.id.created_dialog_body);
        txt.setText(Message);
        dbtn = SuccessDialog.findViewById(R.id.created_dialog_btn);
        dbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuccessDialog.dismiss();
            }
        });
        SuccessDialog.show();
    }

    public static void ShowErrorDialog(String Message, Context context) {
        FailedDialog = new Dialog(context);
        FailedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        FailedDialog.setContentView(R.layout.dialog_failed);
        txtfailed = FailedDialog.findViewById(R.id.failed_dialog_body);
        txtfailed.setText(Message);
        dbtn1 = FailedDialog.findViewById(R.id.failed_dialog_btn);
        dbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FailedDialog.dismiss();
            }
        });
        FailedDialog.show();
    }
}
