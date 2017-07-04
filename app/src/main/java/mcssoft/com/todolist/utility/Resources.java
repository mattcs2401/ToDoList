package mcssoft.com.todolist.utility;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import mcssoft.com.todolist.R;

public class Resources {

    public Resources(Context context) {
        this.context = context;
    }

    public String getString(int resId) {
        return context.getResources().getString(resId);
    }

    public String[] getShoppingItemTypes() {
        return context.getResources().getStringArray(R.array.shopping_item_types);
    }

    /**
     * Get app default string resources to populate initial shopping list values.
     * @return [0]-general values, [1]-fruit and veg values, [2]-meat and fish values.
     */
    public List<String[]> getAllDefaults() {
        List theList = new ArrayList();
        String[] sa = {};
        sa = context.getResources().getStringArray(R.array.shopping_items_general_default);
        theList.add(sa);
        sa = context.getResources().getStringArray(R.array.shopping_items_fruitveg_default);
        theList.add(sa);
        sa = context.getResources().getStringArray(R.array.shopping_items_meatfish_default);
        theList.add(sa);
        return theList;
    }

    private Context context;
}
