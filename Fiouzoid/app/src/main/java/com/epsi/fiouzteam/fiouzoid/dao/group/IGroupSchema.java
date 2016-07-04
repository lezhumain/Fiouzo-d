package com.epsi.fiouzteam.fiouzoid.dao.group;

public interface IGroupSchema {
   String GROUP_TABLE = "\"Group\"";
   String COLUMN_ID = "id";
   String COLUMN_NAME = "name";
   String COLUMN_DESCRIPTION = "description";
   String GROUP_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
       + GROUP_TABLE
       + " ("
       + COLUMN_ID
       + " INTEGER PRIMARY KEY, "
       + COLUMN_NAME
       + " VARCHAR NOT NULL, "
        + COLUMN_DESCRIPTION
        + " TEXT NOT NULL"
   + ")";

   String[] GROUP_COLUMNS = new String[] { COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION};
}
