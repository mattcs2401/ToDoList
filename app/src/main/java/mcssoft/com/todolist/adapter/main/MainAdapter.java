package mcssoft.com.todolist.adapter.main;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.database.Schema;
import mcssoft.com.todolist.interfaces.IItemClickListener;

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {

    public MainAdapter(boolean isEmptyView) {
        this.isEmptyView = isEmptyView;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(!isEmptyView) {
            view = inflater.inflate(R.layout.shopping_row, parent, false);
            mvh = new MainViewHolder(view, isEmptyView);
            mvh.setItemClickListener(icListener);
            return mvh;
        } else {
            view = inflater.inflate(R.layout.shopping_row_empty, parent, false);
            return new MainViewHolder(view, isEmptyView);
        }
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        if(!isEmptyView) {
            cursor.moveToPosition(position);
            holder.getTvDate().setText(cursor.getString(idDateNdx));
        }
    }

    @Override
    public long getItemId(int position) {
        cursor.moveToPosition(position);
        return cursor.getLong(idColNdx);
    }

    @Override
    public int getItemCount() {
        if(cursor != null) {
            return cursor.getCount();
        } else {
            return 1; // need this for empty view.
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);

    }

    public void setOnItemClickListener(IItemClickListener iclistener) {
        this.icListener = iclistener;
    }

    public Cursor getCursor() { return cursor; }

    public void swapCursor(Cursor cursor) {
        if((cursor != null) && (cursor.getCount() > 0)) {
            this.cursor = cursor;
            cursor.moveToFirst();
            idColNdx = cursor.getColumnIndex(Schema.SLIST_ROWID);
            idDateNdx = cursor.getColumnIndex(Schema.SLIST_DATE);
//            idNameNdx = cursor.getColumnIndex(Schema.SLIST_NAME);
//            idNdx = cursor.getColumnIndex(Schema.SLIST_ID);
            notifyDataSetChanged();
        }
    }


    private Cursor cursor;                  // backing data
    private int idColNdx;                   // SLIST.ROWID
    private int idDateNdx;                  // SLIST.DATE
    private int idNameNdx;                  // SLIST.NAME
    private int idNdx;                      // SLIST.ID
    private MainViewHolder mvh;
    private boolean isEmptyView;
    private IItemClickListener icListener;
}
