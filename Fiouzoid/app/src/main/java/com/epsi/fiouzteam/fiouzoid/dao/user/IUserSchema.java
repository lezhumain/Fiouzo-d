package com.epsi.fiouzteam.fiouzoid.dao.user;

import java.util.*;

public interface IUserSchema {
   String USER_TABLE = "USERS";
   String COLUMN_ID = "id";
   String COLUMN_NICK_NAME = "nick_name";
   String COLUMN_EMAIL = "email";
   String USER_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
       + USER_TABLE
       + " ("
       + COLUMN_ID
       + " INTEGER PRIMARY KEY, "
       + COLUMN_NICK_NAME
       + " TEXT NOT NULL"
        + COLUMN_EMAIL
        + " TEXT NOT NULL"
   + ")";

   String[] USER_COLUMNS = new String[] { COLUMN_ID, COLUMN_NICK_NAME, COLUMN_EMAIL };
}
