package com.epsi.fiouzteam.fiouzoid;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by ThaZalman on 05/07/2016.
 */
public class NewGroupDialog extends Dialog {
    private String _groupName, _descr;


    public NewGroupDialog(Context context) {
        super(context);
    }

    public NewGroupDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected NewGroupDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }



    public String get_groupName() {
        return _groupName;
    }

    public void set_groupName(String _groupName) {
        this._groupName = _groupName;
    }

    public String get_descr() {
        return _descr;
    }

    public void set_descr(String _descr) {
        this._descr = _descr;
    }
}
