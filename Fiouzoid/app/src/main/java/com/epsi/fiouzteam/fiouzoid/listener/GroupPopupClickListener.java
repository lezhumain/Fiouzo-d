package com.epsi.fiouzteam.fiouzoid.listener;

import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.epsi.fiouzteam.fiouzoid.R;
import com.epsi.fiouzteam.fiouzoid.component.popup.NewGroupDialog;

/**
 * Created by ThaZalman on 05/07/2016.
 */
public class GroupPopupClickListener implements View.OnClickListener {
    private static final String TAG = "GroupPopupClickListener";
    private NewGroupDialog _parent;

    public GroupPopupClickListener(NewGroupDialog parent)
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
                    return;

            case R.id.dialogButtonNOK: // dismisses ok too
                _parent.dismiss();
                break;
            default:
                msg += "DEFAULT (error!)";
        }

        Log.i(TAG, msg);
    }

    private boolean validateForm() {
        EditText groupNameEdit = (EditText)_parent.findViewById(R.id.groupNameEdit);
        EditText descriptionEdit = (EditText)_parent.findViewById(R.id.descriptionEdit);

        if(groupNameEdit == null || descriptionEdit == null)
            return false;

        String groupName = groupNameEdit.getText().toString();
        _parent.set_groupName(groupName);
        _parent.set_descr(descriptionEdit.getText().toString());

        return groupName.length() > 0;

    }


}
