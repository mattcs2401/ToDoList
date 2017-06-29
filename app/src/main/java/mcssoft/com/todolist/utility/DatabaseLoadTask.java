package mcssoft.com.todolist.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import mcssoft.com.todolist.database.DatabaseHelper;
import mcssoft.com.todolist.database.DatabaseOperations;

/**
 * Utility class to load default table values into the database.
 */
public class DatabaseLoadTask extends AsyncTask<String, Void, String> {

    public DatabaseLoadTask(Context context) {
        this.context = context;
        this.tableName = tableName;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dbOper = new DatabaseOperations(context);
        progressDialog = new ProgressDialog(context, ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Initialising database.");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... tableNames) {
        dbOper.writeTableDefaults(tableNames[0]);
        // TODO - return something more meaningful.
        return "";
    }

    /*
      Runs on UI thread after doInBackground().
    */
    @Override
    protected void onPostExecute(String reult) {
//        super.onPostExecute(o);
        progressDialog.dismiss();
        // TODO - some sort of interface.
//        asyncResult.result(output, theResult);
    }

    private String tableName;
    private Context context;
    private DatabaseOperations dbOper;
    private ProgressDialog progressDialog;
}
