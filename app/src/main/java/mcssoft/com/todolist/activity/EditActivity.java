package mcssoft.com.todolist.activity;

import android.support.design.widget.TabLayout;
import 	android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBar.TabListener;
import android.view.View;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.adapter.PagerAdapter;
import mcssoft.com.todolist.utility.Resources;

/**
 * Class to Add, Edit, or Delete a To Do item.
 */
public class EditActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = new Resources(this);

        String action = getIntent().getAction();
        Bundle bundle = getIntent().getBundleExtra(res.getString(R.string.bundle_key));
        String type = bundle.getString(res.getString(R.string.list_type_key));

        if(action.equals(res.getString(R.string.list_add_action_key))) {
            // add a new item to a list.
            addListItem(type);
        } else if (action.equals(res.getString(R.string.list_edit_action_key))) {
            // edit a list item.
            editListItem(type);
        } else if(action.equals(res.getString(R.string.list_delete_action_key))) {
            // delete a list item.
            deleteListItem(type);
        }

    }

    private void addListItem(String type) {

        // try https://github.com/Intelliabb/android/tree/master/FragmentHostViewPager
        // uses android.support.v4.app.FragmentTabHost

        if (type.equals(res.getString(R.string.list_type_shopping))) {

            setContentView(R.layout.cv_shopping);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            final ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(res.getString(R.string.toolbar_title_new_shopping));

            Resources res = new Resources(this);
            String[] pageTitles = res.getShoppingItemTypes();
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
            tabLayout.addTab(tabLayout.newTab().setText(pageTitles[0]));
            tabLayout.addTab(tabLayout.newTab().setText(pageTitles[1]));
            tabLayout.addTab(tabLayout.newTab().setText(pageTitles[2]));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            pagerAdapter = new PagerAdapter(getSupportFragmentManager(), this);
            viewPager = (ViewPager) findViewById(R.id.id_pager_container);
            viewPager.setAdapter(pagerAdapter);

            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) { }

                @Override
                public void onTabReselected(TabLayout.Tab tab) { }
            });
        }
    }

    private void editListItem(String type) {
        if(type.equals(res.getString(R.string.list_type_shopping))) {

        } else if (type.equals(res.getString(R.string.list_type_general))) {

        }
    }

    private void deleteListItem(String type) {
        if(type.equals(res.getString(R.string.list_type_shopping))) {

        } else if (type.equals(res.getString(R.string.list_type_general))) {

        }

    }


    private Resources res;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
}
