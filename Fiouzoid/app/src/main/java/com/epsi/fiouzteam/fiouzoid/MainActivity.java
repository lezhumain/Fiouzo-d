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

import com.epsi.fiouzteam.fiouzoid.component.popup.NewGroupDialog;
import com.epsi.fiouzteam.fiouzoid.dao.DataManager;
import com.epsi.fiouzteam.fiouzoid.dao.Database;
import com.epsi.fiouzteam.fiouzoid.fragment.TabFragment;
import com.epsi.fiouzteam.fiouzoid.http.HttpHelper;
import com.epsi.fiouzteam.fiouzoid.http.TaskDelegate;
import com.epsi.fiouzteam.fiouzoid.utils.Utils;
import com.epsi.fiouzteam.fiouzoid.model.Group;
import com.epsi.fiouzteam.fiouzoid.model.User;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskDelegate{
    private static final String TAG = "MainActivity";

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    public static int APPUSERID = 1;

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

        DataManager.SaveGroups(APPUSERID);
        mGroups = Database.mGroupDao.fetchAllGroups();
        int groupId = mGroups.get(0).getId();
        DataManager.SaveData(groupId, APPUSERID);

        LoadGroup(mGroups.get(0).getName());



        /**
         * Put groups navigation items
         */
        this.updateGroupItems();

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
                            int idUser = MainActivity.APPUSERID;

                            // post groupName and descr
                            String params = "idUser=" + String.valueOf(idUser) + "&name=" + groupName + "&description=";
                            final String url = Utils.BASE_URL + "/repo/addRepo";
                            String jsonGroup = (new HttpHelper(url, null)).Post(params);
                            jsonGroup = '[' + jsonGroup.split("\n")[1] + ']';
                            Log.i(TAG, "jsonGroup" + jsonGroup);


                            // store the group
                            Group newGroup = (Group.FromJson(jsonGroup)).get(0);
                            Database.mGroupDao.addGroup(newGroup);

                            // store groupUsers (the creator)
                            User user = Database.mUserDao.fetchById(APPUSERID);
                            List<User> lst = new ArrayList<User>();
                            lst.add(user);
                            Database.mUserDao.addUsersToGroupe(lst, newGroup.getId());

                            // update groups in drawermenu
                            mGroups.add(newGroup);
                            updateGroupItems();
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

        if(toolbar == null)
            return;

        toolbar.inflateMenu(R.menu.menu_main);

        // click sur un item du menu parametres
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i(TAG, "\tmenu item clicked");

                if(item.getItemId() == R.id.action_quit)
                {
                    // close the app
                    finish();
                }

                return true;
            }
        });

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar, R.string.app_name,
        R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();
    }

    private void updateGroupItems() {
        Menu m = mNavigationView.getMenu();
//        SubMenu topChannelMenu = (SubMenu)m.findItem(R.id.nav_groups_menu);
        SubMenu topChannelMenu = (m.findItem(R.id.nav_groups)).getSubMenu();

//        if(topChannelMenu == null) {
//            Log.i(TAG, "menu was null...");
//            topChannelMenu = m.addSubMenu("Groupes");
//        }
        topChannelMenu.clear();

        for (Group group : mGroups)
            topChannelMenu.add(group.getName());

        // hack
        MenuItem mi = m.getItem(m.size()-1);
        mi.setTitle(mi.getTitle());
    }

    public void LoadGroup(String groupName)
    {
        mCurrentGroup = Database.mGroupDao.fetchByName(groupName);
        int groupId = mCurrentGroup.getId();

        Hashtable<String, Integer> stocks = Database.mGroupDao.fetchStocksByGroupe(groupId);
        mCurrentGroup.setStock( stocks );

        List<User> users = Database.mUserDao.fetchAllByGroup(groupId);
        mCurrentGroup.setUsers(users);

        Log.i(TAG, "LoadGroup():\t" + mCurrentGroup.toString());
    }

    /*
    public void LoadGroup(int groupId)
    {
        mCurrentGroup = Database.mGroupDao.fetchById(groupId);
        Log.i(TAG, "LoadGroup():\t" + mCurrentGroup.toString());
    }
    */

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
    
    public int GetGroupeId() {
        return mCurrentGroup.getId();
    }

    public void SetStock(String typeRessourceName, int stock) {
        Hashtable<String, Integer> actualStock = mCurrentGroup.getStock();

        actualStock.remove(typeRessourceName);
        actualStock.put(typeRessourceName, stock);

        mCurrentGroup.setStock(actualStock);
    }

    public void AddRessource(String ressourceName, int qte)
    {
        Hashtable<String, Integer> stock = mCurrentGroup.getStock();
        stock.put(ressourceName, qte);

        mCurrentGroup.setStock(stock);
    }
}
