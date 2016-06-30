package com.epsi.fiouzteam.fiouzoid.http;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.epsi.fiouzteam.fiouzoid.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cz.msebera.android.httpclient.HttpEntity;

/**
 * Created by Dju on 21/05/2016.
 */
public class Utils {
    private static final String TAG = "Utils";

    public static void Test()
    {
        HttpURLConnection urlConnection = null;
        try
        {
            URL url = new URL("http://www.android.com/");
            urlConnection = (HttpURLConnection) url.openConnection();


            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String recv = readStream(in);
            Log.i(TAG, '\t' + recv);
        }
        catch (Exception e)
        {
            Log.i(TAG, "\n=============================" +
                    "\n=============================" +
                    "\n" + e.getMessage() + " " + e.getClass().getName() +
                    "\n=============================" +
                    "\n=============================");
            e.printStackTrace();
        }
        finally
        {
            urlConnection.disconnect();
        }
    }

    public static String readStream(InputStream in) {
        byte[] b = new byte[1024];
        String ret = "";

        try {
            int count = in.read(b);
            ret = new String(b, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            ret = "";
        }

        return ret;
    }

    public static String entityToString(HttpEntity entity) {
        InputStream is = null;
        try {
            is = entity.getContent();
        } catch (IOException e) {
            return "";
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        StringBuilder str = new StringBuilder();

        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                str.append(line + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                //tough luck...
            }
        }
        return str.toString();
    }

    public static Dialog CreateStockPopup(Context context, String title, String msg, String okMsg, String cancelMsg)
    {
        if(okMsg == null || okMsg.isEmpty())
            okMsg = " OK ";
        if(cancelMsg == null || cancelMsg.isEmpty())
            cancelMsg  = " CANCEL ";


        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_popup);
        dialog.setTitle(title);



        // set the custom dialog components - text, image and button
        //TextView text = (TextView) dialog.findViewById(R.id.popup_text);
        //text.setText(msg);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        image.setImageResource(R.drawable.dbz);


        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // Cick on ok button
                EditText et = (EditText)dialog.findViewById(R.id.targetEdit);

                Log.i(TAG, "\tView class: " + v.getClass().getName().toString());
                Log.i(TAG, et.getText().toString());
                dialog.dismiss();
            }
        });
        dialogButton.setText(okMsg);

        dialogButton = (Button) dialog.findViewById(R.id.dialogButtonNOK);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogButton.setText(cancelMsg);

        //dialog.show();
        return dialog;
    }

    public static AlertDialog CreateAlertDialog(Context contexts, String msg)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(contexts);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder1.create();
    }
}
