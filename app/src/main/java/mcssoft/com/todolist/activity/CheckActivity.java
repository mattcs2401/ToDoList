package mcssoft.com.todolist.activity;

import android.app.Activity;
import android.content.Context;
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
        Resources.setInstance(getApplicationContext());    // set the Resources singleton.
        Database.setInstance(getApplicationContext());     // set the Database singleton.

        // check if default values exist.
        if(Database.getInstance().getTableRowCount(Schema.TABLE_REF_ITEM, null, null) < 1) {
            Database.getInstance().setTableDefaults(Schema.TABLE_REF_ITEM);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, NEW_MAIN, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == NEW_MAIN) {
            // user has pressed back from ActivityMain.
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Database.getInstance().destroy();
        Resources.getInstance().destroy();
    }

    private static int NEW_MAIN = 1;
}
