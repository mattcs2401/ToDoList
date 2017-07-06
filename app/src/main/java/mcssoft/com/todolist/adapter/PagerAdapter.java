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
        pageTitles = Resources.getInstance().getStringArray(R.array.shopping_item_types);
    }

    @Override
    public Fragment getItem(int position) {
        ShoppingFragment sf = new ShoppingFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Resources.getInstance().getString(R.string.bundle_key), position);
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
        return Resources.getInstance().getInteger(R.integer.num_shopping_pages);
    }

    private String[] pageTitles;
}
