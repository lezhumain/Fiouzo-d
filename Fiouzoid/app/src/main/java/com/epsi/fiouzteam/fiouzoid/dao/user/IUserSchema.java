package com.epsi.fiouzteam.fiouzoid.dao.user;

import java.util.*;

public interface IUserSchema {
   String USER_TABLE = "User";
   String COLUMN_ID = "id";
   String COLUMN_NICK_NAME = "nickName";
   String COLUMN_EMAIL = "email";
   String USER_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
       + USER_TABLE
       + " ("
       + COLUMN_ID
       + " INTEGER PRIMARY KEY, "
       + COLUMN_NICK_NAME
       + " VARCHAR NOT NULL, "
        + COLUMN_EMAIL
        + " VARCHAR NOT NULL"
   + ")";

   String[] USER_COLUMNS = new String[] { COLUMN_ID, COLUMN_NICK_NAME, COLUMN_EMAIL };
}
