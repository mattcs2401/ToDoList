package mcssoft.com.todolist.activity;

import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.adapter.PagerAdapter;
import mcssoft.com.todolist.utility.Resources;

/**
 * Class to Add, Edit, or Delete a To Do item.
 */
public class EditActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String action = getIntent().getAction();
        Bundle bundle = getIntent().getBundleExtra(Resources.getInstance().getString(R.string.bundle_key));
        String type = bundle.getString(Resources.getInstance().getString(R.string.list_type_key));

        if(action.equals(Resources.getInstance().getString(R.string.list_add_action_key))) {
            // add a new item to a list.
            addListItem(type);
        } else if (action.equals(Resources.getInstance().getString(R.string.list_edit_action_key))) {
            // edit a list item.
            editListItem(type);
        } else if(action.equals(Resources.getInstance().getString(R.string.list_delete_action_key))) {
            // delete a list item.
            deleteListItem(type);
        }
    }

    private void addListItem(String type) {
        if (type.equals(Resources.getInstance().getString(R.string.list_type_shopping))) {
            setContentView(R.layout.cv_shopping);
            setActionBar();
            setAdapter();
            setTabLayout();
        }
    }

    private void editListItem(String type) {
        if(type.equals(Resources.getInstance().getString(R.string.list_type_shopping))) {

        } else if (type.equals(Resources.getInstance().getString(R.string.list_type_general))) {

        }
    }

    private void deleteListItem(String type) {
        if(type.equals(Resources.getInstance().getString(R.string.list_type_shopping))) {

        } else if (type.equals(Resources.getInstance().getString(R.string.list_type_general))) {

        }

    }

    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(Resources.getInstance().getString(R.string.toolbar_title_new_shopping));
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

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
}
