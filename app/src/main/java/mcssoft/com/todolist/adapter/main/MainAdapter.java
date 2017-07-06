package mcssoft.com.todolist.adapter.main;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import mcssoft.com.todolist.database.SchemaConstants;
import mcssoft.com.todolist.interfaces.IItemClickListener;

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {

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
            return 0;
        }
    }

    public void setOnItemClickListener(IItemClickListener iclistener) {
        this.icListener = iclistener;
    }

    public Cursor getCursor() { return cursor; }

    public void swapCursor(Cursor cursor) {
        if((cursor != null) && (cursor.getCount() > 0)) {
            this.cursor = cursor;
            cursor.moveToFirst();
            idColNdx = cursor.getColumnIndex(SchemaConstants.SL_ITEM_ROWID);
//            idTypNdx = cursor.getColumnIndex(SchemaConstants.SL_ITEM_TYPE);
//            idValNdx = cursor.getColumnIndex(SchemaConstants.SL_ITEM_VALUE);
//            idValSelNdx = cursor.getColumnIndex(SchemaConstants.SL_ITEM_VAL_SEL);
            notifyDataSetChanged();
        }
    }


    private Cursor cursor;                  // backing data.
    private int idColNdx;
//    private int idTypNdx;
//    private int idValNdx;
//    private int idValSelNdx;
    private MainViewHolder mvh;
    private IItemClickListener icListener;
}
