package mcssoft.com.todolist.model.summary;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class that models a shopping list.
 */
public class ShoppingSummaryList {

    public ShoppingSummaryList() {
        shoppingSummaryList = new ArrayList<ShoppingSummaryListItem>();
    }

    public ShoppingSummaryList getShoppingSummaryList() {
        return (ShoppingSummaryList) shoppingSummaryList;
    }

    public ShoppingSummaryListItem getShoppingListItem(int position) {
        if(shoppingSummaryList.size() > 0) {
            return shoppingSummaryList.get(position);
        } else {
            return null;
        }
    }

    public void putShoppingListItem(ShoppingSummaryListItem item) {
        shoppingSummaryList.add(item);
    }

    public int getSize() {
        return shoppingSummaryList.size();
    }

    private List<ShoppingSummaryListItem> shoppingSummaryList;
}
