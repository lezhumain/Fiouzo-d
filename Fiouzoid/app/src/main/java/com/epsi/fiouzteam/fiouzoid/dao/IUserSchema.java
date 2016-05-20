package com.epsi.fiouzteam.fiouzoid.dao;

import java.util.*;

public interface IUserSchema {
   String USER_TABLE = "USERS";
   String COLUMN_ID = "id";
   String COLUMN_FIRST_NAME = "first_name";
   String COLUMN_LAST_NAME = "last_name";
   String COLUMN_NICK_NAME = "nick_name";
   String USER_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
       + USER_TABLE
       + " ("
       + COLUMN_ID
       + " INTEGER PRIMARY KEY, "
       + COLUMN_FIRST_NAME
       + " TEXT NOT NULL, "
       + COLUMN_LAST_NAME
       + " TEXT NOT NULL, "
       + COLUMN_NICK_NAME
       + " TEXT NOT NULL"
   + ")";

   String[] USER_COLUMNS = new String[] { COLUMN_ID, 
      COLUMN_FIRST_NAME, COLUMN_LAST_NAME, COLUMN_NICK_NAME };
}
