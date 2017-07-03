package mcssoft.com.todolist.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.utility.Resources;

/**
 * Class to Add, Edit, or Delete a To Do item.
 */
public class EditActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = new Resources(this);

        String action = getIntent().getAction();
        Bundle bundle = getIntent().getBundleExtra(res.getString(R.string.bundle_key));
        String type = bundle.getString(res.getString(R.string.list_type_key));

        if(action.equals(res.getString(R.string.list_add_action_key))) {
            // add a new item to a list.
            addListItem(type);
        } else if (action.equals(res.getString(R.string.list_edit_action_key))) {
            // edit a list item.
            editListItem(type);
        } else if(action.equals(res.getString(R.string.list_delete_action_key))) {
            // delete a list item.
            deleteListItem(type);
        }

    }

    private void addListItem(String type) {
        if(type.equals(res.getString(R.string.list_type_shopping))) {

            setContentView(R.layout.cv_shopping_item);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(res.getString(R.string.toolbar_title_new_shopping));

        } else if (type.equals(res.getString(R.string.list_type_general))) {

        }
    }

    private void editListItem(String type) {
        if(type.equals(res.getString(R.string.list_type_shopping))) {

        } else if (type.equals(res.getString(R.string.list_type_general))) {

        }
    }

    private void deleteListItem(String type) {
        if(type.equals(res.getString(R.string.list_type_shopping))) {

        } else if (type.equals(res.getString(R.string.list_type_general))) {

        }

    }


    private Resources res;
}
