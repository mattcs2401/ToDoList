package mcssoft.com.todolist.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.database.DatabaseOperations;
import mcssoft.com.todolist.database.SchemaConstants;
import mcssoft.com.todolist.utility.Resources;

/**
 * Utility class to perform some basic checks before the menu_activity_main part of the app starts.
 */
public class CheckActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_check);  // this likely won't be seen.
        Resources.getInstance(this);             // set the Resources singleton.

        DatabaseOperations dbOper = new DatabaseOperations(this);

        // check if default values exist.
        if(dbOper.getTableRowCount(SchemaConstants.TABLE_SL_ITEM, null) < 1) {
            dbOper.writeTableDefaults(SchemaConstants.TABLE_SL_ITEM);
        }

        Bundle args = new Bundle();

        int count = dbOper.getTableRowCount(SchemaConstants.TABLE_SL, null);
        args.putInt(Resources.getInstance().getString(R.string.sl_item_count_key), count);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Resources.getInstance().getString(R.string.bundle_key), args);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Resources.getInstance().destroy();
    }
}
