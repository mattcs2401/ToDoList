package mcssoft.com.todolist.model.items;

/**
 * Class that basically models a REF_ITEM.
 */
public class ShoppingItemsListItem {

    public ShoppingItemsListItem(int refId, String refCode, String refDesc, String refValue, String refSelect) {
        this.refId = refId;
        this.refCode = refCode;
        this.refDesc = refDesc;
        this.refValue = refValue;
        this.refSelect = refSelect;
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

    public String getRefSelect() {
        return refSelect;
    }

    public void setRefSelect(String refSelect) {
        this.refSelect = refSelect;
    }

    private int refId;
    private String refCode;
    private String refDesc;
    private String refValue;
    private String refSelect;

}
//    public static final String REF_ITEM_CODE    = "REF_CODE";    // e.g. GENRL, FANDV etc.
//    public static final String REF_ITEM_DESC    = "REF_DESC";    // e.g. GENRL, Fruit&Veg etc
//    public static final String REF_ITEM_VALUE   = "REF_VALUE";   // e.g. Bread
//    public static final String REF_ITEM_VAL_SEL = "REF_SELECT";  // Y or N, item selected in UI
