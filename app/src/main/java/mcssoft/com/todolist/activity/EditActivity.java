package mcssoft.com.todolist.activity;

import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.adapter.pager.ShoppingItemPagerAdapter;
import mcssoft.com.todolist.database.Database;
import mcssoft.com.todolist.database.Schema;
import mcssoft.com.todolist.fragment.dialog.NothingSelectedFragment;
import mcssoft.com.todolist.interfaces.INothingSelected;
import mcssoft.com.todolist.utility.DateTime;
import mcssoft.com.todolist.utility.Resources;

/**
 * Class to Add, Edit, or Delete a To Do item.
 */
public class EditActivity extends AppCompatActivity
        implements View.OnClickListener, INothingSelected {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String action = getIntent().getAction();
        Bundle bundle = getIntent().getBundleExtra(Resources.getInstance().getString(R.string.bundle_pagenumber_key));
        listItemType = bundle.getString(Resources.getInstance().getString(R.string.list_type_key));

        if(action.equals(Resources.getInstance().getString(R.string.list_add_action_key))) {
            // add a new item to a list.
            addListItem();
        } else if (action.equals(Resources.getInstance().getString(R.string.list_edit_action_key))) {
//            Toast.makeText(this, "In EditActivity", Toast.LENGTH_SHORT).show();
            editListItem();
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

    //<editor-fold defaultstate="collapsed" desc="Region: Interface">
    @Override
    public void iNoSelect(boolean value) {
        if(!value) {
            finish();
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Listeners">
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // back via action bar.
                finalise(false);
                break;
            case R.id.id_edit_save:
                finalise(true);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view instanceof FloatingActionButton) {
            finalise(true);
        }
    }

    @Override
    public void onBackPressed() {
        finalise(false);
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Utility">
    private void finalise(boolean action) {
        if(!action) {
            uncheckReferenceItems();
            finish();
        } else {
            if (listItemType.equals(Resources.getInstance().getString(R.string.list_type_shopping))) {
                Cursor cursor = Database.getInstance().getCheckedReferenceItems();
                if(cursor.getCount() > 0) {
                    writeNewShoppingList(getRefIds(cursor));
                    uncheckReferenceItems();
                    finish();
                } else {
                    showNothingSelectedDialog();
                }
            } else if (listItemType.equals(Resources.getInstance().getString(R.string.list_type_general))) {
                // TBA.
                String bp = "";
            }
        }
    }

    private void writeNewShoppingList(int[] refIds) {
        List<String> colVals = new ArrayList<>();
        DateTime dateTime = new DateTime();

        colVals.add(dateTime.getCompactedDateTime());      // list identifier.
        colVals.add(dateTime.getFormattedDate(false));     // list date.
        colVals.add("TBA");                                // list name.

        long rowId = Database.getInstance().createShoppingList(colVals);
        Database.getInstance().createShoppingListItems(rowId, refIds);
    }

    private int[] getRefIds(Cursor cursor) {
        int size = cursor.getCount();
        int[] refIds = new int[size]; //cursor.getCount()];
        int colNdx = cursor.getColumnIndex(Schema.REF_ITEM_ROWID);
        int ndx = 0;
        while(cursor.moveToNext()) {
            if (ndx < size) {
                refIds[ndx] = cursor.getInt(colNdx);
                ndx++;
            }
        }
        return refIds;
    }

    private void showNothingSelectedDialog() {
        NothingSelectedFragment nsf = new NothingSelectedFragment();
        nsf.show(getFragmentManager(), null);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: List actions (Add, Edit etc)">
    private void addListItem() {
        if (listItemType.equals(Resources.getInstance().getString(R.string.list_type_shopping))) {
            setContentView(R.layout.cv_shopping);
            setActionBar(Resources.getInstance().getString(R.string.toolbar_title_new_shopping));
            setAdapter();
        } else if(listItemType.equals(Resources.getInstance().getString(R.string.list_type_general))) {
            setContentView(R.layout.cv_general);
            setActionBar(Resources.getInstance().getString(R.string.toolbar_title_new_general));
            // TBA - remainder.
        }
    }

    private void editListItem() {
        if (listItemType.equals(Resources.getInstance().getString(R.string.list_type_shopping))) {
            setContentView(R.layout.cv_shopping);
            setActionBar(Resources.getInstance().getString(R.string.toolbar_title_edit_shopping));
            setAdapter();
        } else if(listItemType.equals(Resources.getInstance().getString(R.string.list_type_general))) {
            setContentView(R.layout.cv_general);
            setActionBar(Resources.getInstance().getString(R.string.toolbar_title_edit_general));
            // TBA - remainder.
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
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.id_fab);
        fab.setOnClickListener(this);
    }

    private void setAdapter() {
        pagerAdapter = new ShoppingItemPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.id_pager_container);
        viewPager.setAdapter(pagerAdapter);
    }

    private void uncheckReferenceItems() {
        Database.getInstance().unCheckReferenceItems();
    }
    //</editor-fold>

    private String listItemType;
    private ViewPager viewPager;
    private ShoppingItemPagerAdapter pagerAdapter;
}
