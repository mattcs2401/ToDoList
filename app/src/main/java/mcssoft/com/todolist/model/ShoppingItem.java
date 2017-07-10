package mcssoft.com.todolist.model;

public class ShoppingItem {

    public ShoppingItem(String itemType, String itemValue, String date) {
        this.itemType = itemType;
        this.itemValue = itemValue;
        this.date = date;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String itemType;
    private String itemValue;
    private String date;

}
