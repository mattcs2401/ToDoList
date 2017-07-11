package mcssoft.com.todolist.activity;

import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.adapter.PagerAdapter;
import mcssoft.com.todolist.database.Database;
import mcssoft.com.todolist.utility.Resources;

/**
 * Class to Add, Edit, or Delete a To Do item.
 */
public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String action = getIntent().getAction();
        Bundle bundle = getIntent().getBundleExtra(Resources.getInstance().getString(R.string.bundle_key));
        listItemType = bundle.getString(Resources.getInstance().getString(R.string.list_type_key));

        if(action.equals(Resources.getInstance().getString(R.string.list_add_action_key))) {
            // add a new item to a list.
            addListItem();
        } else if (action.equals(Resources.getInstance().getString(R.string.list_edit_action_key))) {
            // TBA.
        } else if(action.equals(Resources.getInstance().getString(R.string.list_delete_action_key))) {
            // TBA.
        }
        String bpo = "";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_edit, menu);
        return true;
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Listeners">
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_edit_save:
                // collate values that are checked in the shopping list items table.
                collateValuesFromSave();
                clearValuesFromSave();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view instanceof FloatingActionButton) {
            collateValuesFromSave();
            clearValuesFromSave();
        }
    }
    //</editor-fold>

    /**
     * Get a list of all the selected shopping items.
     */
    private void collateValuesFromSave() {
        if (listItemType.equals(Resources.getInstance().getString(R.string.list_type_shopping))) {
            Cursor cursor = Database.getInstance().getCheckedReferenceItems();
            if(cursor.getCount() > 0) {
                //cursor.moveToFirst();
                String bp = "";
            } else {

            }

            String bp = "";
        } else if(listItemType.equals(Resources.getInstance().getString(R.string.list_type_general))) {
            // TBA.
        }
    }

    /**
     * Clear the check against all the previously selected shopping items.
     */
    private void clearValuesFromSave() {
        Database.getInstance().unCheckReferenceItems();
    }

    //<editor-fold defaultstate="collapsed" desc="Region: List actions">
    private void addListItem() {
        if (listItemType.equals(Resources.getInstance().getString(R.string.list_type_shopping))) {
            setContentView(R.layout.cv_shopping);
            setActionBar(Resources.getInstance().getString(R.string.toolbar_title_new_shopping));
            setAdapter();
            setTabLayout();
        } else if(listItemType.equals(Resources.getInstance().getString(R.string.list_type_general))) {
            setContentView(R.layout.cv_general);
            setActionBar(Resources.getInstance().getString(R.string.toolbar_title_new_general));
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Set UI components">
    private void setActionBar(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(title);
        ((FloatingActionButton) findViewById(R.id.id_fab)).setOnClickListener(this);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.id_fab);
//        fab.setOnClickListener(this);
    }

    private void setAdapter() {
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), this);
        viewPager = (ViewPager) findViewById(R.id.id_pager_container);
        viewPager.setAdapter(pagerAdapter);
    }

    private void setTabLayout() {
        String[] pageTitles = Resources.getInstance().getStringArray(R.array.shopping_item_types);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(pageTitles[0]));
        tabLayout.addTab(tabLayout.newTab().setText(pageTitles[1]));
        tabLayout.addTab(tabLayout.newTab().setText(pageTitles[2]));
        tabLayout.setupWithViewPager(viewPager);
    }
    //</editor-fold>

    private String listItemType;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
}
