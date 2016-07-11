package com.epsi.fiouzteam.fiouzoid.dao.joints;

/**
 * Created by Dju on 28/06/2016.
 */
public interface IGroupRessourceSchema {
    String GROUP_RESSOURCE_TABLE = "GroupRessource";
    String COL_ID_RESSOURCE = "idRessource";
    String COL_RESSOURCE = "ressource";
    String COL_GROUP = "idGroup";
    String COL_QTE = "quantite";

    String[] GROUP_RESSOURCE_COLUMNS = new String[] { COL_QTE, COL_RESSOURCE, COL_ID_RESSOURCE, COL_GROUP};
}
