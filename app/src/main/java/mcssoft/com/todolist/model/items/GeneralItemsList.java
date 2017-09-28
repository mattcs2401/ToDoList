package mcssoft.com.todolist.model.items;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Class that models a list of General items.
 */
public class GeneralItemsList implements Parcelable {

    /**
     * Create a General item list.
     * @param identifier An identifier for the shopping list.
     */
    public GeneralItemsList(@Nullable String identifier) {
        if(identifier == null) {
            this.identifier = "";
        } else {
            this.identifier = identifier;
        }
        generalItemsList = new ArrayList<>();
    }

    //<editor-fold defaultstate="collapsed" desc="Region: List actions">
    /**
     * Add a General item to the General list.
     * @param gili The General list item.
     */
    public void add(GeneralItemsListItem gili) {
        generalItemsList.add(gili);
    }

    public void remove(GeneralItemsListItem gili) {
        generalItemsList.remove(gili);
    }

    /**
     * Get the current list of General items.
     * @return The current General list.
     */
    public ArrayList get() {
        return generalItemsList;
    }

    /**
     * Get a General list item at the current position in the General list.
     * @param position The General list position to get the General list item from.
     * @return The General list item.
     */
    public GeneralItemsListItem get(int position) {
        return generalItemsList.get(position);
    }

//    /**
//     * Create a shopping list (as a sub-list) based on the shopping list item code.
//     * @param refCode The shopping list item code.
//     * @return
//     */
//    public GeneralItemsList get(String refCode) {
//        // TO DO - there is redundant processing here.
//        GeneralItemsList gil = new GeneralItemsList(refCode);
//        for(GeneralItemsListItem gili : generalItemsList) {
//            if(gili.getRefCode().equals(refCode)) {
//                gil.add(gili);
//            }
//        }
//        return gil;
//    }

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
        return generalItemsList.size();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Parcel">
    protected GeneralItemsList(Parcel in) {
        if (in.readByte() == 0x01) {
            generalItemsList = new ArrayList<GeneralItemsListItem>();
            in.readList(generalItemsList, GeneralItemsListItem.class.getClassLoader());
        } else {
            generalItemsList = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (generalItemsList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(generalItemsList);
        }
    }

    @SuppressWarnings("unused")
    public static final Creator<GeneralItemsList> CREATOR = new Creator<GeneralItemsList>() {
        @Override
        public GeneralItemsList createFromParcel(Parcel in) {
            return new GeneralItemsList(in);
        }

        @Override
        public GeneralItemsList[] newArray(int size) {
            return new GeneralItemsList[size];
        }
    };
    //</editor-fold>

    private String identifier;                                // General list identifier (nullable).
    private ArrayList<GeneralItemsListItem> generalItemsList; // the current General list.
}
