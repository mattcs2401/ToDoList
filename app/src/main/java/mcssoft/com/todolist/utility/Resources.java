package mcssoft.com.todolist.utility;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import mcssoft.com.todolist.R;

public class Resources {

    private Resources(Context context) {
        this.context = context;
    }

    /**
     * Get (initialise) an instance of MeetingResources.
     * @param context The current context.
     * @return An instance of MeetingResources.
     */
    public static synchronized void setInstance(Context context) {
        instance = new Resources(context);
    }

    /**
     * Get the current MeetingResources instance.
     * @return The current MeetingResources instance.
     */
    public static synchronized Resources getInstance() {
        return instance;
    }

    /**
     * Check if this instance exists.
     * @return True if instance exists, else false.
     */
    public static boolean instanceExists() {
        return instance != null ? true : false;
    }

    /**
     * Get an integer resource.
     * @param resId The resource id.
     * @return The integer resource.
     */
    public int getInteger(int resId) {
        return context.getResources().getInteger(resId);
    }

    /**
     * Get a boolean resource.
     * @param resId The resource id.
     * @return The boolean resource.
     */
    public boolean getBoolean(int resId) {
        return context.getResources().getBoolean(resId);
    }

    /**
     * Get a String resource.
     * @param resId The resource id.
     * @return The String resource.
     */
    public String getString(int resId) {
        return context.getResources().getString(resId);
    }

    public String[] getStringArray(int resId) {
        return context.getResources().getStringArray(resId);
    }

    /**
     * Get app default string resources to populate initial shopping list values.
     * @return [0]-general values, [1]-fruit and veg values, [2]-meat and fish values.
     */
    public List<String[]> getAllDefaults() {
        List theList = new ArrayList();
        String[] sa = {};
        sa = getStringArray(R.array.shopping_items_general_default);
        theList.add(sa);
        sa = getStringArray(R.array.shopping_items_fruitveg_default);
        theList.add(sa);
        sa = getStringArray(R.array.shopping_items_meatfish_default);
        theList.add(sa);
        return theList;
    }

    /**
     * Ensure instance values are made NULL.
     */
    public void destroy() {
        context = null;
        instance = null;
    }

    private Context context;
    private static volatile Resources instance = null;

}
