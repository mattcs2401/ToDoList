package mcssoft.com.todolist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import mcssoft.com.todolist.fragment.ListSelectFragment;
import mcssoft.com.todolist.interfaces.IListSelect;
import mcssoft.com.todolist.utility.Resources;

public class MainActivity extends AppCompatActivity
        implements IListSelect,
                   View.OnClickListener,
                   NavigationView.OnNavigationItemSelectedListener   {

    //<editor-fold defaultstate="collapsed" desc="Region: Interface">
    /**
     * Interface return on user selecting the type of list to create.
     * @param value The radio button id.
     */
    @Override
    public void iSelected(int value) {
        Resources res = new Resources(this);
        switch(value) {
            // General type list.
            case R.id.id_rb_list_select_general:
                // TBA
                break;
            // Shopping type list.
            case R.id.id_rb_list_select_shopping:
                // set list type as shopping.
                Bundle bundle = new Bundle();
                bundle.putString(res.getString(R.string.list_type_key), res.getString(R.string.list_type_shopping));
                // set action as add.
                Intent intent = new Intent(this, EditActivity.class);
                intent.setAction(res.getString(R.string.list_add_action_key));
                intent.putExtra(res.getString(R.string.bundle_key), bundle);
                startActivity(intent);
                break;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Lifecycle">
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0); // R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Listeners">
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

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
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Utility">
    private void showListSelectDialog() {
        ListSelectFragment lsf = new ListSelectFragment();
        lsf.show(getFragmentManager(), "");
    }
    //</editor-fold>
}
// Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
// .setAction("Action", null).show();

