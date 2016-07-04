package com.epsi.fiouzteam.fiouzoid.dao.joints;

/**
 * Created by Dju on 28/06/2016.
 */
public interface IGroupRessourceSchema {
    String GROUP_RESSOURCE_TABLE = "GroupRessource";
    String COLUMN_TYPE = "idTypeRessource";
    String COLUMN_GROUP = "idGroup";
    String COLUMN_QTE = "quantite";

    String[] GROUP_COLUMNS = new String[] { COLUMN_TYPE, COLUMN_GROUP, COLUMN_QTE};
}
