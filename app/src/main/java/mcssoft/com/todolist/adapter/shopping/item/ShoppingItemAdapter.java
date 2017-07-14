package mcssoft.com.todolist.adapter.shopping.item;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mcssoft.com.todolist.database.Schema;
import mcssoft.com.todolist.interfaces.IItemClickListener;
import mcssoft.com.todolist.R;

public class ShoppingItemAdapter extends RecyclerView.Adapter<ShoppingItemViewHolder> {

    @Override
    public ShoppingItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.shopping_item_row, parent, false);
        svh = new ShoppingItemViewHolder(view);
        svh.setItemClickListener(icListener);
        return svh;
    }

    @Override
    public void onBindViewHolder(ShoppingItemViewHolder holder, int position) {
        cursor.moveToPosition(position);
        if(cursor.getString(idValSelNdx).equals("Y")) {
            holder.getCbShoppingItem().setChecked(true);
        } else {
            holder.getCbShoppingItem().setChecked(false);
        }
        holder.gettvShoppingItem().setText(cursor.getString(idValNdx));
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
            idColNdx = cursor.getColumnIndex(Schema.REF_ITEM_ROWID);
            idTypNdx = cursor.getColumnIndex(Schema.REF_ITEM_DESC);
            idValNdx = cursor.getColumnIndex(Schema.REF_ITEM_VALUE);
            idValSelNdx = cursor.getColumnIndex(Schema.REF_ITEM_VAL_SEL);
            notifyDataSetChanged();
        }
    }

    private Cursor cursor;                  // backing data.
    private int idColNdx;
    private int idTypNdx;
    private int idValNdx;
    private int idValSelNdx;
    private ShoppingItemViewHolder svh;
    private IItemClickListener icListener;
}
