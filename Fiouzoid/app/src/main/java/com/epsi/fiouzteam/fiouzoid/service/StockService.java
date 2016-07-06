package com.epsi.fiouzteam.fiouzoid.service;

import android.util.Log;

import com.epsi.fiouzteam.fiouzoid.dao.Database;
import com.epsi.fiouzteam.fiouzoid.http.HttpHelper;
import com.epsi.fiouzteam.fiouzoid.utils.LoggerSql;
import com.epsi.fiouzteam.fiouzoid.utils.Utils;
import com.epsi.fiouzteam.fiouzoid.model.GroupRessource;
import com.epsi.fiouzteam.fiouzoid.model.User;

/**
 * Created by Dju on 04/07/2016.
 */
public class StockService {
    private static final String TAG = "StockService";

    public static boolean PostExchange(int idGroup, String userTo, int idFrom, String itemName, int qte)
    {
        int idTo, idRessource;
        User to = Database.mUserDao.fetchByName(userTo);
        String ressource = (itemName.split("\t"))[1],
            url = Utils.BASE_URL + "/exchange/addexchange",
            posParams;
        GroupRessource gr = Database.mGroupDao.fetchRessourceByName(ressource);

        if(to == null)
        {
            Log.i(TAG, "\tuser fetchByName returned null");
            return false;
        }
        if(gr == null)
        {
            Log.i(TAG, "\tfetchRessourceByName returned null");
            return false;
        }

        idTo = to.getId();
        idRessource = gr.getIdRessource(); // TODO: get correct idRessource value
        
        posParams = "idrepo=" + idGroup + "&" +
                "idresource=" + idRessource + "&" +
                "iduserfrom=" + idFrom + "&" +
                "iduserto=" + idTo + "&" +
                "quantite=" + qte;
        
        Log.i(TAG, "addexchange post params:\n\t" + posParams );
        HttpHelper http = new HttpHelper(url, null);
        String ret = http.Post(posParams);

        Log.i(TAG, "post response:\n\t" + ret );
        String httpCode = ret.split("\n")[0];

        // TODO: do this server side
        /*
        if(qte > 0)
        {
            url = new String(Utils.BASE_URL + "/repo/createorupdatestock");
            String params = new String("idRepo=" + idGroup + "&idResource=" + idRessource + "&idUser=" + idFrom + "&quantity=" + String.valueOf(-1 * qte)),
                jsonRessource = new String(new HttpHelper(url, null).Post(params));

            jsonRessource = '[' + jsonRessource.split("\n")[1] + ']';
            Log.i(TAG, "jsonRessource1" + jsonRessource); // TODO server side: return full quantity
        }
        */


        return !httpCode.contains("404");

    }
}
