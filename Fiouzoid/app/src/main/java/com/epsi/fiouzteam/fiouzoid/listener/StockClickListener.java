package com.epsi.fiouzteam.fiouzoid.listener;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.epsi.fiouzteam.fiouzoid.MainActivity;
import com.epsi.fiouzteam.fiouzoid.R;
import com.epsi.fiouzteam.fiouzoid.utils.Utils;
import com.epsi.fiouzteam.fiouzoid.service.StockService;

import java.util.regex.Pattern;

/**
 * Created by Dju on 23/06/2016.
 */
public class StockClickListener implements AdapterView.OnItemClickListener{
    private static final String TAG = "StockClickListener";
    private static final Pattern _numberPattern = Pattern.compile("^[0-9]+$");
    private MainActivity _contexts;
    private TextView _tv;

    public StockClickListener(Context a) {
        _contexts = (MainActivity)a;
    }

    private void decStockOnClick(final int qte)
    {
        String groupName = _tv.getText().toString();
        String[] lol = groupName.split("\t");

        if(lol.length != 3)
            Log.i(TAG, "Split error");
        else
        {
            String charStr = lol[2],
                    newName;
            int stock = Integer.parseInt(charStr);

            if(stock == 0)
                return;

            stock = qte < stock ? stock - qte : 0;

            String typeRessourceName = lol[1];
            _contexts.SetStock(typeRessourceName, stock);
            Log.i(TAG, "_contexts.SetStock(\"" + typeRessourceName + "\", \"" + String.valueOf(stock) + "\")" );

            newName = lol[0] + '\t' + typeRessourceName + '\t' + stock;
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



        final int idGroup = _contexts.GetGroupeId(),
                idAppUser = MainActivity.APPUSERID;
        final Dialog popup = Utils.CreateExchangePopup(_contexts, "TITLE", msg, idGroup, null, null);
        popup.show();
        popup.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                EditText et = (EditText)popup.findViewById(R.id.targetEdit);
                String toName = et.getText().toString();
                final String qteStr = ((EditText)popup.findViewById(R.id.quantityEdit)).getText().toString();

                if(!formValid(qteStr, toName)) {
                    Log.i(TAG, "form not valid...");
                    return;
                }

                // itemName, groupName, fromName, toName
                final int qte = Integer.parseInt(qteStr);

                if( StockService.PostExchange(idGroup, toName, idAppUser, itemName, qte) )
                    decStockOnClick(qte);
            }
        });
    }

    private boolean formValid(String qteStr, String toName) {
        return (_numberPattern.matcher(qteStr).matches()) && (toName != null) && (!toName.isEmpty()) ;
    }
}
