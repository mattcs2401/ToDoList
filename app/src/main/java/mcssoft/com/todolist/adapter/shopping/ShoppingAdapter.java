package mcssoft.com.todolist.adapter.shopping;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.database.Schema;
import mcssoft.com.todolist.interfaces.IItemClick;
import mcssoft.com.todolist.interfaces.IItemLongClick;

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
                return new ShoppingViewHolder(view, icListener, ilcListener);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ShoppingViewHolder holder, int position) {
        if(!isEmptyView) {
            cursor.moveToPosition(position);
            holder.getTvDate().setText(cursor.getString(idDateNdx));

            if(metaData != null && metaData.size() == cursor.getCount()) {
                int count = metaData.get(position)[1];
                holder.getTvNumItems().setText(Integer.toString(count));
                if(count > 1) {

                    holder.getTvItems().setText("items");
                } else {
                    holder.getTvItems().setText("item");
                }
            } else {
                holder.getTvNumItems().setText("");
            }
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
        return SHOPPING_VIEW;
    }

    public void setOnItemClickListener(IItemClick iclistener) {
        this.icListener = iclistener;
    }

    public void setOnItemLongClickListener(IItemLongClick ilclistener) {
        this.ilcListener = ilclistener;
    }

    public void setEmptyView(boolean isEmptyView) {
        this.isEmptyView = isEmptyView;
    }

    public Cursor getData() { return cursor; }

    public void setData(Cursor cursor) {
        if(cursor == null || cursor.getCount() < 1) {
            isEmptyView = true;
        } else {
            isEmptyView = false;
            this.cursor = cursor;
            cursor.moveToFirst();
            idColNdx = cursor.getColumnIndex(Schema.SLIST_ROWID);
            idDateNdx = cursor.getColumnIndex(Schema.SLIST_DATE);
        }
        notifyDataSetChanged();
    }

    /**
     * Use a small amount of in memory data to lessen the amount of processing within each call to
     * onBindViewHolder().
     * @param metaData (any list member) [0] database row id, [1] count of associated shopping items.
     */
    public void setMetaData(ArrayList<int[]> metaData) {
        this.metaData = metaData;
    }

    private Cursor cursor;                       // backing data
    private int idColNdx;                        // SLIST.ROWID
    private int idDateNdx;                       // SLIST.DATE
    private int idNameNdx;                       // SLIST.NAME
    private int idNdx;                           // SLIST.ID
    private boolean isEmptyView;                 // flag, nothing to show.
    private ArrayList<int[]> metaData;           // shopping list meta data.
    private IItemClick icListener;       // interface click listener.
    private IItemLongClick ilcListener;  // interface long
    // click listener.

    private static final int EMPTY_VIEW = 0;
    private static final int SHOPPING_VIEW = 1;
}
