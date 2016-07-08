package com.epsi.fiouzteam.fiouzoid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.epsi.fiouzteam.fiouzoid.MainActivity;
import com.epsi.fiouzteam.fiouzoid.R;

import java.util.ArrayList;

/**
 * Created by Ratan on 7/29/2015.
 */
public class MembresFragment extends Fragment {

    private static final String TAG = "MembresFragment";

    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        ListView mainListView = (ListView)
        View view = inflater.inflate(R.layout.membres_layout,null);
        String groupName = ((MainActivity)getContext()).GetGroupeName();

        TextView titleView = (TextView)view.findViewById(R.id.membres_group_title);
        if(titleView !=null)
        {
            titleView.setText("Membres de " + groupName);
        }

        mainListView = (ListView)view.findViewById(R.id.membres_list);

        if(mainListView == null)
            Log.d(TAG, "ERREUR");
        else
        {
            Log.d(TAG, "OKOK (context is of type " + getContext().getClass().getName() + ')');
            UpdateListView();
        }

        return view;
    }

    protected void UpdateListView()
    {
        ArrayList<String>  membreList = ((MainActivity)getContext()).GetMembresGroupe();
        // Create ArrayAdapter using the membre list.
        listAdapter = new ArrayAdapter<>(getContext(), R.layout.simplerow, membreList);
        // Set the ArrayAdapter as the ListView's adapter.
        mainListView.setAdapter( listAdapter );
    }
}
