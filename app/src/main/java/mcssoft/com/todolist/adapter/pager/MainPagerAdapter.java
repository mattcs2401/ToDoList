package mcssoft.com.todolist.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.fragment.GeneralItemList;
import mcssoft.com.todolist.fragment.ShoppingItemList;
import mcssoft.com.todolist.utility.Resources;

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    public MainPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        pageTitles = Resources.getInstance().getStringArray(R.array.main_page_titles);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case SHOPPING:
                return new ShoppingItemList();
            case GENERAL:
                return new GeneralItemList();
        }
        return null;
    }

    @Override
    public String getPageTitle(int position) {
        String title = null;
        switch(position) {
            case SHOPPING:
                title = pageTitles[SHOPPING];
                break;
            case GENERAL:
                title = pageTitles[GENERAL];
                break;
        }
        return title;
    }

    @Override
    public int getCount() {
        return Resources.getInstance().getInteger(R.integer.num_main_pages);
    }

    private String[] pageTitles;
    private static final int SHOPPING = 0;
    private static final int GENERAL = 1;
}
