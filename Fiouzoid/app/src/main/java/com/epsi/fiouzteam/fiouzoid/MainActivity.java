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

import com.epsi.fiouzteam.fiouzoid.dao.DataManager;
import com.epsi.fiouzteam.fiouzoid.dao.Database;
import com.epsi.fiouzteam.fiouzoid.http.TaskDelegate;
import com.epsi.fiouzteam.fiouzoid.model.Group;
import com.epsi.fiouzteam.fiouzoid.model.User;
import com.epsi.fiouzteam.fiouzoid.service.GroupService;
import com.epsi.fiouzteam.fiouzoid.service.UserService;

public class MainActivity extends AppCompatActivity implements TaskDelegate{
    private static final String TAG = "MainActivity";

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    private Database mDb;


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
         * Setup click events on the Navigation View Items.
         */
         mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
         {
             @Override
             public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                // if (menuItem.getItemId() == R.id.nav_item_sent) {
                //     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                 //    fragmentTransaction.replace(R.id.containerView,new SentFragment()).commit();

                // }
                //Section non utilise dans le drawer navigator
                if (menuItem.getItemId() == R.id.nav_item_inbox) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();
                }

                 return false;
            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
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

        mDb = new Database(this);
        mDb.open();

        DataManager.SaveUsers();
        DataManager.SaveGroups();
    }

    private void TestHttp()
    {
        //String url = "http://jsonplaceholder.typicode.com/posts/1";
//        User u = UserService.getUserById(3);
        //User u = Database.mUserDao.fetchById(1);
        Group u = GroupService.getTestGroupById(1);

        Log.i(TAG, "helper's response: " + u.toJson());


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
        Log.i(TAG, '\t' + result);
    }
}
