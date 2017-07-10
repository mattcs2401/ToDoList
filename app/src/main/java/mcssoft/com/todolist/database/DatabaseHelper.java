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
            sqLiteDb.execSQL(Schema.DROP_TABLE_SL);
            sqLiteDb.execSQL(Schema.DROP_TABLE_REF_ITEM);
            sqLiteDb.execSQL(Schema.CREATE_TABLE_SL_ITEM);
            sqLiteDb.execSQL(Schema.CREATE_TABLE_SL);
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
        SLItemSchema, SLSchema
    }

    public static String [] getProjection(Projection p) {
        String[] projection = null;
        switch (p) {
            case SLItemSchema:
                projection = getSLItemProjection();
                break;
            case SLSchema:
                projection = getSLProjection();
                break;
        }
        return  projection;
    }
    /**
     * Housekeeping activities.
     */
    public void close() {
        if(sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
        if(context != null) {
            context = null;
        }
    }

    private static String[] getSLItemProjection() {
        return new String[] {
            Schema.REF_ITEM_ROWID,
            Schema.REF_ITEM_TYPE,
            Schema.REF_ITEM_VALUE,
            Schema.REF_ITEM_VAL_SEL
        };
    }

    private static String[] getSLProjection() {
        return new String[] {
                Schema.SL_ROWID,
                Schema.SL_ID,
                Schema.SL_TYPE,
                Schema.SL_VAL
        };
    }

    private Context context;
    private SQLiteDatabase sqLiteDatabase;
}
