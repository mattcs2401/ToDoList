package mcssoft.com.todolist.adapter.pager;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.database.Database;
import mcssoft.com.todolist.database.Schema;
import mcssoft.com.todolist.fragment.ShoppingItemFragment;
import mcssoft.com.todolist.model.items.ShoppingItemsList;
import mcssoft.com.todolist.model.items.ShoppingItemsListItem;
import mcssoft.com.todolist.utility.Resources;

public class ShoppingItemPagerAdapter extends FragmentStatePagerAdapter {

    // TODO - need something that says this is for a new shopping list, or editing a list.

    public ShoppingItemPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        pageTitles = Resources.getInstance().getStringArray(R.array.shopping_item_types);
    }

    @Override
    public Fragment getItem(int position) {
        ShoppingItemFragment shoppingItemFragment = new ShoppingItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Resources.getInstance().getString(R.string.bundle_pagecode_key), pageTitles[position].split(":")[0]);
        shoppingItemFragment.setArguments(bundle);
        return shoppingItemFragment;
    }

    @Override
    public String getPageTitle(int position) {
        return pageTitles[position].split(":")[1];
    }

    @Override
    public int getCount() {
        return Resources.getInstance().getInteger(R.integer.num_shopping_item_pages);
    }

    private String[] pageTitles;
}
