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
        return getRecords(Schema.TABLE_SLIST, null, null, null);
    }

    public Cursor getAllGeneral() {
        // TBA.
        return null;
    }

    /**
     * Uncheck previously selected reference items.
     * @return A count of the items unchecked.
     */
    public int unCheckReferenceItems() {
        Cursor cursor = getCheckedReferenceItems();
        while(cursor.moveToNext()) {

        }
        return cursor.getCount();
    }

    /**
     * Get a record from the reference items.
     * @param dbRowId The row id.
     * @return The reference item record.
     */
    public Cursor getReferenceItem(int dbRowId) {
        return getRecords(Schema.TABLE_REF_ITEM, null, Schema.WHERE_REF_ITEM_ROWID, new String[] {Integer.toString(dbRowId)});
    }

    /**
     * Set the the REF_ITEM_VAL_SEL column as N ot Y (i.e. check or uncheck);
     * @param dbRowId The row id of the SL item.
     * @param check True - set Y, else set N.
     * @return 1 if the row updated *(checked or unchecked).
     */
    public int setCheckReferenceItem(int dbRowId, boolean check) {
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

    /**
     * Get the reference items that have been checked (REF_ITEM.REF_ITEM_SEL_VAL="Y").
     * @return A cursor over the checked items.
     */
    public Cursor getCheckedReferenceItems() {
        return getRecords(Schema.TABLE_REF_ITEM, null, Schema.WHERE_REF_ITEM_SEL, new String[] {"Y"});
    }

    public Cursor getCheckedReferenceItems(ShoppingFragment.PageType pageType) {

        String[] selArgs = null;

        switch(pageType) {
            case GENRL:
                selArgs = new String[]{Resources.getInstance().getStringArray(R.array.shopping_item_types)[0].split(":")[0]};
                break;
            case FANDV:
                selArgs = new String[]{Resources.getInstance().getStringArray(R.array.shopping_item_types)[1].split(":")[0]};
                break;
            case MANDF:
                selArgs = new String[]{Resources.getInstance().getStringArray(R.array.shopping_item_types)[2].split(":")[0]};
                break;
        }
        return getRecords(Schema.TABLE_REF_ITEM, null, Schema.WHERE_REF_ITEM_CODE, selArgs);
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
        String code;
        String desc;
        String[] itemTypes = Resources.getInstance().getStringArray(R.array.shopping_item_types);
        List<String[]> allDefaults = mcssoft.com.todolist.utility.Resources.getInstance().getAllDefaults();

        SQLiteDatabase db = dbHelper.getDatabase();
        ContentValues cv = new ContentValues();

        String[] general = allDefaults.get(0);
        code = itemTypes[0].split(":")[0];
        desc = itemTypes[0].split(":")[1];

        for (String val : general) {

            try {
                db.beginTransaction();
                cv.put(Schema.REF_ITEM_CODE, code);
                cv.put(Schema.REF_ITEM_DESC, desc);
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

        general = allDefaults.get(1);
        code = itemTypes[1].split(":")[0];
        desc = itemTypes[1].split(":")[1];

        for (String val : general) {

            try {
                db.beginTransaction();
                cv.put(Schema.REF_ITEM_CODE, code);
                cv.put(Schema.REF_ITEM_DESC, desc);
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

        general = allDefaults.get(2);
        code = itemTypes[2].split(":")[0];
        desc = itemTypes[2].split(":")[1];

        for (String val : general) {
            try {
                db.beginTransaction();
                cv.put(Schema.REF_ITEM_CODE, code);
                cv.put(Schema.REF_ITEM_DESC, desc);
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
        // TODO - item type OTHER not done.
    }

    public void destroy() {
        dbHelper = null;
        context = null;
    }

    private Cursor getRecords(String tableName, @Nullable String[] projection, @Nullable String whereClause, @Nullable String[] selArgs) {
        if(projection == null) {
            projection = getProjection(tableName);
        }
        if(whereClause == null) {
            // this will get all records.
            selArgs = null;
        }
        SQLiteDatabase db = dbHelper.getDatabase();
        db.beginTransaction();
        Cursor cursor = db.query(tableName, projection, whereClause, selArgs, null, null, null);
        db.endTransaction();
        return cursor;
    }

//    private int setRecords() {
//        Cursor cursor = getRecords(Schema.TABLE_REF_ITEM,)
//
//        return 0;
//    }

    private String[] getProjection(String tableName) {
        String[] projection = null;
        switch (tableName) {
            case Schema.TABLE_SLIST:
                projection = dbHelper.getProjection(DatabaseHelper.Projection.SListSchema);
                break;
            case Schema.TABLE_REF_ITEM:
                projection = dbHelper.getProjection(DatabaseHelper.Projection.RefItemSchema);
                break;
        }
        return  projection;
    }

    private Context context;
    private static DatabaseHelper dbHelper;
    private static volatile Database instance = null;
}
