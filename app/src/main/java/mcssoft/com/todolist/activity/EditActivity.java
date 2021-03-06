package mcssoft.com.todolist.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.adapter.pager.ShoppingItemPagerAdapter;
import mcssoft.com.todolist.database.Database;
import mcssoft.com.todolist.fragment.GeneralItem;
import mcssoft.com.todolist.utility.Resources;

/**
 * Class to Add, Edit, or Delete a To Do item.
 */
public class EditActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String action = getIntent().getAction();
        Bundle bundle = getIntent().getBundleExtra(Resources.getInstance().getString(R.string.bundle_key));
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

    //<editor-fold defaultstate="collapsed" desc="Region: Listeners">
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // back is via action bar back navigation.
                if (listItemType.equals(Resources.getInstance().getString(R.string.list_type_shopping))) {
                    finalise(false);
                } else if(listItemType.equals(Resources.getInstance().getString(R.string.list_type_general))) {
                    // TBA
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finalise(false);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Utility">
    private void finalise(boolean action) {
        if(!action) {
            Database.getInstance().unCheckReferenceItems();
            finish();
        } else {
            // TBA.
        }
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
            GeneralItem gif = new GeneralItem();
            Bundle args = new Bundle();
            gif.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.id_gif_container, gif, "tag_general_item_fragment").commit();
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
    }

    private void setAdapter() {
        pagerAdapter = new ShoppingItemPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.id_pager_container);
        viewPager.setAdapter(pagerAdapter);
    }
    //</editor-fold>

    private String listItemType;
    private ViewPager viewPager;
    private ShoppingItemPagerAdapter pagerAdapter;
}
