package com.epsi.fiouzteam.fiouzoid;

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
import com.epsi.fiouzteam.fiouzoid.model.Group;
import com.epsi.fiouzteam.fiouzoid.model.User;
import com.epsi.fiouzteam.fiouzoid.service.GroupService;
import com.epsi.fiouzteam.fiouzoid.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskDelegate{
    private static final String TAG = "MainActivity";

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

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

        DataManager.SaveUsers();
        DataManager.SaveGroups();
        mGroups = Database.mGroupDao.fetchAllGroups();
        LoadGroup(1);

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
                    // TODO: create & use a user fragment

                    Log.i(TAG, "\tClick on 'User Name'");
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();
                }
//                else if (menuItem.getItemId() == R.id.nav_item_groups)
//                {
//                    // TODO: use correct fragment
//
//                    Log.i(TAG, "\tClick on 'Groupes'");
//                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
//                    xfragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();
//                }
                else
                {
                    Log.i(TAG, "\tClick on item '" + menuItem.getTitle() + "'");


                    // TODO: find out what item and pass params to fragment

                    TabFragment fragment = new TabFragment();
//                    Bundle args = new Bundle();
//                    args.putString("groupName", menuItem.getTitle().toString());
//                    fragment.setArguments(args);
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
        Log.i(TAG, "LoadGroup():\t" + mCurrentGroup.toString());
    }

    public void LoadGroup(int groupId)
    {
        mCurrentGroup = Database.mGroupDao.fetchById(groupId);
        Log.i(TAG, "LoadGroup():\t" + mCurrentGroup.toString());
    }

    private void TestHttp()
    {
        //String url = "http://jsonplaceholder.typicode.com/posts/1";
        String url = "http://posttestserver.com/post.php";
        HttpHelper help = new HttpHelper(url, null);
        String data = "{\"player\":\"player1\",\"badge\":\"yeah\"}",
            result = help.Post(data);
            //result = help.Get();

        Log.i(TAG, "\tTEST HTTP: result = " + result);
//        User u = UserService.getUserById(3);
        //User u = Database.mUserDao.fetchById(1);
        //Group u = GroupService.getTestGroupById(1);

        //Log.i(TAG, "helper's response: " + u.toJson());


//        u.setEmail(u.getEmail() + 1);
//        u.setNickName(u.getNickName() + 1);
        /*
        boolean res = Database.mUserDao.addUser(u);
        if(!res)
            Log.i(TAG, "User wasn't added");
        */
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
            String nick = user.getNickName();

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

        for (String key :
                actualStock.keySet()) {
            stock.add('(' + String.valueOf(mCurrentGroup.getId()) + ") " + key + '\t' + String.valueOf(actualStock.get(key)));
        }

        return stock;
    }
}
