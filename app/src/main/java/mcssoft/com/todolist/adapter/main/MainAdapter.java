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

    public MainAdapter() {
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.content_main_row, parent, false);
        mvh = new MainViewHolder(view);
        mvh.setItemClickListener(icListener);
        return mvh;
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
        return cursor.getCount();
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
        this.cursor = cursor;
        cursor.moveToFirst();
        idColNdx = cursor.getColumnIndex(Schema.SL_ITEM_ROWID);
//            idTypNdx = cursor.getColumnIndex(Schema.SL_ITEM_TYPE);
//            idValNdx = cursor.getColumnIndex(Schema.SL_ITEM_VALUE);
//            idValSelNdx = cursor.getColumnIndex(Schema.SL_ITEM_VAL_SEL);
        notifyDataSetChanged();
    }


    private Cursor cursor;                  // backing data.
    private int idColNdx;
//    private int idTypNdx;
//    private int idValNdx;
//    private int idValSelNdx;
    private MainViewHolder mvh;
    private IItemClickListener icListener;
}
