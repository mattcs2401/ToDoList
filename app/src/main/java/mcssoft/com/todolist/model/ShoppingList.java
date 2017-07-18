package mcssoft.com.todolist.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class that models a shopping list.
 */
public class ShoppingList {

    public ShoppingList() {
        shoppingList = new ArrayList<ShoppingListItem>();
    }

    public ShoppingList getShoppingList() {
        return (ShoppingList) shoppingList;
    }

    public ShoppingListItem getShoppingListItem(int position) {
        if(shoppingList.size() > 0) {
            return shoppingList.get(position);
        } else {
            return null;
        }
    }

    public void putShoppingListItem(ShoppingListItem item) {
        shoppingList.add(item);
    }

    public int getSize() {
        return shoppingList.size();
    }

    private List<ShoppingListItem> shoppingList;
}
