package mcssoft.com.todolist.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.database.Database;
import mcssoft.com.todolist.database.Schema;
import mcssoft.com.todolist.utility.Resources;

/**
 * Utility class to perform some basic checks before the main part of the app starts.
 */
public class CheckActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_check);            // this likely won't be seen.

        Resources.getInstance(getApplicationContext());    // set the Resources singleton.
        Database.getInstance(getApplicationContext());     // set the database singleton.

        // check if default values exist.
        if(Database.getInstance().getTableRowCount(Schema.TABLE_REF_ITEM, null, null) < 1) {
            Database.getInstance().setTableDefaults(Schema.TABLE_REF_ITEM);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle args = new Bundle();

        int count = Database.getInstance().getTableRowCount(Schema.TABLE_SLIST, null, null);
        args.putInt(Resources.getInstance().getString(R.string.sl_item_count_key), count);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Resources.getInstance().getString(R.string.bundle_key), args);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Database.getInstance().destroy();
        Resources.getInstance().destroy();
    }
}
