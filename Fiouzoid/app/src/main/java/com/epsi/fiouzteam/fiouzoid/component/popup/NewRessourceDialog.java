package com.epsi.fiouzteam.fiouzoid.component.popup;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by ThaZalman on 06/07/2016.
 */
public class NewRessourceDialog extends Dialog {
    private String _ressourceName, _ressourceUnite;
    private int _ressourcePrice;

    public NewRessourceDialog(Context context) {
        super(context);
    }

    protected NewRessourceDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public String get_ressourceName() {
        return _ressourceName;
    }

    public void set_ressourceName(String _ressourceName) {
        this._ressourceName = _ressourceName;
    }

    public String get_ressourceUnite() {
        return _ressourceUnite;
    }

    public void set_ressourceUnite(String _ressourceUnite) {
        this._ressourceUnite = _ressourceUnite;
    }

    public int get_ressourcePrice() {
        return _ressourcePrice;
    }

    public void set_ressourcePrice(int _ressourcePrice) {
        this._ressourcePrice = _ressourcePrice;
    }
}
