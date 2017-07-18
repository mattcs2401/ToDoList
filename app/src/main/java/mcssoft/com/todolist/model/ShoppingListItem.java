package mcssoft.com.todolist.model;

/**
 * Utility class to model a shopping list item.
 * Note: The SLIST_ITEM table is just a linking table between SLIST and REF_ITEM. The values in this
 *       class were originally sourced from REF_ITEM.
 */
public class ShoppingListItem {

    public ShoppingListItem(boolean isHeader, String description) {
        this.isHeader = isHeader;
        this.description = description;
    }

    public ShoppingListItem(String code, String description, String value) {
        this.code = code;
        this.description = description;
        this.value = value;
        this.isHeader = false;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private boolean isHeader;     // if set then essentially an empty item (used for a header row).
    private String code;          //
    private String description;   //
    private String value;         //

}
