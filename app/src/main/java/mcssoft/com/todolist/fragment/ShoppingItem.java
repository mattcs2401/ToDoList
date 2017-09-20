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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.adapter.shopping.item.ShoppingItemAdapter;
import mcssoft.com.todolist.database.Database;
import mcssoft.com.todolist.database.Schema;
import mcssoft.com.todolist.interfaces.IItemClick;
import mcssoft.com.todolist.utility.DateTime;
import mcssoft.com.todolist.utility.Resources;

/**
 * Class that is a single shopping item.
 */
public class ShoppingItem extends Fragment implements IItemClick {

    //<editor-fold defaultstate="collapsed" desc="Region: Lifecycle">
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.shopping_item_fragment, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        pageCode = getArguments().getString(Resources.getInstance().getString(R.string.bundle_pagecode_key));

        setShoppingAdapter();     // set adapter associated with the recycler view.
        setRecyclerView(rootView);// set the recycler view.
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_shopping_item, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Listeners">
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_shopping_item_save:
                finalise();
                break;
        }
        return super.onOptionsItemSelected(item);
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
        // from shopping item view holder.
        if(view instanceof CheckBox) {
            if(((CheckBox) view.findViewById(R.id.id_cb_shopping_item)).isChecked()) {
                adapter.setCheck(position, true);
            } else {
                adapter.setCheck(position, false);
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Utility">
    private void finalise() {
        Cursor cursor = Database.getInstance().getCheckedReferenceItems();
        if(cursor.getCount() > 0) {
            writeNewShoppingList(getRefIds(cursor));
            uncheckReferenceItems();
            getActivity().finish();
        } else {
            Snackbar.make(rootView, Resources.getInstance()
                    .getString(R.string.snackbar_nothing_selected), Snackbar.LENGTH_SHORT).show();
        }
    }

    private void writeNewShoppingList(int[] refIds) {
        List<String> colVals = new ArrayList<>();
        DateTime dateTime = new DateTime();

        colVals.add(dateTime.getCompactedDateTime());      // list identifier.
        colVals.add(dateTime.getFormattedDate(false));     // list date.
        colVals.add("TBA");                                // list name.

        long rowId = Database.getInstance().createShoppingList(colVals);
        Database.getInstance().createShoppingListItems(rowId, refIds);
    }

    private int[] getRefIds(Cursor cursor) {
        int size = cursor.getCount();
        int[] refIds = new int[size];
        int colNdx = cursor.getColumnIndex(Schema.REF_ITEM_ROWID);
        int ndx = 0;
        while(cursor.moveToNext()) {
            if (ndx < size) {
                refIds[ndx] = cursor.getInt(colNdx);
                ndx++;
            }
        }
        return refIds;
    }

    private void uncheckReferenceItems() {
        Database.getInstance().unCheckReferenceItems();
    }

    private void setShoppingAdapter() {
        adapter = new ShoppingItemAdapter();
        adapter.setData(Database.getInstance().getAllReferenceItems(pageCode));
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
    //</editor-fold>

    private View rootView;
    private String pageCode;
    private ShoppingItemAdapter adapter;
}
