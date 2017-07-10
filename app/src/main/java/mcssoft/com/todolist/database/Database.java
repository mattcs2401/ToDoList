package mcssoft.com.todolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.List;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.fragment.ShoppingFragment;
import mcssoft.com.todolist.utility.Resources;

public class Database {

    private Database(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public static synchronized Database getInstance(Context context) {
        if(!instanceExists()) {
            instance = new Database(context);
            dbHelper = new DatabaseHelper(context);
        }
        return instance;
    }

    public static synchronized Database getInstance() {
        return instance;
    }

    public static boolean instanceExists() {
        return instance != null ? true : false;
    }

    public Cursor getAllShopping() {
        return getRecords(Schema.TABLE_SL, null, null);
    }

    public Cursor getAllGeneral() {
        // TBA.
        return null;
    }

    public Cursor getShoppingItem(int dbRowId) {
        return getRecords(Schema.TABLE_REF_ITEM, Schema.WHERE_REF_ITEM_ROWID, new String[] {Integer.toString(dbRowId)});
    }

    /**
     * Set the the REF_ITEM_VAL_SEL column as N ot Y (i.e. check or uncheck);
     * @param dbRowId The row id of the SL item.
     * @param check True - set Y, else set N.
     * @return 1 if the row updated *(checked or unchecked).
     */
    public int setCheckShoppingItem(int dbRowId, boolean check) {
        int count = -1;
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getDatabase();

        try {
            db.beginTransaction();
            if(check) {
                cv.put(Schema.REF_ITEM_VAL_SEL, "Y");
            } else {
                cv.put(Schema.REF_ITEM_VAL_SEL, "N");
            }
            count = db.update(Schema.TABLE_REF_ITEM, cv, Schema.WHERE_REF_ITEM_ROWID, new String[] {Integer.toString(dbRowId)});
            db.setTransactionSuccessful();
        } catch(Exception ex) {
            Log.d(context.getClass().getCanonicalName(), ex.getMessage());
        } finally {
            if(db != null) {
                db.endTransaction();
            }
        }
        return count;
    }

    public Cursor getShoppingItems(ShoppingFragment.PageType pageType) {
        String[] selArgs = null;

        switch(pageType) {
            case General:
                selArgs = new String[]{Resources.getInstance().getStringArray(R.array.shopping_item_types)[0]};
                break;
            case Fruit_and_Veg:
                selArgs = new String[]{Resources.getInstance().getStringArray(R.array.shopping_item_types)[1]};
                break;
            case Meat_and_Fish:
                selArgs = new String[]{Resources.getInstance().getStringArray(R.array.shopping_item_types)[2]};
                break;
        }
        return getRecords(Schema.TABLE_REF_ITEM, Schema.WHERE_REF_ITEM_TYPE, selArgs);
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
                case Schema.TABLE_REF_ITEM:
                    cursor = db.rawQuery("SELECT " + Schema.REF_ITEM_ROWID + " FROM " + tableName + ";", args);
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
        String[] itemTypes = Resources.getInstance().getStringArray(R.array.shopping_item_types);
        List<String[]> listing = mcssoft.com.todolist.utility.Resources.getInstance().getAllDefaults();

        SQLiteDatabase db = dbHelper.getDatabase();
        ContentValues cv = new ContentValues();

        String[] general = listing.get(0);

        for (String val : general) {
            try {
                db.beginTransaction();
                cv.put(Schema.REF_ITEM_TYPE, itemTypes[0]);
                cv.put(Schema.REF_ITEM_VALUE, val);
                cv.put(Schema.REF_ITEM_VAL_SEL, "N");
                db.insert(tableName, null, cv);
                db.setTransactionSuccessful();
            } catch(SQLException ex){
                Log.d(context.getClass().getCanonicalName(), ex.getMessage());
            } finally{
                if(db != null) {
                    db.endTransaction();
                }
            }
        }

        general = listing.get(1);

        for (String val : general) {
            try {
                db.beginTransaction();
                cv.put(Schema.REF_ITEM_TYPE, itemTypes[1]);
                cv.put(Schema.REF_ITEM_VALUE, val);
                cv.put(Schema.REF_ITEM_VAL_SEL, "N");
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
                cv.put(Schema.REF_ITEM_TYPE, itemTypes[2]);
                cv.put(Schema.REF_ITEM_VALUE, val);
                cv.put(Schema.REF_ITEM_VAL_SEL, "N");
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

    public void destroy() {
        dbHelper = null;
        context = null;
    }

    private Cursor getRecords(String tableName, @Nullable String whereClause, @Nullable String[] selArgs) {
        if(whereClause == null) {
            // this will get all records.
            selArgs = null;
        }
        SQLiteDatabase db = dbHelper.getDatabase();
        db.beginTransaction();
        Cursor cursor = db.query(tableName, getProjection(tableName), whereClause, selArgs, null, null, null);
        db.endTransaction();
        return cursor;
    }

    private String[] getProjection(String tableName) {
        String[] projection = null;
        switch (tableName) {
            case Schema.TABLE_SL:
                projection = dbHelper.getProjection(DatabaseHelper.Projection.SLSchema);
                break;
            case Schema.TABLE_REF_ITEM:
                projection = dbHelper.getProjection(DatabaseHelper.Projection.SLItemSchema);
                break;
        }
        return  projection;
    }

    private Context context;
    private static DatabaseHelper dbHelper;
    private static volatile Database instance = null;
}
