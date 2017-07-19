package mcssoft.com.todolist.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.adapter.shopping.item.ShoppingItemAdapter;
import mcssoft.com.todolist.database.Database;
import mcssoft.com.todolist.database.Schema;
import mcssoft.com.todolist.interfaces.IItemClickListener;
import mcssoft.com.todolist.utility.Resources;


public class ShoppingItemFragment extends Fragment implements IItemClickListener {

    public ShoppingItemFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getArguments();
        if(args == null) {
            pageNo = 0;
        } else {
            pageNo = args.getInt(Resources.getInstance().getString(R.string.bundle_key));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.shopping_item_fragment, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setCursor(pageNo);        // generate backing data.
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
        int dbRowId = -1;
        if(view instanceof CheckBox) {
            // mimmick the checkbox check/uncheck on the underlying record.
            dbRowId = getDbRowId(position);
            if(((CheckBox) view.findViewById(R.id.id_cb_shopping_item)).isChecked()) {
                Database.getInstance().setCheckReferenceItem(dbRowId, true);
            } else {
                Database.getInstance().setCheckReferenceItem(dbRowId, false);
            }
        }
    }
    //</editor-fold>

    public enum PageType {
        GENRL, FANDV, MANDF
    }

    private int getDbRowId(int position) {
        adapter.getItemId(position);
        Cursor cursor = adapter.getCursor();
        int dbRowId = cursor.getInt(cursor.getColumnIndex(Schema.REF_ITEM_ROWID));
        return dbRowId;
    }

    private void setCursor(int pageNo) {
        switch(pageNo) {
            case 0:
                cursor = Database.getInstance().getCheckedReferenceItems(PageType.GENRL);
                break;
            case 1:
                cursor = Database.getInstance().getCheckedReferenceItems(PageType.FANDV);
                break;
            case 2:
                cursor = Database.getInstance().getCheckedReferenceItems(PageType.MANDF);
                break;
        }
    }

    private void setShoppingAdapter() {
        adapter = new ShoppingItemAdapter();
        adapter.swapCursor(cursor);
        adapter.setOnItemClickListener(this);
     }

    private void setRecyclerView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.id_rv_shopping_item);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.scrollToPosition(0);
        recyclerView.setLayoutManager(llm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private int pageNo;
    private Bundle args;
    private Cursor cursor;
    private View rootView;
    private ShoppingItemAdapter adapter;
}