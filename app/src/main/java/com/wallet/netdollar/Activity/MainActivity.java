package com.wallet.netdollar.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.wallet.netdollar.R;
import com.wallet.netdollar.fragments.AdapterFragment;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    private DrawerLayout mDrawer;
    public NavigationView nvDrawer;
    ViewPager viewPager;
    private ActionBarDrawerToggle drawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.tab_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //toolbar.setNavigationIcon(R.drawable.ic_qrcode);
        viewPager =findViewById(R.id.viewpager);
        AdapterFragment PagerAdapter= new AdapterFragment(getSupportFragmentManager(),MainActivity.this);
        viewPager.setAdapter(PagerAdapter);
        TabLayout tabLayout= findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        mDrawer = findViewById(R.id.drawer_layout);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home1);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_invoice);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_payment);
        drawerToggle = setupDrawerToggle();
        nvDrawer =  findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);
        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);
        SharedPreferences sharedUser = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        closeSession();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.action_scan:
                Intent intent=new Intent(MainActivity.this,ScannerActivity.class) ;
                startActivity(intent);
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass = null;

        switch(menuItem.getItemId()) {
            case R.id.navtransfer:
                viewPager.setCurrentItem(1);
            break;
            case R.id.navfacture:
                viewPager.setCurrentItem(0);
                break;
            case R.id.navpaiement:
                Intent inten=new Intent(MainActivity.this,PaymentActivity.class) ;
                startActivity(inten);
                break;
            case R.id.navdeconnexion:
                SharedPrefs.saveShared(MainActivity.this,"login","true");
                Toast.makeText(getApplicationContext(), "Good bye", Toast.LENGTH_LONG).show();
                Intent intentd=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intentd);
                finish();

                break;
            default:
                viewPager.setCurrentItem(0);

        }

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_scanner,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void closeSession()
    {
        Boolean check=Boolean.valueOf(SharedPrefs.readShared(MainActivity.this,"login","true"));
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        intent.putExtra("login",check);
        if (check)
        {
            startActivity(intent);
            finish();
        }
    }
}
