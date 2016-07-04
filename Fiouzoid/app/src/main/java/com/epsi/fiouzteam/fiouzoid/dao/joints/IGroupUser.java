package com.epsi.fiouzteam.fiouzoid.dao.joints;

/**
 * Created by Dju on 28/06/2016.
 */
public interface IGroupUser {
    String GROUP_USER_TABLE = "UserGroup";
    String COLUMN_USER = "idUsesr";
    String COLUMN_GROUP = "idGroup";

    String[] GROUP_COLUMNS = new String[] { COLUMN_USER, COLUMN_GROUP };
}
