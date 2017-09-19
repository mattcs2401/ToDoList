package mcssoft.com.todolist.adapter.general;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.database.Schema;
import mcssoft.com.todolist.interfaces.IItemClickListener;

public class GeneralAdapter extends RecyclerView.Adapter<GeneralViewHolder> {

    public GeneralAdapter() { }

    @Override
    public GeneralViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch(viewType) {
            case EMPTY_VIEW:
                view = inflater.inflate(R.layout.row_empty, parent, false);
                return new GeneralViewHolder(view);
            case GENERAL_VIEW:
                view = inflater.inflate(R.layout.general_row, parent, false);
                return new GeneralViewHolder(view, icListener);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(GeneralViewHolder holder, int position) {
        if(!isEmptyView) {
            cursor.moveToPosition(position);
            holder.getTvTitle().setText(cursor.getString(idNameNdx));
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
            // need to do this so the onCreateViewHolder fires.
            return  1;
        }
        return cursor.getCount();
    }

    @Override
    public int getItemViewType(int position) {
        if(isEmptyView) {
            return EMPTY_VIEW;
        }
        return GENERAL_VIEW;
    }

    public void setOnItemClickListener(IItemClickListener iclistener) {
        this.icListener = iclistener;
    }

    public void setEmptyView(boolean isEmptyView) {
        this.isEmptyView = isEmptyView;
    }

    public Cursor getCursor() { return cursor; }

    public void setData(Cursor cursor) {
       this.cursor = cursor;
       cursor.moveToFirst();
       idColNdx = cursor.getColumnIndex(Schema.GENERAL_ROWID);
       idDateNdx = cursor.getColumnIndex(Schema.GENERAL_DATE);
       idNameNdx = cursor.getColumnIndex(Schema.GENERAL_NAME);
       idNdx = cursor.getColumnIndex(Schema.GENERAL_ID);
       notifyDataSetChanged();
    }

    private int idNdx;                      // GENERAL.ID
    private int idColNdx;                   // GENERAL.ROWID
    private int idDateNdx;                  // GENERAL.DATE
    private int idNameNdx;                  // GENERAL.NAME
    private Cursor cursor;                  // backing data
    private boolean isEmptyView;            // flag.
    private IItemClickListener icListener;

    private static final int EMPTY_VIEW = 0;
    private static final int GENERAL_VIEW = 1;
}
