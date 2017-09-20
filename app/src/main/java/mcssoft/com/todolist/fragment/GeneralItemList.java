package mcssoft.com.todolist.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.adapter.general.GeneralAdapter;
import mcssoft.com.todolist.database.Database;
import mcssoft.com.todolist.database.Schema;
import mcssoft.com.todolist.interfaces.IItemClick;
import mcssoft.com.todolist.utility.Resources;

/**
 * Class that represents the listing of General Items.
 */
public class GeneralItemList extends Fragment implements IItemClick, View.OnClickListener {

    //<editor-fold defaultstate="collapsed" desc="Region: Lifecycle">
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        args = getArguments();    // TBA.
        setAdapter();             // set adapter associated with the recycler view.
        setRecyclerView(rootView);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Interface">
    /**
     * Interface IItemClick returns here.
     * @param view The selected Adapter item view.
     * @param position Row position of the Adapter's item.
     */
    @Override
    public void onItemClick(View view, int position) {
        dbRowId = getDbRowId(position);
        switch(view.getId()) {
            case R.id.id_iv_delete:
                Database.getInstance().archiveGeneralItem(dbRowId, true);
//                Database.getInstance().archiveShoppingListItem(dbRowId, true);
                showSnackBarDelete(view);
                adapter.setData(Database.getInstance().getAllGeneral());
                break;
            case R.id.id_iv_expand:
                Toast.makeText(getActivity(), "Expand not implemted yet.", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.id_cv_general_row:
//                Toast.makeText(getActivity(), "Show General details not implemted yet.", Toast.LENGTH_SHORT).show();
//                // TBA show a dialog with summary of General item values.
//                break;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Listeners">
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.snackbar_action:
                Database.getInstance().archiveGeneralItem(dbRowId, false);
//                Database.getInstance().archiveShoppingListItem(dbRowId, false);
                showSnackBarRestore(view);
                adapter.setData(Database.getInstance().getAllGeneral());
                break;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Utility">
    private int getDbRowId(int position) {
        adapter.getItemId(position);
        Cursor cursor = adapter.getCursor();
        int dbRowId = cursor.getInt(cursor.getColumnIndex(Schema.GENERAL_ROWID));
        return dbRowId;
    }

    private void setAdapter() {
        cursor = Database.getInstance().getAllGeneral();
        adapter = new GeneralAdapter();
        if(cursor == null || cursor.getCount() < 1) {
            adapter.setEmptyView(true);
        } else {
            adapter.setEmptyView(false);
            adapter.setData(cursor);
        }
        adapter.setOnItemClickListener(this);
     }

    private void setRecyclerView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.id_rv_fragment);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.scrollToPosition(0);
        recyclerView.setLayoutManager(llm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void showSnackBarDelete(View view) {
        Snackbar snackbar = Snackbar.make(view, Resources.getInstance()
                .getString(R.string.snackbar_item_removed), Snackbar.LENGTH_LONG)
                .setAction(Resources.getInstance().getString(R.string.snackbar_item_undo), this);
        snackbar.show();
    }

    private void showSnackBarRestore(View view) {
        Snackbar snackbar = Snackbar.make(view, Resources.getInstance()
                .getString(R.string.snackbar_item_restored), Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
    //</editor-fold>

    private int dbRowId;
    private Bundle args;
    private Cursor cursor;
    private View rootView;
    private GeneralAdapter adapter;
}
