package mcssoft.com.todolist.model.items;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/**
 * Class that models a list of shopping items.
 */
public class ShoppingItemsList implements Parcelable {

    public ShoppingItemsList(String identifier) {
        this.identifier = identifier;
        shoppingItemsList = new ArrayList<>();
    }

    public void add(ShoppingItemsListItem shoppingItemsListItem) {
        shoppingItemsList.add(shoppingItemsListItem);
    }

    public ArrayList get() {
        return shoppingItemsList;
    }

    public ShoppingItemsListItem get(int position) {
        return shoppingItemsList.get(position);
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public int size() {
        return shoppingItemsList.size();
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Parcel">
    protected ShoppingItemsList(Parcel in) {
        if (in.readByte() == 0x01) {
            shoppingItemsList = new ArrayList<ShoppingItemsListItem>();
            in.readList(shoppingItemsList, ShoppingItemsListItem.class.getClassLoader());
        } else {
            shoppingItemsList = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (shoppingItemsList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(shoppingItemsList);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ShoppingItemsList> CREATOR = new Parcelable.Creator<ShoppingItemsList>() {
        @Override
        public ShoppingItemsList createFromParcel(Parcel in) {
            return new ShoppingItemsList(in);
        }

        @Override
        public ShoppingItemsList[] newArray(int size) {
            return new ShoppingItemsList[size];
        }
    };
    //</editor-fold>

    private String identifier;
    private ArrayList<ShoppingItemsListItem> shoppingItemsList;
}
