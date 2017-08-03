package mcssoft.com.todolist.adapter.pager;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.database.Database;
import mcssoft.com.todolist.database.Schema;
import mcssoft.com.todolist.fragment.ShoppingItemFragment;
import mcssoft.com.todolist.model.items.ShoppingItemsList;
import mcssoft.com.todolist.model.items.ShoppingItemsListItem;
import mcssoft.com.todolist.utility.Resources;

public class ShoppingItemPagerAdapter extends FragmentStatePagerAdapter {

    // TODO - need something that says this is for a new shopping list, or editing one.

    public ShoppingItemPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        shoppingItemsList = new ShoppingItemsList(null);
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

    /**
     * Create/update the current shopping list.
     * @param pageNo The page number of the shopping itens page.
     * @param position The page's adapter position.
     * @param isChecked True if the shopping list item selected.
     */
    public void buildShoppingList(int pageNo, int position, boolean isChecked) {
        // get the 'equivalent' shopping list item from the main list.
        ShoppingItemsListItem temp = null;
        ArrayList<ShoppingItemsListItem> sil = getData(pageNo).get();

        for(ShoppingItemsListItem sili : sil) {
        // TODO - lot of redundant processing if selected items have a high adapter position number.
            temp = sil.get(position);
            if(temp.getRefId() == sili.getRefId()) {
                if(isChecked) {
                    shoppingItemsList.add(sili);
                    break;
                } else {
                    shoppingItemsList.remove(sili);
                    break;
                }
            }
        }
    }

    /**
     * Get the current shopping list.
     * @return The shopping list.
     */
    public ShoppingItemsList getShoppingList() {
        return shoppingItemsList;
    }

    /**
     * Get the identifiers of the selected shopping items.
     * @return The identifiers.
     */
    public int[] getShoppingListRefIds() {
        int size = shoppingItemsList.size();
        int[] refIds = new int[size];
        for(int ndx = 0; ndx < size; ndx++) {
            refIds[ndx] = shoppingItemsList.get(ndx).getRefId();
        }
        return refIds;
    }

    public void clearShoppingList() {
        shoppingItemsList = null;
        shoppingItemsList = new ShoppingItemsList(null);
    }

    /**
     * Get the backing data for a shopping items page.
     * @param pageNo The page number.
     * @return The list of items.
     */
    private ShoppingItemsList getData(int pageNo) {
        return shoppingItemsListAll.get(pageTitles[pageNo].split(":")[0]);
    }

    /**
     * Get ref item data from the database and create a master list to be used as the backing
     * (in memory) data for the shopping item pages to display.
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
    private ShoppingItemsList shoppingItemsList;      // the current list of shopping items.
    private ShoppingItemsList shoppingItemsListAll;   // the 'master' list of all possible shopping
                                                      // list items.
}
