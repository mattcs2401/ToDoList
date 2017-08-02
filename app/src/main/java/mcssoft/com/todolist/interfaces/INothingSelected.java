package mcssoft.com.todolist.interfaces;

/**
 * Interface between the NothingSelectedFragment and the calling activity.
 */
public interface INothingSelected {

    /**
     * Get the user's choice to continue with a shopping list.
     * @param value True to continue creating a shopping list.
     */
    void iNoSelect(boolean value);
}
