package mcssoft.com.todolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.List;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.utility.DatabaseLoadTask;

public class DatabaseOperations {

    public DatabaseOperations(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * Check that a table has records.
     * @param tableName The table to check.
     * @return True if records exist, else false.
     */
    public boolean checkTable(String tableName) {

        return false;
    }

    /**
     * Write default table values to the database.
     * @param tableName The database table.
     */
    public void writeTableDefaults(String tableName) {
        // TODO - this needs work.
        List<String[]> listing = getDefaultsFromResources();

        SQLiteDatabase db = dbHelper.getDatabase();
        ContentValues cv = new ContentValues();

        String[] general = listing.get(0);

        try {
            for(String val : general) {
                db.beginTransaction();
                cv.put(SchemaConstants.SHOPPING_LIST_TYPE, SchemaConstants.SHOPPING_LIST_TYPE_GEN);
                cv.put(SchemaConstants.SHOPPING_LIST_VALUE, val);
                db.insert(tableName, null, cv);
                db.setTransactionSuccessful();
            }
        } catch (SQLException ex) {
            Log.d(context.getClass().getCanonicalName(), ex.getMessage());
        } finally {
            db.endTransaction();
        }

        general = listing.get(1);

        try {
            for(String val : general) {
                db.beginTransaction();
                cv.put(SchemaConstants.SHOPPING_LIST_TYPE, SchemaConstants.SHOPPING_LIST_TYPE_FANDV);
                cv.put(SchemaConstants.SHOPPING_LIST_VALUE, val);
                db.insert(tableName, null, cv);
                db.setTransactionSuccessful();
            }
        } catch (SQLException ex) {
            Log.d(context.getClass().getCanonicalName(), ex.getMessage());
        } finally {
            db.endTransaction();
        }

        general = listing.get(2);

        try {
            for(String val : general) {
                db.beginTransaction();
                cv.put(SchemaConstants.SHOPPING_LIST_TYPE, SchemaConstants.SHOPPING_LIST_TYPE_MANDF);
                cv.put(SchemaConstants.SHOPPING_LIST_VALUE, val);
                db.insert(tableName, null, cv);
                db.setTransactionSuccessful();
            }
        } catch (SQLException ex) {
            Log.d(context.getClass().getCanonicalName(), ex.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    private List<String[]> getDefaultsFromResources() {
        List theList = new ArrayList();
        String[] l1 = context.getResources().getStringArray(R.array.shopping_items_general_default);
        String[] l2 = context.getResources().getStringArray(R.array.shopping_items_fruitveg_default);
        String[] l3 = context.getResources().getStringArray(R.array.shopping_items_meatfish_default);
        theList.add(l1);
        theList.add(l2);
        theList.add(l3);
        return theList;
    }

    private Context context;
    private DatabaseHelper dbHelper;
}
