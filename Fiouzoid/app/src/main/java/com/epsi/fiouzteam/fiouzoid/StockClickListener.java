package com.epsi.fiouzteam.fiouzoid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.epsi.fiouzteam.fiouzoid.http.Utils;

/**
 * Created by Dju on 23/06/2016.
 */
public class StockClickListener implements AdapterView.OnItemClickListener{
    private static final String TAG = "StockClickListener";
    private MainActivity _contexts;
    private TextView _tv;

    public StockClickListener(Context a) {
        _contexts = (MainActivity)a;
    }

    private void decStockOnClick()
    {
        String groupName = _tv.getText().toString();
        String[] lol = groupName.split("\t");

        //msg += '\n' + lol.length;

        if(lol.length != 2)
            Log.i(TAG, "Split error");
        else
        {
            String charStr = lol[1],
                    newName;
            int stock = Integer.parseInt(charStr);

            if(stock == 0)
                return;

            stock -= 1;

            String typeRessourceName = (lol[0].split(" "))[1];
            _contexts.SetStock(typeRessourceName, stock);
            Log.i(TAG, "_contexts.SetStock(\"" + typeRessourceName + "\", \"" + String.valueOf(stock) + "\")" );

            newName = lol[0] + '\t' + stock;
            _tv.setText(newName);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        _tv = (TextView) view;
        final String itemName = _tv.getText().toString(),
            msg = "\tClick on item '" + itemName + "' (pos " + String.valueOf(position) + ", id " + String.valueOf(id) + ')';

        Log.i(TAG, msg);

        // TODO: ask for dest user iu dialog
        //Dialog popup = Utils.CreateAlertDialog(_contexts, msg);
        final Dialog popup = Utils.CreateStockPopup(_contexts, "TITLE", msg, _contexts.GetGroupeId(), null, null);
        popup.show();
        popup.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                EditText et = (EditText)popup.findViewById(R.id.targetEdit);
                String toName = et.getText().toString();

                if(toName != null && !toName.isEmpty())
                {
                    // TODO: post stock, idGroup, idFrom, idTo
                    // itemName, groupName, fromName, toName
                    decStockOnClick();
                }
            }
        });
    }
}
