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
import android.widget.ImageView;
import android.widget.Toast;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.adapter.shopping.ShoppingAdapter;
import mcssoft.com.todolist.database.Database;
import mcssoft.com.todolist.database.Schema;
import mcssoft.com.todolist.interfaces.IItemClickListener;
import mcssoft.com.todolist.utility.Resources;


public class ShoppingFragment extends Fragment
        implements IItemClickListener, View.OnClickListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        args = getArguments();
//        if(args == null) {
//            pageNo = 0;
//        } else {
//            pageNo = args.getInt(Resources.getInstance().getString(R.string.bundle_key));
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setCursor();        // generate backing data.
        setShoppingAdapter();     // set adapter associated with the recycler view.
        setRecyclerView(rootView);// set the recycler view.
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Interface">
    /**
     * Interface IItemClickListener returns here.
     * @param view The selected Adapter item view.
     * @param position Row position of the Adapter's item.
     */
    @Override
    public void onItemClick(View view, int position) {
        this.position = position;
        int dbRowId = getDbRowId(position);
        switch(view.getId()) {
            case R.id.id_iv_delete:
                // TBA - confirm delete dialog / or snqackbar with undo button.
                doSnackBarDelete(view);
                break;
            case R.id.id_iv_expand:
                Toast.makeText(getActivity(), "Expand not implemted yet.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.id_cv_shopping_row:
                // show a dialog with summary of shopping items.
                showShoppingDetails(dbRowId);
                break;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Listeners">
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.snackbar_action) {
            doSnackBarRestore(view);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Utility">
    private int getDbRowId(int position) {
        adapter.getItemId(position);
        Cursor cursor = adapter.getCursor();
        int dbRowId = cursor.getInt(cursor.getColumnIndex(Schema.SLIST_ROWID));
        return dbRowId;
    }

    private void setCursor() {
        cursor = Database.getInstance().getAllShopping();
    }

    private void setShoppingAdapter() {
        adapter = new ShoppingAdapter();
        if(cursor == null || cursor.getCount() < 1) {
            adapter.setEmptyView(true);
        } else {
            adapter.setEmptyView(false);
            adapter.swapCursor(cursor);
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

    private void doSnackBarDelete(View view) {
        Snackbar snackbar = Snackbar.make(view, Resources.getInstance()
                .getString(R.string.snackbar_item_removed), Snackbar.LENGTH_LONG)
                .setAction(Resources.getInstance().getString(R.string.snackbar_item_undo), this);
        snackbar.show();
    }

    private void doSnackBarRestore(View view) {
        Snackbar snackbar = Snackbar.make(view, Resources.getInstance()
                .getString(R.string.snackbar_item_restored), Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    private void showShoppingDetails(int dbRowId) {
        Bundle args = new Bundle();
        args.putInt(Resources.getInstance().getString(R.string.sl_details_rowid_key), dbRowId);
        ShoppingDetailsFragment sdf = new ShoppingDetailsFragment();
        sdf.setArguments(args);
        sdf.show(getActivity().getFragmentManager(), "");
    }
    //</editor-fold>

    private int position;
//    private Bundle args;
    private Cursor cursor;
    private View rootView;
    private ShoppingAdapter adapter;
}
