package com.epsi.fiouzteam.fiouzoid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epsi.fiouzteam.fiouzoid.R;
import com.epsi.fiouzteam.fiouzoid.model.User;
import com.epsi.fiouzteam.fiouzoid.service.UserService;

/**
 * Created by Ratan on 7/29/2015.
 */
public class SentFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.sent_layout,null);
        TextView tv = (TextView) v.findViewById(R.id.textViewName);
        User u = UserService.getUserById(0);

        tv.setText(u.toString());

        return v;
    }
}
