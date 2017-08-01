package mcssoft.com.todolist.model.items;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Class that models a list of shopping items (the shopping list).
 */
public class ShoppingItemsList implements Parcelable {

    /**
     * Create a shopping list.
     * @param identifier An identifier for the shopping list.
     */
    public ShoppingItemsList(@Nullable String identifier) {
        if(identifier == null) {
            this.identifier = "";
        } else {
            this.identifier = identifier;
        }
        shoppingItemsList = new ArrayList<>();
    }

    //<editor-fold defaultstate="collapsed" desc="Region: List actions">
    /**
     * Add a shopping list item to the shopping list.
     * @param sili The shopping list item.
     */
    public void add(ShoppingItemsListItem sili) {
        shoppingItemsList.add(sili);
    }

    public void remove(ShoppingItemsListItem sili) {
        shoppingItemsList.remove(sili);
    }

    /**
     * Get the current shopping list.
     * @return The current shopping list.
     */
    public ArrayList get() {
        return shoppingItemsList;
    }

    /**
     * Get a shopping list item at the current position in the shopping list.
     * @param position The shopping list position to get the shopping list item from.
     * @return The shopping list item.
     */
    public ShoppingItemsListItem get(int position) {
        return shoppingItemsList.get(position);
    }

    /**
     * Create a shopping list (as a sub-list) based on the shopping list item code.
     * @param refCode The shopping list item code.
     * @return
     */
    public ShoppingItemsList get(String refCode) {
        // TODO - there is redundant processing here.
        ShoppingItemsList sil = new ShoppingItemsList(refCode);
        for(ShoppingItemsListItem sili : shoppingItemsList) {
            if(sili.getRefCode().equals(refCode)) {
                sil.add(sili);
            }
        }
        return sil;
    }

    /**
     * Get the current identifier for the shopping list.
     * @return
     */
    public String getIdentifier() {
        return this.identifier;
    }

    /**
     * Set (or change) the identifier for the shopping list.
     * @param identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Get the size of the current shopping list.
     */
    public int size() {
        return shoppingItemsList.size();
    }
    //</editor-fold>

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

    private String identifier;                                  // shopping list identifier.
    private ArrayList<ShoppingItemsListItem> shoppingItemsList; // the current shopping list.
}
