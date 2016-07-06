package com.epsi.fiouzteam.fiouzoid.service;

import android.util.Log;

import com.epsi.fiouzteam.fiouzoid.http.HttpHelper;
import com.epsi.fiouzteam.fiouzoid.model.Group;
import com.epsi.fiouzteam.fiouzoid.model.GroupRessource;

import java.util.List;

/**
 * Created by Dju on 21/05/2016.
 */
public class GroupService {
    private static final String TAG = "GroupService";

//    private static String fillWithUsers(List<User> users, String jsonStr) {
//        jsonStr += "'users': [";
//
//        if (users != null)
//        {
//            for (User u : users) {
//                jsonStr += u.toJson() + ',';
//            }
//            jsonStr = jsonStr.substring(0,jsonStr.length() - 1);
//        }
//
//        return  jsonStr + ']';
//    }

    public static List<Group> getAllGroups(int idUser) {
        idUser = idUser != 0 ? idUser : 1;
        String url = "http://api.davanture.fr/api/repo/getByUser?idUser=" + String.valueOf(idUser);
        HttpHelper helper = new HttpHelper(url, null);
        String resp = helper.Get();

        //Log.i(TAG, "json url:\n\t" + url);
        //Log.i(TAG, "json response:\n\t" + resp);

        List<Group> lg = Group.FromJson(resp);
        Log.i(TAG, "group toString:\n\t" + lg.get(0).toString());
        return lg;
    }

    public static List<GroupRessource> getStocksForGroup(int idGroup) {
        String url = "http://api.davanture.fr/api/repo/getallstock?idUser=" + String.valueOf(idGroup);
        HttpHelper helper = new HttpHelper(url, null);
        String resp = helper.Get();

        List<GroupRessource> lst = GroupRessource.FromJson(resp);
        Log.i(TAG, "json response:\n\t" + resp);

        return lst;

    }
}
