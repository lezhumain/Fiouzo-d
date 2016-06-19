package com.epsi.fiouzteam.fiouzoid;

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

import java.util.ArrayList;

/**
 * Created by Ratan on 7/29/2015.
 */
public class StockFragment extends Fragment
{
    private static final String TAG = "StockFragment";

    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        ListView mainListView = (ListView)
        View view = inflater.inflate(R.layout.stock_layout,null);
        String groupName = ((MainActivity)getContext()).GetGroupeName();

        TextView titleView = (TextView)view.findViewById(R.id.stock_group_title);
        if(titleView != null)
        {
            titleView.setText("Stock de " + groupName);
        }

        mainListView = (ListView)view.findViewById(R.id.stock_list);

        if(mainListView == null)
            Log.d(TAG, "ERREUR");
        else
        {
            Log.d(TAG, "OKOK (context is of type " + getContext().getClass().getName() + ')');
            handleListView();
        }

        return view;
    }

    protected void handleListView()
    {
        ArrayList<String> stockList = ((MainActivity)getContext()).GetStockGroupe();

        // TODO layout
        listAdapter = new ArrayAdapter<>(getContext(), R.layout.simplerow, stockList);
        mainListView.setAdapter( listAdapter );
    }
}
