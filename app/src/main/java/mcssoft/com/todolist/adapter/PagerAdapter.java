package mcssoft.com.todolist.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.fragment.ShoppingFragment;
import mcssoft.com.todolist.utility.Resources;

public class PagerAdapter extends FragmentStatePagerAdapter {

    public PagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        resources = new Resources(context);
        pageTitles = resources.getShoppingItemTypes();
    }

    @Override
    public Fragment getItem(int position) {
        ShoppingFragment sf = new ShoppingFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(resources.getString(R.string.bundle_key), position);
        sf.setArguments(bundle);
        return sf;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch(position) {
            case 0:
                title = pageTitles[0];
                break;
            case 1:
                title = pageTitles[1];
                break;
            case 2:
                title = pageTitles[2];
                break;
        }
        return title;
    }

    @Override
    public int getCount() {
        return resources.getInteger(R.integer.num_shopping_pages);
    }

    private String[] pageTitles;
    private Resources resources;
}
