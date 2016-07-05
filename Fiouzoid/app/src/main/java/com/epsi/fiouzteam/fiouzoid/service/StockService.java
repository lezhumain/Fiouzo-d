package com.epsi.fiouzteam.fiouzoid.service;

import android.provider.ContactsContract;
import android.util.Log;

import com.epsi.fiouzteam.fiouzoid.dao.Database;
import com.epsi.fiouzteam.fiouzoid.dao.user.UserDao;
import com.epsi.fiouzteam.fiouzoid.http.HttpHelper;
import com.epsi.fiouzteam.fiouzoid.http.Utils;
import com.epsi.fiouzteam.fiouzoid.model.GroupRessource;
import com.epsi.fiouzteam.fiouzoid.model.User;

/**
 * Created by Dju on 04/07/2016.
 */
public class StockService {
    private static final String TAG = "StockService";

    public static boolean PostExchange(int idGroup, String userTo, int idFrom, String itemName, int qte)
    {
        int idTo = -1, idRessource = -1;
        User to = Database.mUserDao.fetchByName(userTo);
        String ressource = (itemName.split("\t"))[1],
            url = Utils.BASE_URL + "/exchange/addexchange",
            posParams = "";
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
        idRessource = gr.getIdRessource();
        
        posParams = "idrepo=" + idGroup + "&" +
                "idresource=" + idRessource + "&" +
                "iduserfrom=" + idFrom + "&" +
                "iduserto=" + idTo + "&" +
                "quantity=" + qte;
        
        Log.i(TAG, "post params:\n\t" + posParams );
        HttpHelper http = new HttpHelper(url, null);
        String ret = http.Post(posParams);

        Log.i(TAG, "post response:\n\t" + ret );
        String httpCode = ret.split("\n")[0];

        if(httpCode.contains("404"))
            return false;

        return true;
    }
}
