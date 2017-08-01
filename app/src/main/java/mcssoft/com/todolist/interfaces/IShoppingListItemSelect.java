package mcssoft.com.todolist.interfaces;

/**
 * Interface between the ShoppingItemFragment and the (implementation) in EditActivity.
 * The idea behind this interface is to capture the selected elements of the backing in memory data
 * behind the shopping list item pages.
 */
public interface IShoppingListItemSelect {

    /**
     * Capture which elements of the shopping item pages backing data is selected.
     * @param pagNo The page number.
     * @param position The (adapter) position on the page.
     * @param isChecked True if the shopping list item selected.
     */
    void iItemSelected(int pagNo, int position, boolean isChecked);
}
