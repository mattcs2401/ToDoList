package mcssoft.com.todolist.adapter.shopping;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.database.Schema;
import mcssoft.com.todolist.interfaces.IItemClickListener;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingViewHolder> {

    public ShoppingAdapter() { }

    @Override
    public ShoppingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch(viewType) {
            case EMPTY_VIEW:
                view = inflater.inflate(R.layout.row_empty, parent, false);
                return new ShoppingViewHolder(view);
            case SHOPPING_VIEW:
                view = inflater.inflate(R.layout.shopping_row, parent, false);
                return new ShoppingViewHolder(view, icListener);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ShoppingViewHolder holder, int position) {
        if(!isEmptyView) {
            cursor.moveToPosition(position);
            holder.getTvDate().setText(cursor.getString(idDateNdx));
        } else {
            holder.getEmptyView().getText().toString();
        }
    }

    @Override
    public long getItemId(int position) {
        cursor.moveToPosition(position);
        return cursor.getLong(idColNdx);
    }

    @Override
    public int getItemCount() {
        if(isEmptyView) {
            return  1; // need to do this so the onCreateViewHolder fires.
        }
        return cursor.getCount();
    }

    @Override
    public int getItemViewType(int position) {
        if(isEmptyView) {
            return EMPTY_VIEW;
        }
        //return super.getItemViewType(position);  // this returns 0.
        return SHOPPING_VIEW;
    }

    public void setOnItemClickListener(IItemClickListener iclistener) {
        this.icListener = iclistener;
    }

    public void setEmptyView(boolean isEmptyView) {
        this.isEmptyView = isEmptyView;
    }

    public Cursor getCursor() { return cursor; }

    public void swapCursor(Cursor cursor) {
            this.cursor = cursor;
            cursor.moveToFirst();
            idColNdx = cursor.getColumnIndex(Schema.SLIST_ROWID);
            idDateNdx = cursor.getColumnIndex(Schema.SLIST_DATE);
//            idNameNdx = cursor.getColumnIndex(Schema.SLIST_NAME);
//            idNdx = cursor.getColumnIndex(Schema.SLIST_ID);
            notifyDataSetChanged();
    }


    private Cursor cursor;                  // backing data
    private int idColNdx;                   // SLIST.ROWID
    private int idDateNdx;                  // SLIST.DATE
    private int idNameNdx;                  // SLIST.NAME
    private int idNdx;                      // SLIST.ID
    private boolean isEmptyView;
    private IItemClickListener icListener;

    private static final int EMPTY_VIEW = 0;
    private static final int SHOPPING_VIEW = 1;
    private static final int GENERAL_VIEW = 2;
}
