package com.epsi.fiouzteam.fiouzoid.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import com.epsi.fiouzteam.fiouzoid.component.popup.NewRessourceDialog;
import com.epsi.fiouzteam.fiouzoid.dao.Database;
import com.epsi.fiouzteam.fiouzoid.http.HttpHelper;
import com.epsi.fiouzteam.fiouzoid.listener.StockClickListener;
import com.epsi.fiouzteam.fiouzoid.model.GroupRessource;
import com.epsi.fiouzteam.fiouzoid.model.Ressource;
import com.epsi.fiouzteam.fiouzoid.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ratan on 7/29/2015.
 */
public class StockFragment extends Fragment
{
    private static final String TAG = "StockFragment";

    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter;
    private StockClickListener listener;
    private NewRessourceDialog newStockPopup;
    private int _idRepo;
    private int _idUser;

    public StockFragment()
    {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.stock_layout,null);
        String groupName = ((MainActivity)getContext()).GetGroupeName();

        _idRepo = ((MainActivity) getContext()).GetGroupeId();
        _idUser = ((MainActivity) getContext()).GetAppUserId();


        TextView titleView = (TextView)view.findViewById(R.id.stock_group_title);
        if(titleView != null)
        {
            titleView.setText("Stock de " + groupName);
        }

        Context a = getActivity();
        listener = new StockClickListener(a);
        mainListView = (ListView)view.findViewById(R.id.stock_list);
        mainListView.setOnItemClickListener(listener);

        if(mainListView == null)
            Log.d(TAG, "ERREUR");
        else
        {
            Log.d(TAG, "OKOK (context is of type " + getContext().getClass().getName() + ')');
            handleListView();
        }

        // add button click listener
        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.add_stock_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleNewStock();
            }
        });

        return view;
    }

    private void handleNewStock() {
        // TODO: popup stock + post + sqlite
        newStockPopup = Utils.CreateRessourcePopup(getContext(), "Nouvelle ressource");
        newStockPopup.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (newStockPopup == null)
                {
                    Log.i(TAG, "dialog is not newStockPopup");
                    return;
                }

                String name     = newStockPopup.get_ressourceName(),
                        unit    = newStockPopup.get_ressourceUnite(),
                        price   = String.valueOf(newStockPopup.get_ressourcePrice());

                // post ressource
                String params = "name=" + name + "&unite=" + unit + "&price=" + price + "&idrepo=" + _idRepo;
                String url = Utils.BASE_URL + "/repo/addResourceToRepo";
                // TODO uncomment
                String jsonRessource = (new HttpHelper(url, null)).Post(params);
                jsonRessource = '[' + jsonRessource.split("\n")[1] + ']';
                Log.i(TAG, "jsonRessource" + jsonRessource);
                //jsonRessource = '[' + jsonRessource.split("\n")[1] + ']';

                Log.i(TAG, "full url: " + url + '?' + params);

                List<Ressource> lr = Ressource.FromJson("" + jsonRessource);

                //post stock
                url = new String(Utils.BASE_URL + "/repo/createorupdatestock");
                params = new String("idRepo=" + _idRepo + "&idResource=" + lr.get(0).getId() + "&idUser=" + _idUser + "&quantity=" + String.valueOf(0));
                jsonRessource = new String(new HttpHelper(url, null).Post(params));
                jsonRessource = '[' + jsonRessource.split("\n")[1] + ']';
                Log.i(TAG, "jsonRessource1" + jsonRessource);


                List<GroupRessource> lgr = GroupRessource.FromJson(jsonRessource);
                Database.mGroupDao.addStocksToGroup(lgr, _idRepo);
            }
        });
        newStockPopup.show();

    }

    private void handleListView()
    {
        ArrayList<String> stockList = ((MainActivity)getContext()).GetStockGroupe();

        // TODO layout + binding
        listAdapter = new ArrayAdapter<>(getContext(), R.layout.simplerow, stockList);
        mainListView.setAdapter( listAdapter );
    }
}
