package mcssoft.com.todolist.interfaces;

/**
 * Interface between the ListSelectFragment and the calling activity.
 */
public interface IListSelect {

    /**
     * Get the selected (checked) radio button identifier.
     * @param value The radio button id.
     */
    void iSelected(int value);
}
