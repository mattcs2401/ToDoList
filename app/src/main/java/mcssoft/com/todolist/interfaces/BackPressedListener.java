package mcssoft.com.todolist.interfaces;

import mcssoft.com.todolist.utility.ToDoEditText;

/**
 * Interface to trap a Back key press from an ToDoEditText (while the softkeyboard is showing) and
 * the implementing class.
 */
public interface BackPressedListener {
    void onImeBack(ToDoEditText toDoEditText);
}