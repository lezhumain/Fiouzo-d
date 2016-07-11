package com.epsi.fiouzteam.fiouzoid.listener;

import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.epsi.fiouzteam.fiouzoid.R;
import com.epsi.fiouzteam.fiouzoid.component.popup.NewRessourceDialog;

/**
 * Created by ThaZalman on 06/07/2016.
 */
public class RessourcePopupClickListener implements View.OnClickListener
{
    private static final String TAG = "RessPopupClickListener";
    private NewRessourceDialog _parent;

    public RessourcePopupClickListener(NewRessourceDialog parent)
    {
        if(parent == null)
            Log.i(TAG, "/!\\ ERROR: parent was null");

        _parent = parent;
    }

    @Override
    public void onClick(View v) {
        AppCompatButton sender = (AppCompatButton)v;
        String msg = "\t";

        if(sender == null)
            return;


        switch (sender.getId())
        {
            case R.id.dialogButtonOK:
                boolean isValid = validateForm();

                if(!isValid)
                {
                    // dont dismiss
                    Log.i(TAG, "invalid data");
                    return;
                }

            case R.id.dialogButtonNOK: // dismisses ok too
                _parent.dismiss();
                break;
            default:
                msg += "DEFAULT (error!)";
        }

        Log.i(TAG, msg);
    }

    private boolean validateForm() {
        EditText nameEdit = (EditText)_parent.findViewById(R.id.ressourceNameEdit),
            unitEdit = (EditText)_parent.findViewById(R.id.uniteEdit),
            priceEdit = (EditText)_parent.findViewById(R.id.priceEdit);

        if(nameEdit == null || unitEdit == null || priceEdit == null )
            return false;

        String name = nameEdit.getText().toString(),
            unit = unitEdit.getText().toString(),
            price = priceEdit.getText().toString();

        price = price.length() == 0 ? "0" : price;

        _parent.set_ressourceName(name);
        _parent.set_ressourceUnite(unit);
        _parent.set_ressourcePrice(Integer.valueOf(price));

        return name.length() > 0 && unit.length() > 0 && price.length() > 0; // TODO check price maybe
    }
}
