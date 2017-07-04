package mcssoft.com.todolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.List;
import mcssoft.com.todolist.utility.Resources;

public class DatabaseOperations {

    public DatabaseOperations(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * Get the number of records for a table.
     * @param tableName The table to check.
     * @param args Optional aguments.
     * @return The number of records.
     */
    public int getTableRowCount(String tableName, @Nullable String[] args) {
        Cursor cursor = null;
        SQLiteDatabase db = dbHelper.getDatabase();
        try {
            db.beginTransaction();
            switch(tableName) {
                case SchemaConstants.TABLE_SHOPPING_LIST:
                    cursor = db.rawQuery("SELECT " + SchemaConstants.SHOPPING_LIST_ROWID + " FROM " + tableName + ";", args);
                    break;
            }
        } catch (Exception ex) {
            Log.d(context.getClass().getCanonicalName(), ex.getMessage());
        } finally {
            db.endTransaction();
            if(cursor != null) {
                return cursor.getCount();
            } else {
                return 0;
            }
        }
    }

    /**
     * Write default table values to the database. Values are derived from app string resources.
     * @param tableName The database table.
     */
    public void writeTableDefaults(String tableName) {
        Resources resources = new Resources(context);
        String[] itemTypes = resources.getShoppingItemTypes();
        List<String[]> listing = resources.getAllDefaults();

        SQLiteDatabase db = dbHelper.getDatabase();
        ContentValues cv = new ContentValues();

        String[] general = listing.get(0);

        for (String val : general) {
            try {
                db.beginTransaction();
                cv.put(SchemaConstants.SHOPPING_LIST_TYPE, itemTypes[0]);
                cv.put(SchemaConstants.SHOPPING_LIST_VALUE, val);
                cv.put(SchemaConstants.SHOPPING_LIST_VALUE_SEL, "N");
                db.insert(tableName, null, cv);
                db.setTransactionSuccessful();
            } catch(SQLException ex){
                Log.d(context.getClass().getCanonicalName(), ex.getMessage());
            } finally{
                db.endTransaction();
            }
        }

        general = listing.get(1);

        for (String val : general) {
            try {
                db.beginTransaction();
                cv.put(SchemaConstants.SHOPPING_LIST_TYPE, itemTypes[1]);
                cv.put(SchemaConstants.SHOPPING_LIST_VALUE, val);
                db.insert(tableName, null, cv);
                db.setTransactionSuccessful();
            } catch(SQLException ex){
                Log.d(context.getClass().getCanonicalName(), ex.getMessage());
            } finally{
                db.endTransaction();
            }
        }

        general = listing.get(2);

        for (String val : general) {
            try {
                db.beginTransaction();
                cv.put(SchemaConstants.SHOPPING_LIST_TYPE, itemTypes[2]);
                cv.put(SchemaConstants.SHOPPING_LIST_VALUE, val);
                db.insert(tableName, null, cv);
                db.setTransactionSuccessful();
            } catch(SQLException ex){
                Log.d(context.getClass().getCanonicalName(), ex.getMessage());
            } finally{
                db.endTransaction();
            }
        }

        dbHelper.close();
    }

    private Context context;
    private DatabaseHelper dbHelper;
}
