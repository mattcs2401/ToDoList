package mcssoft.com.todolist.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.database.DatabaseOperations;
import mcssoft.com.todolist.database.SchemaConstants;

/**
 * Utility class to perform some basic checks before the main part of the app starts.
 */
public class CheckActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_check);  // this likely won't be seen.

        DatabaseOperations dbOper = new DatabaseOperations(this);

        if(dbOper.getTableRowCount(SchemaConstants.TABLE_SL_ITEM, null) < 1) {
            dbOper.writeTableDefaults(SchemaConstants.TABLE_SL_ITEM);
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
