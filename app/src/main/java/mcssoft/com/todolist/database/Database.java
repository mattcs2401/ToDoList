package mcssoft.com.todolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.utility.Resources;

public class Database {

    //<editor-fold defaultstate="collapsed" desc="Region: Instance">
    private Database(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * Set the database singleton instance. This must be called before getInstance().
     * @param context The current context.
     */
    public static synchronized void setInstance(Context context) {
        instance = new Database(context);
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * Get the current database instance. Must be called in conjunction with any other methode,
     * e.g. Database.getInstance().methodName();
     * @return The curreent database instance.
     */
    public static synchronized Database getInstance() {
        return instance;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Shopping">
    /**
     * Get all the items associated with a shopping list.
     * @param dbRowId The database row id reference of the shopping list.
     * @return A cursor over all the items.
     */
    public Cursor getShopping(int dbRowId) {
        return getRecordsRaw(Schema.QUERY_RAW_GET_SHOPPING, new String[] {Integer.toString(dbRowId)});
    }

    /**
     * Get all shopping lists (less the items that comprise an individual shopping list).
     * @return A cursor over the shopping lists.
     */
    public Cursor getAllShopping() {
        return getRecords(Schema.TABLE_SLIST, null, Schema.WHERE_SLIST_ARCHV, new String[] {"N"});
    }

    /**
     * Basically get metadata for a shopping list.
     * @return [0]-shopping list db row id, [1]-count of associated items.
     */
    public ArrayList<int[]> getShoppingMetaData() {
        ArrayList<int[]> idsList = new ArrayList<>();
        // get the shopping list(s).
        Cursor ids = getRecords(Schema.TABLE_SLIST, new String[] {Schema.SLIST_ROWID},
                Schema.WHERE_SLIST_ARCHV, new String[] {"N"});
        // get the count of associated shopping list items.
        while(ids.moveToNext()) {
            int rowId = ids.getInt(ids.getColumnIndexOrThrow(Schema.SLIST_ROWID));
            Cursor cursor = getShopping(rowId);
            int count = cursor.getCount();
            idsList.add(new int[] {rowId, count});
        }
        return idsList;
    }

    /**
     * Get all the items of type General.
     * @return A cursor over the items.
     */
    public Cursor getAllGeneral() {
        return getRecords(Schema.TABLE_GENERAL, null, Schema.WHERE_GENERAL_ARCHV, new String[] {"N"});
    }

    /**
     * Uncheck previously selected reference items.
     * @return A count of the items unchecked.
     */
    public int unCheckReferenceItems() {
        int rowId;
        Cursor cursor = getCheckedReferenceItems();
        while(cursor.moveToNext()) {
            rowId = cursor.getInt(cursor.getColumnIndex(Schema.REF_ITEM_ROWID));
            setCheckReferenceItem(rowId, false);
        }
        return cursor.getCount();
    }

    /**
     * Get all checked reference items.
     * @return A cursor over the checked items.
     */
    public Cursor getCheckedReferenceItems() {
        return getRecords(Schema.TABLE_REF_ITEM, null, Schema.WHERE_REF_ITEM_SEL, new String[] {"Y"});
    }

    /**
     * Set the the REF_ITEM_SEL column as N ot Y (i.e. checked or unchecked);
     * @param dbRowId The row id of the reference item.
     * @param check True set Y, else set N.
     * @return 1 if the row updated (checked or unchecked).
     */
    public int setCheckReferenceItem(int dbRowId, boolean check) {
        int count = -1;
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getDatabase();

        try {
            db.beginTransaction();
            if(check) {
                cv.put(Schema.REF_ITEM_SEL, "Y");
            } else {
                cv.put(Schema.REF_ITEM_SEL, "N");
            }
            count = db.update(Schema.TABLE_REF_ITEM, cv, Schema.WHERE_REF_ITEM_ROWID,
                    new String[] {Integer.toString(dbRowId), "N"});

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

    public Cursor getAllReferenceItems(String pageCode) {
        return getRecords(Schema.TABLE_REF_ITEM, null, Schema.WHERE_REF_ITEM_CODE, new String[] {pageCode, "N"});
    }

    /**
     * Write shopping list item values ino a new shopping list.
     * @param colVals [0]-list id, [1]-list date, [2]-list name.
     * @return The rowId of the newly inserted row, or -1,
     */
    public long createShoppingList(List<String> colVals) {
        long rowId = -1;
        SQLiteDatabase db = dbHelper.getDatabase();
        ContentValues cv = new ContentValues();

        // create the shopping list.
        try {
            db.beginTransaction();
            cv.put(Schema.SLIST_ID, colVals.get(0));
            cv.put(Schema.SLIST_ARCHV, "N");
            cv.put(Schema.SLIST_DATE, colVals.get(1));
            cv.put(Schema.SLIST_NAME, colVals.get(2));
            rowId = db.insertOrThrow(Schema.TABLE_SLIST, null, cv);
            db.setTransactionSuccessful();
        } catch(Exception ex) {
            Log.d(context.getClass().getCanonicalName(), ex.getMessage());
        } finally {
            db.endTransaction();
        }
        return rowId;
    }

    /**
     * Set the archive flag on a shopping list entry.
     * @param dbRowId The row id in the database (SLIST table).
     * @param archive True==archive, False==un-archive.
     * @return The count of items updated.
     */
    public int archiveShoppingList(int dbRowId, boolean archive) {
        int count = -1;
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getDatabase();

        try {
            db.beginTransaction();
            if(archive) {
                cv.put(Schema.SLIST_ARCHV, "Y");
            } else {
                cv.put(Schema.SLIST_ARCHV, "N");
            }
            count = db.update(Schema.TABLE_SLIST, cv, Schema.WHERE_SLIST_ROWID, new String[] {Integer.toString(dbRowId)});
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
     * Set the archive flag on a general item list entry.
     * @param dbRowId The row id in the database (GEENERAL table).
     * @param archive True==archive, False==un-archive.
     * @return The count of items updated.
     */
    public int archiveGeneralItem(int dbRowId, boolean archive) {
        int count = -1;
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getDatabase();

        try {
            db.beginTransaction();
            if(archive) {
                cv.put(Schema.GENERAL_ARCHV, "Y");
            } else {
                cv.put(Schema.GENERAL_ARCHV, "N");
            }
            count = db.update(Schema.TABLE_GENERAL, cv, Schema.WHERE_GENERAL_ROWID, new String[] {Integer.toString(dbRowId)});
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
     * Set the archive flag on a shopping list item entry. This should be called in conjunction with
     * archiveShoppingList().
     * @param dbRowId The row id in the database (SLIST table).
     * @param archive True==archive, False==un-archive.
     * @return The count of items updated.
     */
    public int archiveShoppingListItem(int dbRowId, boolean archive) {
        int count = -1;
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getDatabase();

        try {
            db.beginTransaction();
            if(archive) {
                cv.put(Schema.SLIST_ITEM_ARCHV, "Y");
            } else {
                cv.put(Schema.SLIST_ITEM_ARCHV, "N");
            }
            count = db.update(Schema.TABLE_SLIST_ITEM, cv, Schema.WHERE_SLIST_ITEM_SLIST_ID, new String[] {Integer.toString(dbRowId)});
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
     * Write new shopping list item values and associate with a shopping list.
     * @param rowId The rowid of the shopping list to associate with,
     * @return The count of shopping list items.
     */
    public int createShoppingListItems(long rowId, int[] refIds) {
        ContentValues cv;
        int size = refIds.length;
        SQLiteDatabase db = dbHelper.getDatabase();

        for(int ndx = 0; ndx < size; ndx++) {
            cv = new ContentValues();

            try {
                db.beginTransaction();
                cv.put(Schema.SLIST_ITEM_SLIST_ID, rowId);
                cv.put(Schema.SLIST_ITEM_ARCHV, "N");
                cv.put(Schema.SLIST_ITEM_REF_ID, refIds[ndx]);
                db.insertOrThrow(Schema.TABLE_SLIST_ITEM, null, cv);
                db.setTransactionSuccessful();
            } catch(Exception ex) {
                Log.d(context.getClass().getCanonicalName(), ex.getMessage());
            } finally {
                db.endTransaction();
            }
        }
        return refIds.length;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: General">
    /**
     * Write a new General Item.
     * @param colVals [0]-id, [1]-date, [2]-name.
     * @return The rowId of the newly inserted row, or -1,
     */
    public long createGeneralItem(List<String> colVals) {
        long rowId = -1;
        SQLiteDatabase db = dbHelper.getDatabase();
        ContentValues cv = new ContentValues();

        try {
            db.beginTransaction();
            cv.put(Schema.GENERAL_ID, colVals.get(0));
            cv.put(Schema.GENERAL_ARCHV, "N");
            cv.put(Schema.GENERAL_DATE, colVals.get(1));
            cv.put(Schema.GENERAL_NAME, colVals.get(2));
            rowId = db.insertOrThrow(Schema.TABLE_GENERAL, null, cv);
            db.setTransactionSuccessful();
        } catch(Exception ex) {
            Log.d(context.getClass().getCanonicalName(), ex.getMessage());
        } finally {
            db.endTransaction();
        }
        return rowId;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Utility">
    /**
     * Get the number of records for a table.
     * @param tableName The table to check.
     * @param args Optional aguments.
     * @return The number of records.
     */
    public int getTableRowCount(String tableName, @Nullable String where, @Nullable String[] args) {
        Cursor cursor = null;
        SQLiteDatabase db = dbHelper.getDatabase();
        try {
            db.beginTransaction();
            switch(tableName) {
                case Schema.TABLE_REF_ITEM:
                    if(where == null) {
                        args = null;
                        cursor = db.rawQuery("SELECT " + Schema.REF_ITEM_ROWID + " FROM " + tableName, args);
                    } else {
                        cursor = db.rawQuery("SELECT " + Schema.REF_ITEM_ROWID + " FROM " + tableName + where, args);
                    }
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
    public void setTableDefaults(String tableName) {
        String code;
        String desc;
        String[] itemTypes = Resources.getInstance().getStringArray(R.array.shopping_item_types);
        List<String[]> allDefaults = Resources.getInstance().getAllDefaults();

        SQLiteDatabase db = dbHelper.getDatabase();
        ContentValues cv = new ContentValues();

        String[] type = allDefaults.get(0);
        code = itemTypes[0].split(":")[0];
        desc = itemTypes[0].split(":")[1];

        for (String val : type) {

            try {
                db.beginTransaction();
                cv.put(Schema.REF_ITEM_ARCHV, "N");
                cv.put(Schema.REF_ITEM_CODE, code);
                cv.put(Schema.REF_ITEM_DESC, desc);
                cv.put(Schema.REF_ITEM_VALUE, val);
                cv.put(Schema.REF_ITEM_SEL, "N");
                db.insertOrThrow(tableName, null, cv);
                db.setTransactionSuccessful();
            } catch(SQLException ex){
                Log.d(context.getClass().getCanonicalName(), ex.getMessage());
            } finally{
                if(db != null) {
                    db.endTransaction();
                }
            }
        }

        type = allDefaults.get(1);
        code = itemTypes[1].split(":")[0];
        desc = itemTypes[1].split(":")[1];

        for (String val : type) {

            try {
                db.beginTransaction();
                cv.put(Schema.REF_ITEM_ARCHV, "N");
                cv.put(Schema.REF_ITEM_CODE, code);
                cv.put(Schema.REF_ITEM_DESC, desc);
                cv.put(Schema.REF_ITEM_VALUE, val);
                cv.put(Schema.REF_ITEM_SEL, "N");
                db.insertOrThrow(tableName, null, cv);
                db.setTransactionSuccessful();
            } catch(SQLException ex){
                Log.d(context.getClass().getCanonicalName(), ex.getMessage());
            } finally{
                if(db != null) {
                    db.endTransaction();
                }
            }
        }

        type = allDefaults.get(2);
        code = itemTypes[2].split(":")[0];
        desc = itemTypes[2].split(":")[1];

        for (String val : type) {
            try {
                db.beginTransaction();
                cv.put(Schema.REF_ITEM_ARCHV, "N");
                cv.put(Schema.REF_ITEM_CODE, code);
                cv.put(Schema.REF_ITEM_DESC, desc);
                cv.put(Schema.REF_ITEM_VALUE, val);
                cv.put(Schema.REF_ITEM_SEL, "N");
                db.insertOrThrow(tableName, null, cv);
                db.setTransactionSuccessful();
            } catch(SQLException ex){
                Log.d(context.getClass().getCanonicalName(), ex.getMessage());
            } finally{
                if(db != null) {
                    db.endTransaction();
                }
            }
        }
        // TODO - item type OTHER not done.
    }

    private Cursor getRecords(String tableName, @Nullable String[] projection, @Nullable String whereClause, @Nullable String[] selArgs) {

        // sanity checks.
        if(projection == null) {
            projection = getProjection(tableName);
        }
        if(whereClause == null) {
            selArgs = null;
        } else if(selArgs == null) {
            whereClause = null;
        }

        Cursor cursor = null;
        SQLiteDatabase db = dbHelper.getDatabase();

        try {
            db.beginTransaction();
            cursor = db.query(tableName, projection, whereClause, selArgs, null, null, null);
        } catch(Exception ex) {
            Log.d(context.getClass().getCanonicalName(), ex.getMessage());
        } finally {
            if(db != null) {
                db.endTransaction();
            }
            return cursor;
        }
    }

    private Cursor getRecordsRaw(String query, @Nullable String[] selArgs) {
        Cursor cursor = null;
        SQLiteDatabase db = dbHelper.getDatabase();
        try {
            db.beginTransaction();
            cursor = db.rawQuery(query, selArgs);
        } catch (Exception ex) {
            Log.d(context.getClass().getCanonicalName(), ex.getMessage());
        } finally {
            if(db != null) {
                db.endTransaction();
            }
            return cursor;
        }
    }

    private String[] getProjection(String tableName) {
        String[] projection = null;
        switch (tableName) {
            case Schema.TABLE_SLIST:
                projection = dbHelper.getProjection(DatabaseHelper.Projection.SListSchema);
                break;
            case Schema.TABLE_REF_ITEM:
                projection = dbHelper.getProjection(DatabaseHelper.Projection.RefItemSchema);
                break;
            case Schema.TABLE_GENERAL:
                projection = dbHelper.getProjection(DatabaseHelper.Projection.GeneralSchema);
        }
        return  projection;
    }

    public void destroy() {
        if(dbHelper != null) {
            dbHelper.close();
        }
        dbHelper = null;
        if(context != null) {
            context = null;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Private Vars">
    private Context context;
    private static DatabaseHelper dbHelper;
    private static volatile Database instance = null;
    //</editor-fold>
}
