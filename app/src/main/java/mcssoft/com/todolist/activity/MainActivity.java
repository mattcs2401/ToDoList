package mcssoft.com.todolist.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.adapter.main.MainAdapter;
import mcssoft.com.todolist.database.Database;
import mcssoft.com.todolist.fragment.ListSelectFragment;
import mcssoft.com.todolist.interfaces.IItemClickListener;
import mcssoft.com.todolist.interfaces.IListSelect;
import mcssoft.com.todolist.utility.Resources;

public class MainActivity extends AppCompatActivity
        implements IListSelect, IItemClickListener, View.OnClickListener,
                   NavigationView.OnNavigationItemSelectedListener   {

    //<editor-fold defaultstate="collapsed" desc="Region: Interface">
    /**
     * Interface return on user selecting the type of list to create (from dialog).
     * @param value The radio button id.
     */
    @Override
    public void iSelected(int value) {
        switch(value) {
            // GENRL type list.
            case R.id.id_rb_list_select_general:
                doNewGeneralList();
                break;
            // Shopping type list.
            case R.id.id_rb_list_select_shopping:
                doNewShoppingList();
                break;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Lifecycle">
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initialiseBaseUI(); // toolbar, fab, nav drawer etc.
        initialiseUI();     // what shows in the main screen,
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_SHOPPING) {

            String bp = "";
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
            Snackbar.make(getCurrentFocus(),
                    Resources.getInstance().getString(R.string.app_home_screen), Snackbar.LENGTH_LONG).show();
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
            // Launch dialog for user to select new list type.
            showListSelectDialog();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        // TBA
    }
        //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Utility">
    private void showListSelectDialog() {
        ListSelectFragment lsf = new ListSelectFragment();
        lsf.show(getFragmentManager(), "");
    }

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

    private void doNewGeneralList() {
        // TBA.
    }

    private void doListMaintenance() {
        // TBA.
    }

    private void setCursor() {
        cursor = Database.getInstance().getAllShopping();
    }

    private void setMainAdapter(boolean isEmptyView) {
        adapter = new MainAdapter(isEmptyView);
        if(!isEmptyView) {
            adapter.swapCursor(cursor);
            adapter.setOnItemClickListener(this);
        }
    }

    private  void setRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.id_rv_content_main);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.scrollToPosition(0);
        recyclerView.setLayoutManager(llm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
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

    private void initialiseUI() {
        int count = 0;
        SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
        Bundle args = getIntent().getBundleExtra(Resources.getInstance().getString(R.string.bundle_key));

        if(args != null) {
            // save value.
            count = args.getInt(Resources.getInstance().getString(R.string.sl_item_count_key));
            sp.edit().putInt(Resources.getInstance().getString(R.string.sl_item_count_key), count).commit();
        } else {
            // args will be null when entering this activity as back press from the Edit activity.
            count = sp.getInt(Resources.getInstance().getString(R.string.sl_item_count_key), 0);
        }

            setCursor();
            setMainAdapter(true);
            setRecyclerView();
    }
    //</editor-fold>

    private Cursor cursor;
    private MainAdapter adapter;
    private DrawerLayout drawer;

    private static final int NEW_SHOPPING = 1;   // request code.
}
// Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
// .setAction("Action", null).show();

