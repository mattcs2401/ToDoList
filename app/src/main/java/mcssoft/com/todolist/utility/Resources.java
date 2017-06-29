package mcssoft.com.todolist.utility;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import mcssoft.com.todolist.R;

public class Resources {

    public Resources(Context context) {
        this.context = context;
    }

    /**
     * Get app default string resources to populate initial shopping list values.
     * @return [0] - general values, [1] - fruit and veg values, [2] - meat and fish values.
     */
    public List<String[]> getAllDefaults() {
        List theList = new ArrayList();
        String[] l1 = context.getResources().getStringArray(R.array.shopping_items_general_default);
        String[] l2 = context.getResources().getStringArray(R.array.shopping_items_fruitveg_default);
        String[] l3 = context.getResources().getStringArray(R.array.shopping_items_meatfish_default);
        theList.add(l1);
        theList.add(l2);
        theList.add(l3);
        return theList;
    }

    private Context context;
}
