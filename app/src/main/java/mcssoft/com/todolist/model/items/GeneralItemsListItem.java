package mcssoft.com.todolist.model.items;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that basically models a General item (within the list of General items).
 */
public class GeneralItemsListItem {

    public GeneralItemsListItem(String nameOrLabel) {
        this.nameOrLabel = nameOrLabel;
        hasElements = false;
        elementList = new ArrayList();
    }

    public GeneralItemsListItem(String nameOrLabel, List elementList) {
        this.nameOrLabel = nameOrLabel;
        if(elementList != null && elementList.size() > 0) {
            this.elementList = elementList;
            hasElements = true;
        } else {
            hasElements = false;
            this.elementList = new ArrayList();
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Item Actions">
    /**
     * Add an element to this General item's list of elements.
     * @param element The element to add.
     */
    public void add(String element) {
        if(elementList != null) {
            elementList.add(element);
        } else {
            elementList = new ArrayList();
            elementList.add(element);
        }
        hasElements = true;
    }
    // TODO - remove(int pos) method ?
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Basic Get/Set">
    public String getNameOrLabel() {
        return nameOrLabel;
    }

    public void setNameOrLabel(String nameOrLabel) {
        this.nameOrLabel = nameOrLabel;
    }

    public boolean getHasElements() {
        return hasElements;
    }

    public void setHasElements(boolean hasElements) {
        this.hasElements = hasElements;
    }

    public List getElementList() {
        return elementList;
    }

    public void setElementList(List elementList) {
        this.elementList = elementList;
    }
    //</editor-fold>

    private String nameOrLabel;   // this General item's name/label.
    private boolean hasElements;  // flag, element/values list contains entries.
    private List elementList;     // list of this General item's elements/values.
}
