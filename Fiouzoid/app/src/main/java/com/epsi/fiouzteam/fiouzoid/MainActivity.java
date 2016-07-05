package com.epsi.fiouzteam.fiouzoid;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import com.epsi.fiouzteam.fiouzoid.dao.DataManager;
import com.epsi.fiouzteam.fiouzoid.dao.Database;
import com.epsi.fiouzteam.fiouzoid.http.HttpHelper;
import com.epsi.fiouzteam.fiouzoid.http.TaskDelegate;
import com.epsi.fiouzteam.fiouzoid.http.Utils;
import com.epsi.fiouzteam.fiouzoid.model.Group;
import com.epsi.fiouzteam.fiouzoid.model.User;
import com.epsi.fiouzteam.fiouzoid.service.UserService;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskDelegate{
    private static final String TAG = "MainActivity";

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    private int appUserId = 1;

    private Database mDb;
    private List<Group> mGroups = new ArrayList<>();

    private Group mCurrentGroup = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         *Setup the DrawerLayout and NavigationView
         */
         mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
         mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */
         mFragmentManager = getSupportFragmentManager();
         mFragmentTransaction = mFragmentManager.beginTransaction();
         mFragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();

        /**
         * Handle data and bdd
         */
        mDb = new Database(this);
        mDb.open();

        int groupId = 1;
        DataManager.SaveData(groupId, appUserId);

        mGroups = Database.mGroupDao.fetchAllGroups();
        LoadGroup(mGroups.get(0).getName());

        /**
         * Put groups navigation items
         */
        this.handleGroupItems();

        /**
         * Setup click events on the Navigation View Items. (NavigationDrawer)
         */
         mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
         {
             @Override
             public boolean onNavigationItemSelected(MenuItem menuItem)
             {
                mDrawerLayout.closeDrawers();


                if (menuItem.getItemId() == R.id.nav_item_inbox) {
                    Log.i(TAG, "\tClick on 'User Name'");
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();
                }
                else if (menuItem.getItemId() == R.id.nav_item_create_grp)
                {
                    // TODO: create gruop popup

                    Log.i(TAG, "\tClick on 'create Groupe'");
                    Dialog popup = Utils.CreateNewGroupPopup("TITLE", MainActivity.this);
                    popup.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            NewGroupDialog ngd = (NewGroupDialog)dialog;

                            if (ngd == null)
                            {
                                Log.i(TAG, "dialog is not NewGroupDialog");
                                return;
                            }

                            String groupName = ngd.get_groupName()
//                                    ,description = ngd.get_descr()
                                            ;
                            int idUser = MainActivity.this.getAppUserId();

                            // post groupName and descr
                            String params = "idUser=" + String.valueOf(idUser) + "&name=" + groupName + "&description=";
                            final String url = Utils.BASE_URL + "/repo/addRepo";
                            String jsonGroup = (new HttpHelper(url, null)).Post(params);
                            jsonGroup = '[' + jsonGroup.split("\n")[1] + ']';
                            Log.i(TAG, "jsonGroup" + jsonGroup);


                            // TODO store in sqlite
                            // store the group

                            Group newGroup = (Group.FromJson(jsonGroup)).get(0);
                            Database.mGroupDao.addGroup(newGroup);

                            // store groupUsers (the creator)
                            User user = Database.mUserDao.fetchById(appUserId);
                            List<User> lst = new ArrayList<User>();
                            lst.add(user);
                            Database.mUserDao.addUsersToGroupe(lst, newGroup.getId());
                        }
                    });
                    popup.show();
                }
                else
                {
                    Log.i(TAG, "\tClick on item '" + menuItem.getTitle() + "'");


                    TabFragment fragment = new TabFragment();
                    LoadGroup(menuItem.getTitle().toString());

                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,fragment).commit();
                }


                 return false;
            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main);

        // click sur un item du menu parametres
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i(TAG, "\tmenu item clicked");

                if(item.getItemId() == R.id.action_test)
                {
                    TestHttp();
                }

                return true;
            }
        });

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar, R.string.app_name,
        R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();
    }

    private void handleGroupItems() {
//        navView = (NavigationView) findViewById(R.id.navView);

        Menu m = mNavigationView.getMenu();
        SubMenu topChannelMenu = m.addSubMenu("Groupes");
//        MenuItem groupsItem = m.getItem(R.id.nav_item_groups);
//        SubMenu topChannelMenu = groupsItem.getSubMenu();
//        topChannelMenu.clear();


        //topChannelMenu.add("test");
        for (Group group : mGroups)
            topChannelMenu.add(group.getName());

        // hack
        MenuItem mi = m.getItem(m.size()-1);
        mi.setTitle(mi.getTitle());
    }

    public void LoadGroup(String groupName)
    {
        mCurrentGroup = Database.mGroupDao.fetchByName(groupName);

        Hashtable<String, Integer> stocks = Database.mGroupDao.fetchStocksByGroupe(mCurrentGroup.getId());
        mCurrentGroup.setStock( stocks );

        Log.i(TAG, "LoadGroup():\t" + mCurrentGroup.toString());
    }

    /*
    public void LoadGroup(int groupId)
    {
        mCurrentGroup = Database.mGroupDao.fetchById(groupId);
        Log.i(TAG, "LoadGroup():\t" + mCurrentGroup.toString());
    }
    */

    private void TestHttp()
    {
        String url = "http://api.davanture.fr/api/repo/getallstock?idUser=1";
        //String url = "http://posttestserver.com/post.php";
        HttpHelper help = new HttpHelper(url, null);
        //String data = "{\"player\":\"player1\",\"badge\":\"yeah\"}",
          //  result = help.Post(data);
            //result = help.Get();

        //Log.i(TAG, "\tTEST HTTP: result = " + result);
//        User u = UserService.getUserById(3);
        //User u = Database.mUserDao.fetchById(1);
        //List<Group> u = GroupService.getAllGroups(1);
        List<User> u = UserService.getUsersByGroup(1);

        //Log.i(TAG, "helper's response: " + u.toJson());


//        u.setLastName(u.getLastName() + 1);
//        u.setUsername(u.getUsername() + 1);
        /*
        boolean res = Database.mUserDao.addUser(u);
        if(!res)
            Log.i(TAG, "User wasn't added");
        */

        String msg = "";
        for (User g :
                u) {
            msg += g.toString() + '\n';
        }
        Log.i(TAG, msg);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDb.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void taskCompletionResult(String result) {
        Log.i(TAG, "\ttaskCompletionResult" + result);
    }

    public ArrayList<String> GetMembresGroupe() {
        ArrayList<String> userNames = new ArrayList<>();
        List<User> users = mCurrentGroup.getUsers();

        for (User user : users)
        {
            String nick = user.getUsername();

            if(nick == null)
                continue;

            userNames.add(nick);
        }


        return userNames;
    }

    public String GetGroupeName() {
        return mCurrentGroup != null ? mCurrentGroup.getName() : "-";
    }

    public ArrayList<String> GetStockGroupe() {
        ArrayList<String> stock = new ArrayList<>();
        Hashtable<String, Integer> actualStock = mCurrentGroup.getStock();
//        List<String> keys = actualStock.keys();

        Hashtable<String, Integer> currentStock = mCurrentGroup.getStock();
        for (String key :
                currentStock.keySet()) {
            stock.add('(' + String.valueOf(mCurrentGroup.getId()) + ")\t" + key + '\t' + String.valueOf(actualStock.get(key)));
        }

        return stock;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public int GetGroupeId() {
        return mCurrentGroup.getId();
    }

    public void SetStock(String typeRessourceName, int stock) {
        Hashtable<String, Integer> actualStock = mCurrentGroup.getStock();

        actualStock.remove(typeRessourceName);
        actualStock.put(typeRessourceName, stock);

        mCurrentGroup.setStock(actualStock);
    }
}
