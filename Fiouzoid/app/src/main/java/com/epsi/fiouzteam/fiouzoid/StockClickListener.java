package com.epsi.fiouzteam.fiouzoid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.epsi.fiouzteam.fiouzoid.http.Utils;

/**
 * Created by Dju on 23/06/2016.
 */
public class StockClickListener implements AdapterView.OnItemClickListener{
    private static final String TAG = "StockClickListener";
    private MainActivity _contexts;

    public StockClickListener(Context a) {
        _contexts = (MainActivity)a;
    }

    private void decStockOnClick(TextView tv)
    {
        String groupName = tv.getText().toString();
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
            tv.setText(newName);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        TextView tv = (TextView) view;
        String groupName = tv.getText().toString(),
            msg = "\tClick on item '" + groupName + "' (pos " + String.valueOf(position) + ", id " + String.valueOf(id) + ')';


        // TODO: dec stock after
        decStockOnClick(tv);

        Log.i(TAG, msg);

        // TODO: ask for dest user iu dialog
        // AlertDialog popup = Utils.CreateAlertDialog(_contexts, msg);
        // popup.show();
    }


}
