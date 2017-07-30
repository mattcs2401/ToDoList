package mcssoft.com.todolist.model.items;

/**
 * Class that basically models a REF_ITEM.
 */
public class ShoppingItemsListItem {

    public ShoppingItemsListItem(int refId, String refCode, String refDesc, String refValue, boolean selected) {
        this.refId = refId;
        this.refCode = refCode;
        this.refDesc = refDesc;
        this.refValue = refValue;
        this.selected = selected;
    }

    public int getRefId() {
        return refId;
    }

    public void setRefId(int refId) {
        this.refId = refId;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public String getRefDesc() {
        return refDesc;
    }

    public void setRefDesc(String refDesc) {
        this.refDesc = refDesc;
    }

    public String getRefValue() {
        return refValue;
    }

    public void setRefValue(String refValue) {
        this.refValue = refValue;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    private int refId;       // reference item identifier.
    private String refCode;  // reference item code.
    private String refDesc;  // reference item description.
    private String refValue; // reference item value.
    private boolean selected;// reference item selected.

}
//    public static final String REF_ITEM_CODE    = "REF_CODE";    // e.g. GENRL, FANDV etc.
//    public static final String REF_ITEM_DESC    = "REF_DESC";    // e.g. GENRL, Fruit&Veg etc
//    public static final String REF_ITEM_VALUE   = "REF_VALUE";   // e.g. Bread
