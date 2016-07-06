package com.epsi.fiouzteam.fiouzoid.dao.group;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.epsi.fiouzteam.fiouzoid.dao.Database;
import com.epsi.fiouzteam.fiouzoid.dao.DbContentProvider;
import com.epsi.fiouzteam.fiouzoid.dao.joints.IGroupRessourceSchema;
import com.epsi.fiouzteam.fiouzoid.model.Group;
import com.epsi.fiouzteam.fiouzoid.model.GroupRessource;
import com.epsi.fiouzteam.fiouzoid.model.User;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class GroupDao extends DbContentProvider
        implements IGroupSchema, IGroupDao, IGroupRessourceSchema {

    private static final String TAG = "GroupDao";
    private Cursor cursor;
    private ContentValues initialValues;
    public GroupDao(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public Group fetchById(int id) {
        final String selectionArgs[] = { String.valueOf(id) };
        final String selection = COLUMN_ID + " = ?";
        Group group = new Group();
        cursor = super.query(GROUP_TABLE, GROUP_COLUMNS, selection,
                selectionArgs, COLUMN_ID);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                group = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        List<User> groupUsers = Database.mUserDao.fetchAllByGroup(group.getId());
        group.setUsers(groupUsers);

        return group;
    }

    @Override
    public List<Group> fetchAllByUser(int userId)
    {
        List<Group> groupList = new ArrayList<Group>();
        String  url = "select g.* from UserGroup ug, \"Group\" g, User u where g.id = ug.idGroup and u.id = ug.idUser and u.id = ?";
        String[] args = { String.valueOf(userId) };

        cursor = super.rawQuery(url, args);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Group group = cursorToEntity(cursor);
                groupList.add(group);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return groupList;
    }

    public List<Group> fetchAllGroups() {
        List<Group> groupList = new ArrayList<Group>();
        cursor = super.query(GROUP_TABLE, GROUP_COLUMNS, null,
                null, COLUMN_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Group group = cursorToEntity(cursor);
                groupList.add(group);
                cursor.moveToNext();
            }
            cursor.close();
        }


        for (Group g : groupList)
        {
            /*
            if(g.getId() > 2)
                break;
            */

            g.setUsers(Database.mUserDao.fetchAllByGroup(g.getId()));
        }

        return groupList;
    }

    public boolean addGroup(Group group) {
        // set values
        setContentValue(group);

        List<User> users = group.getUsers();
        if(users != null && !users.isEmpty()) // TODO test
        {
            String query = "insert into UserGroup select ";
            boolean isFirst = true;
            for (User u :
                    users) {
                if(isFirst) {
                    query += u.getId() + " as idUser, " + group.getId() + " as idGroup";
                    isFirst = false;
                }
                else
                    query += " union all select " + u.getId() + ", " + group.getId();
            }
//            query += " ";

            Log.i(TAG, "raw query: " + query);
            super.rawQuery(query, null);
        }

        Log.i(TAG, "inserting group '" + group.getName() + "'");
        long res = super.insert(GROUP_TABLE, getContentValue());
        return res > 0;
    }

    @Override
    public boolean addGroups(List<Group> groups) {
        boolean ret = true;
        for (Group u : groups)
        {
            boolean val = addGroup(u);
            ret = ret != false && val;
        }

        return ret;
    }

    @Override
    public boolean deleteAllGroups() {
        return false;
    }

    protected Group cursorToEntity(Cursor cursor) {

        Group group = new Group();

        int idIndex, nickNameIndex, emailIndex;

        if (cursor != null) {
            if (cursor.getColumnIndex(COLUMN_ID) != -1) {
                idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                group.setId(cursor.getInt(idIndex));
            }
            if (cursor.getColumnIndex(COLUMN_NAME) != -1) {
                nickNameIndex = cursor.getColumnIndexOrThrow(
                        COLUMN_NAME);
                group.setName(cursor.getString(nickNameIndex));
            }
            if (cursor.getColumnIndex(COLUMN_DESCRIPTION) != -1) {
                emailIndex = cursor.getColumnIndexOrThrow(
                        COLUMN_DESCRIPTION);
                group.setDescription(cursor.getString(emailIndex));
            }

        }
        return group;
    }

    public Hashtable<String, Integer> fetchStocksByGroupe(int idGroup)
    {
        String query = "SELECT quantite, ressource FROM GroupRessource where idGroup = " + String.valueOf(idGroup);
        Hashtable<String, Integer> stocks = new Hashtable<>();
        String[] args = { String.valueOf(idGroup) };
        String[] columns = {"quantite", "ressource"};
        final String selectionArgs[] = { String.valueOf(idGroup) };
        final String selection = "idGroup = ?";

        //cursor = super.query("GroupRessource", columns, selection, selectionArgs, null);
        cursor = super.rawQuery(query, null);
        Log.i(TAG, "query:\n\t" + query);

        int nameIndex, qteIndex, qte;
        String name;
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                nameIndex = cursor.getColumnIndexOrThrow("ressource");
                name = cursor.getString(nameIndex);

                qteIndex = cursor.getColumnIndexOrThrow("quantite");
                qte = cursor.getInt(qteIndex);

                stocks.put(name, qte);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return stocks;
    }

    /*
    public void addStocksToGroup(Hashtable<String, Integer> stocks, int groupId)
    {
        boolean isFirst = true;
        Object[] keys = stocks.keySet().toArray();
        int cpt = 0,
            stock = stocks.get(keys[cpt]);
        String stockQuery = "",
                query = "insert into GroupRessource select " +
                        String.valueOf( stock ) + " as quantite, '" +
                        keys[cpt] + "' as ressource, " +
                        groupId + " as idGroup ";

        // TODO do without foreach
        cpt++;
        for (; cpt < keys.length; ++cpt)
        {

            //if(keys.length < 2)
            //    break;

            String keyStr = (String)keys[cpt];
            stock = stocks.get(keyStr);
            //String keyStr = (String)key;


            if(keyStr == null) {
                cpt -= 1;
                continue;
            }

            query += "union all select " + String.valueOf( stock ) + ", '" + keys[cpt] + "', " + groupId + ' ';
        }

        Log.i(TAG, "query:\n\t" + query);
        execSql(query);
    }
    */

    private void setContentValue(Group group) {
        initialValues = new ContentValues();
        if(group.getId() > 0)
            initialValues.put(COLUMN_ID, group.getId());

        initialValues.put(COLUMN_NAME, group.getName());
        initialValues.put(COLUMN_DESCRIPTION, group.getDescription());
    }

    private ContentValues getContentValue() {
        return initialValues;
    }

    public Group fetchByName(String groupName) {
        //TODO: correct here
        final int id = 0;
        final String selectionArgs[] = { String.valueOf(groupName) };
        final String selection = COLUMN_NAME + " = ?";
        Group group = new Group();
        cursor = super.query(GROUP_TABLE, GROUP_COLUMNS, selection,
                selectionArgs, COLUMN_ID);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                group = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        List<User> groupUsers = Database.mUserDao.fetchAllByGroup(group.getId());
        group.setUsers(groupUsers);

        return group;
    }

    public void deleteGroupStocks()
    {
        String query = "delete from GroupRessource";
        execSql(query);
    }

    public void addStocksToGroup(List<GroupRessource> stocks, int groupId)
    {
        boolean isFirst = true;
        int cpt = 0;//, stock = stocks.get(keys[cpt]);
        String stockQuery = "",
                query = "insert into GroupRessource select ";

        // TODO do without foreach
        cpt++;
        for ( GroupRessource gr : stocks )
        {
            if(isFirst)
            {
                isFirst = false;
                query += gr.getQuantity() + " as " + COL_QTE + ", '" + gr.getResource() + "' as " + COL_RESSOURCE + ", " +
                        gr.getIdRessource() + " as " + COL_ID_RESSOURCE + ", " + groupId + " as " + COL_GROUP + ' ';
            }
            else
                query += "union all select " + gr.getQuantity() + ", '" + gr.getResource() + "', " + gr.getIdRessource() + ", " +
                       groupId + " ";
        }

        Log.i(TAG, "query:\n\t" + query);
        execSql(query);
    }

    public GroupRessource fetchRessourceByName(String ressource)
    {
        final String selectionArgs[] = { ressource };
        final String selection = COL_RESSOURCE + " = ?";
        GroupRessource groupRessource = null;
        cursor = super.query(GROUP_RESSOURCE_TABLE, GROUP_RESSOURCE_COLUMNS, selection,
                selectionArgs, COL_ID_RESSOURCE); // TODO check if COL_ID_RESSOURCE ok
        if (cursor != null) {
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                groupRessource = cursorToGroupRessource(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return groupRessource;
    }

    private GroupRessource cursorToGroupRessource(Cursor cursor)
    {
        GroupRessource groupRessource = new GroupRessource();

        int repoIndex, ressourceIndex, qteIndex, ressourceIdIndex;

        if (cursor != null) {
            if (cursor.getColumnIndex(COL_GROUP) != -1) {
                repoIndex = cursor.getColumnIndexOrThrow(COL_GROUP);
                int repoId = cursor.getInt(repoIndex);
                String groupName = (this.fetchById(repoId)).getName();
                groupRessource.setRepo(groupName);
            }
            if (cursor.getColumnIndex(COL_RESSOURCE) != -1) {
                ressourceIndex = cursor.getColumnIndexOrThrow(
                        COL_RESSOURCE);
                groupRessource.setResource(cursor.getString(ressourceIndex));
            }
            if (cursor.getColumnIndex(COL_QTE) != -1) {
                qteIndex = cursor.getColumnIndexOrThrow(
                        COL_QTE);
                groupRessource.setQuantity(cursor.getInt(qteIndex));
            }
            if (cursor.getColumnIndex(COL_ID_RESSOURCE) != -1) {
                ressourceIdIndex = cursor.getColumnIndexOrThrow(
                        COL_ID_RESSOURCE);
                groupRessource.setIdRessource(cursor.getInt(ressourceIdIndex));
            }

        }
        return groupRessource;
    }
}
