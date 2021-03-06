package mcssoft.com.todolist.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, Schema.DATABASE_NAME, null, Schema.DATABASE_VERSION);
        sqLiteDatabase = this.getWritableDatabase();
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDb) {
        try {
            sqLiteDb.beginTransaction();
            sqLiteDb.execSQL(Schema.DROP_TABLE_SLIST);
            sqLiteDb.execSQL(Schema.DROP_TABLE_SLIST_ITEM);
            sqLiteDb.execSQL(Schema.DROP_TABLE_GENERAL);
            sqLiteDb.execSQL(Schema.DROP_TABLE_GEN_ITEM);
            sqLiteDb.execSQL(Schema.DROP_TABLE_REF_ITEM);
            sqLiteDb.execSQL(Schema.CREATE_TABLE_REF_ITEM);
            sqLiteDb.execSQL(Schema.CREATE_TABLE_SLIST);
            sqLiteDb.execSQL(Schema.CREATE_TABLE_SLIST_ITEM);
            sqLiteDb.execSQL(Schema.CREATE_TABLE_GENERAL);
            sqLiteDb.execSQL(Schema.CREATE_TABLE_GEN_ITEM);
            sqLiteDb.setTransactionSuccessful();
        } catch(SQLException ex) {
            Log.d(context.getClass().getCanonicalName(), ex.getMessage());
        } finally {
            sqLiteDb.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    public SQLiteDatabase getDatabase() {
        return sqLiteDatabase;
    }

    public enum Projection {
        RefItemSchema, SListSchema, SListItemSchema, GeneralSchema, GenItemSchema
    }

    public static String [] getProjection(Projection p) {
        String[] projection = null;
        switch (p) {
            case RefItemSchema:
                projection = getRefItemProjection();
                break;
            case SListSchema:
                projection = getSListProjection();
                break;
            case SListItemSchema:
                projection = getSListItemProjection();
                break;
            case GeneralSchema:
                projection = getGeneralProjection();
                break;
            case GenItemSchema:
                projection = getGenItemProjection();
                break;
        }
        return  projection;
    }

    /**
     * Housekeeping activities.
     */
    public void close() {
        if((sqLiteDatabase != null) && (sqLiteDatabase.isOpen())) {
            sqLiteDatabase.close();
        }
        if(context != null) {
            context = null;
        }
    }

    private static String[] getRefItemProjection() {
        return new String[] {
            Schema.REF_ITEM_ROWID,
            Schema.REF_ITEM_ARCHV,
            Schema.REF_ITEM_CODE,
            Schema.REF_ITEM_DESC,
            Schema.REF_ITEM_VALUE,
            Schema.REF_ITEM_SEL
        };
    }

    private static String[] getSListProjection() {
        return new String[] {
                Schema.SLIST_ROWID,
                Schema.SLIST_ARCHV,
                Schema.SLIST_ID,
                Schema.SLIST_NAME,
                Schema.SLIST_DATE
        };
    }

    private static String[] getSListItemProjection() {
        return new String[]{
            Schema.SLIST_ITEM_ROWID,
            Schema.SLIST_ITEM_ARCHV,
            Schema.SLIST_ITEM_SLIST_ID, // SLIST.SLIST_ID
            Schema.SLIST_ITEM_REF_ID    // REF_ITEM.ROWID
        };
    }

    private static String[] getGeneralProjection() {
        return new String[] {
            Schema.GENERAL_ROWID,
            Schema.GENERAL_ARCHV,
            Schema.GENERAL_ID,
            Schema.GENERAL_NAME,
            Schema.GENERAL_DATE
        };
    }

    private static String[] getGenItemProjection() {
        return new String[] {
                Schema.GEN_ITEM_ROWID,
                Schema.GEN_ITEM_ARCHV,
                Schema.GEN_ITEM_ID,
                Schema.GEN_ITEM_GENERAL,    // GENERAL.GENERAL_ID.
                Schema.GEN_ITEM_SLIST,      // SLIST.SLIST_ID.
                Schema.GEN_ITEM_TEXT
        };
    }

    private Context context;
    private SQLiteDatabase sqLiteDatabase;
}
