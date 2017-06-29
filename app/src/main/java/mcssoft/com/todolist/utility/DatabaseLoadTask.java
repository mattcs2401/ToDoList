package mcssoft.com.todolist.utility;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import mcssoft.com.todolist.database.DatabaseHelper;
import mcssoft.com.todolist.database.DatabaseOperations;

/**
 * Utility class to load default table values into the database.
 */
public class DatabaseLoadTask extends AsyncTask {

    public DatabaseLoadTask(Context context, String tableName) {
        this.context = context;
        this.tableName = tableName;
    }

    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        dbOper = new DatabaseOperations(context);
    }

    @Override
    protected Object doInBackground(Object[] params) {
        dbOper.writeTableDefaults(tableName);
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    private String tableName;
    private Context context;
    private DatabaseOperations dbOper;
}
