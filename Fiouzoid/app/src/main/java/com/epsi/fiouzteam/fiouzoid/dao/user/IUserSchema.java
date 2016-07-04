package com.epsi.fiouzteam.fiouzoid.dao.user;

public interface IUserSchema {
   String USER_TABLE = "User";
   String COLUMN_ID = "id";
   String COLUMN_USERNAME = "username";
   String COLUMN_FIRST_NAME = "firstName";
   String COLUMN_LAST_NAME = "lastName";
   String COLUMN_IS_ADMIN = "isAdmin";
   String USER_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
         + USER_TABLE
         + " ("
         + COLUMN_ID
         + " INTEGER PRIMARY KEY, "
         + COLUMN_USERNAME
         + " VARCHAR NOT NULL, "
         + COLUMN_FIRST_NAME
         + " VARCHAR NOT NULL"
         + COLUMN_LAST_NAME
         + " VARCHAR NOT NULL"
         + COLUMN_IS_ADMIN
         + " BOOLEAN"
   + ")";

   String[] USER_COLUMNS = new String[] { COLUMN_ID, COLUMN_USERNAME, COLUMN_FIRST_NAME, COLUMN_LAST_NAME, COLUMN_IS_ADMIN };
}
