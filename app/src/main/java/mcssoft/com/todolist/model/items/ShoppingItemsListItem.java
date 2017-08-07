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

    private int refId;            // reference item identifier (database row id).
    private String refCode;       // reference item code.
    private String refDesc;       // reference item description.
    private String refValue;      // reference item value.
    private String refSelect;     // reference item select.
}
