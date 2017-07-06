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
import mcssoft.com.todolist.adapter.ShoppingAdapter;
import mcssoft.com.todolist.database.DatabaseOperations;
import mcssoft.com.todolist.database.SchemaConstants;
import mcssoft.com.todolist.interfaces.IItemClickListener;
import mcssoft.com.todolist.utility.Resources;


public class ShoppingFragment extends Fragment implements IItemClickListener {

    public ShoppingFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getArguments();
        if(args == null) {
            pageNo = 0;
        } else {
            Resources res = new Resources(getActivity());
            pageNo = args.getInt(res.getString(R.string.bundle_key));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.shopping_fragment, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setCursor(pageNo);        // generate backing data.
        setShoppingAdapter();     // set adapter associated with the recycler view.
        setRecyclerView(rootView);// set the recycler view.
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Listeners">
    @Override
    public void onItemClick(View view, int position) {
        int dbRowId = -1;
        if(view instanceof CheckBox) {
            // only interested if the checkbox is touched.
            // TBA - do something.
            dbRowId = getDbRowId(position);
        }
        String bp = "";
    }
    //</editor-fold>

    public enum PageType {
        General, Fruit_and_Veg, Meat_and_Fish
    }

    private int getDbRowId(int position) {
        adapter.getItemId(position);
        Cursor cursor = adapter.getCursor();
        int dbRowId = cursor.getInt(cursor.getColumnIndex(SchemaConstants.SL_ITEM_ROWID));
        return dbRowId;
    }

    private void setCursor(int pageNo) {
        DatabaseOperations dbOper = new DatabaseOperations(getActivity());
        switch(pageNo) {
            case 0:
                cursor = dbOper.getShoppingItems(PageType.General);
                break;
            case 1:
                cursor = dbOper.getShoppingItems(PageType.Fruit_and_Veg);
                break;
            case 2:
                cursor = dbOper.getShoppingItems(PageType.Meat_and_Fish);
                break;
        }
    }

    private void setShoppingAdapter() {
        adapter = new ShoppingAdapter();
        adapter.swapCursor(cursor);
        adapter.setOnItemClickListener(this);
     }

    private void setRecyclerView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.id_rv_shopping);
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
    private ShoppingAdapter adapter;
}
