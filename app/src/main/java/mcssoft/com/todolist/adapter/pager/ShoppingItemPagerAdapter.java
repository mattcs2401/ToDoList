package mcssoft.com.todolist.adapter.pager;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.database.Database;
import mcssoft.com.todolist.database.Schema;
import mcssoft.com.todolist.fragment.ShoppingItemFragment;
import mcssoft.com.todolist.model.items.ShoppingItemsList;
import mcssoft.com.todolist.model.items.ShoppingItemsListItem;
import mcssoft.com.todolist.utility.Resources;

public class ShoppingItemPagerAdapter extends FragmentStatePagerAdapter {

    public ShoppingItemPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        pageTitles = Resources.getInstance().getStringArray(R.array.shopping_item_types);
        getAllData();
    }

    @Override
    public Fragment getItem(int position) {
        ShoppingItemFragment shoppingItemFragment = new ShoppingItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Resources.getInstance().getString(R.string.bundle_key), position);
        bundle.putParcelable(Resources.getInstance().getString(R.string.bundle_data_key), getData(position));
        shoppingItemFragment.setArguments(bundle);
        return shoppingItemFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles[position].split(":")[1];
    }

    @Override
    public int getCount() {
        return Resources.getInstance().getInteger(R.integer.num_shopping_item_pages);
    }

    private ShoppingItemsList getData(int pageNo) {
        int size = shoppingItemsListAll.size();
        String refCode = pageTitles[pageNo].split(":")[0];
        ShoppingItemsList dataList = new ShoppingItemsList(refCode);

        for(int ndx = 0; ndx < size; ndx++) {
            // TODO - there is redundant processing here.
            if(refCode.equals(shoppingItemsListAll.get(ndx).getRefCode())) {
                dataList.add(shoppingItemsListAll.get(ndx));
            }
        }
        return dataList;
    }

    /**
     * Utility method to get ref item data from the database and create a master list to be used as
     * the backing (in memory) data for the list item pages to display.
     */
    private void getAllData() {
        shoppingItemsListAll = new ShoppingItemsList("");
        Cursor cursor = Database.getInstance().getAllReferenceItems();
        while(cursor.moveToNext()) {
            ShoppingItemsListItem sili = new ShoppingItemsListItem(
                cursor.getInt(cursor.getColumnIndex(Schema.REF_ITEM_ROWID)),
                cursor.getString(cursor.getColumnIndex(Schema.REF_ITEM_CODE)),
                cursor.getString(cursor.getColumnIndex(Schema.REF_ITEM_DESC)),
                cursor.getString(cursor.getColumnIndex(Schema.REF_ITEM_VALUE)),
                false);

            shoppingItemsListAll.add(sili);
        }
    }

    private String[] pageTitles;
    private ShoppingItemsList shoppingItemsListAll;
}
