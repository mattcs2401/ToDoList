package mcssoft.com.todolist.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import mcssoft.com.todolist.utility.DatabaseLoadTask;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, SchemaConstants.DATABASE_NAME, null, SchemaConstants.DATABASE_VERSION);
        sqLiteDatabase = this.getWritableDatabase();
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDb) {
        sqLiteDb.beginTransaction();
        try {
            sqLiteDb.execSQL(SchemaConstants.DROP_TABLE_SHOPPING_LIST);
            sqLiteDb.execSQL(SchemaConstants.CREATE_TABLE_SHOPPING_LIST);

            populateTableDefaults(SchemaConstants.TABLE_SHOPPING_LIST);

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
        ShoppingListSchema
    }

    public static String [] getProjection(Projection projection) {
        switch (projection) {
            case ShoppingListSchema:
                return getShoppingListProjection();
        }
        return  null;
    }
    /**
     * Housekeeping activities.
     */
    public void onDestroy() {
        if(sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
        if(context != null) {
            context = null;
        }
    }

    private static String[] getShoppingListProjection() {
        return new String[] {
            SchemaConstants.SHOPPING_LIST_ROWID,
            SchemaConstants.SHOPPING_LIST_TYPE,
            SchemaConstants.SHOPPING_LIST_VALUE
        };
    }

    private void populateTableDefaults(String tableName) {
        switch(tableName) {
            case SchemaConstants.TABLE_SHOPPING_LIST:
                DatabaseLoadTask dblt = new DatabaseLoadTask(context, tableName);
                dblt.execute();
                break;
        }
    }

    private Context context;
    private SQLiteDatabase sqLiteDatabase;
}
