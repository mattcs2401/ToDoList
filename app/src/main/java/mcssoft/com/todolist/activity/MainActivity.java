package mcssoft.com.todolist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.adapter.pager.MainPagerAdapter;
import mcssoft.com.todolist.interfaces.IItemClickListener;
import mcssoft.com.todolist.utility.Resources;

public class MainActivity extends AppCompatActivity
        implements IItemClickListener, View.OnClickListener,
                   NavigationView.OnNavigationItemSelectedListener   {

    //<editor-fold defaultstate="collapsed" desc="Region: Interface">
    @Override
    public void onItemClick(View view, int position) {
        String bp = ";";
        // TBA
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Lifecycle">
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initialiseBaseUI(); // toolbar, fab, nav drawer etc.
        setAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case NEW_SHOPPING:
            case EDIT_SHOPPING:
                setAdapter();     // essentially trigger reload main view.
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Listeners">
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_preference_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch(item.getItemId()) {
            case R.id.id_new_shopping_list:
                doNewShoppingList();
                break;
            case R.id.id_new_general_list:
                // TBA.
                doNewGeneralList();
                break;
            case R.id.id_list_maint:
                // TBA.
                doListMaintenance();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view instanceof FloatingActionButton) {
            String pageTitle = pagerAdapter.getPageTitle(viewPager.getCurrentItem());
            if(pageTitle.equals(Resources.getInstance().getString(R.string.list_type_shopping))) {
                doNewShoppingList();
            } else if (pageTitle.equals(Resources.getInstance().getString(R.string.list_type_general))) {
                doNewGeneralList();
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Utility">
    private void doNewShoppingList() {
        // set list type as shopping.
        Bundle bundle = new Bundle();
        bundle.putString(Resources.getInstance().getString(R.string.list_type_key),
                Resources.getInstance().getString(R.string.list_type_shopping));
        // set action as add.
        Intent intent = new Intent(this, EditActivity.class);
        intent.setAction(Resources.getInstance().getString(R.string.list_add_action_key));
        intent.putExtra(Resources.getInstance().getString(R.string.bundle_key), bundle);
        startActivityForResult(intent, NEW_SHOPPING);
    }

/*    private void doEditShoppingList(int position) {
        // set list type as shopping.
        Bundle bundle = new Bundle();
        bundle.putString(Resources.getInstance().getString(R.string.list_type_key),
                Resources.getInstance().getString(R.string.list_type_shopping));
        // set the position of the item to edit.
        bundle.putInt(Resources.getInstance().getString(R.string.list_edit_position_key), position);
        // set action as edit.
        Intent intent = new Intent(this, EditActivity.class);
        intent.setAction(Resources.getInstance().getString(R.string.list_edit_action_key));
        intent.putExtra(Resources.getInstance().getString(R.string.bundle_key), bundle);
        startActivityForResult(intent, EDIT_SHOPPING);
    }*/

    private void doNewGeneralList() {
        // set list type as shopping.
        Bundle bundle = new Bundle();
        bundle.putString(Resources.getInstance().getString(R.string.list_type_key),
                Resources.getInstance().getString(R.string.list_type_general));
        // set action as add.
        Intent intent = new Intent(this, EditActivity.class);
        intent.setAction(Resources.getInstance().getString(R.string.list_add_action_key));
        intent.putExtra(Resources.getInstance().getString(R.string.bundle_key), bundle);
        startActivityForResult(intent, NEW_GENERAL);    }

    private void doListMaintenance() {
        // TBA.
    }

    private void setAdapter() {
        pagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), this);
        viewPager = (ViewPager) findViewById(R.id.id_pager_container);
        viewPager.setAdapter(pagerAdapter);
    }

    private void initialiseBaseUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0); // R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Private vars">
    private DrawerLayout drawer;
    private ViewPager viewPager;
    private MainPagerAdapter pagerAdapter;

    private static final int NEW_SHOPPING = 1;   // request code.
    private static final int NEW_GENERAL = 2;    // "       "
    private static final int EDIT_SHOPPING = 3;  // "       "
    //</editor-fold>
}

