package mcssoft.com.todolist.adapter.shopping.item;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mcssoft.com.todolist.database.Database;
import mcssoft.com.todolist.database.Schema;
import mcssoft.com.todolist.interfaces.IItemClickListener;
import mcssoft.com.todolist.R;
import mcssoft.com.todolist.model.items.ShoppingItemsList;
import mcssoft.com.todolist.model.items.ShoppingItemsListItem;

/**
 * Class that represents the adapter behind a fragment (page) of the shopping item view pager.
 * The backing data in the adapter is a sub-set of all the possible shopping list items.
 */
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
        if(shoppingItemsList.get(position).getRefSelect().equals("Y")) {
            holder.getCbShoppingItem().setChecked(true);
        } else {
            holder.getCbShoppingItem().setChecked(false);
        }
        holder.getTvShoppingItem().setText(shoppingItemsList.get(position).getRefValue());
    }

    @Override
    public int getItemCount() {
        return shoppingItemsList.size();
    }

    public void setOnItemClickListener(IItemClickListener iclistener) {
        this.icListener = iclistener;
    }

    public void setData(Cursor cursor) {
        this.cursor = cursor;
        shoppingItemsList = setModelData();
        notifyDataSetChanged();
    }

    public void setCheck(int position, boolean setCheck) {
        int dbRowId = shoppingItemsList.get(position).getRefId();
        if(setCheck) {
            shoppingItemsList.get(position).setRefSelect("Y");
            Database.getInstance().setCheckReferenceItem(dbRowId, true);
        } else {
            shoppingItemsList.get(position).setRefSelect("N");
            Database.getInstance().setCheckReferenceItem(dbRowId, false);
        }
        notifyItemChanged(position);
    }

    private ShoppingItemsList setModelData() {
        ShoppingItemsList shoppingItemsList = new ShoppingItemsList("");
        while(cursor.moveToNext()) {
            ShoppingItemsListItem sili = new ShoppingItemsListItem(
                    cursor.getInt(cursor.getColumnIndex(Schema.REF_ITEM_ROWID)),
                    cursor.getString(cursor.getColumnIndex(Schema.REF_ITEM_CODE)),
                    cursor.getString(cursor.getColumnIndex(Schema.REF_ITEM_DESC)),
                    cursor.getString(cursor.getColumnIndex(Schema.REF_ITEM_VALUE)),
                    cursor.getString(cursor.getColumnIndex(Schema.REF_ITEM_SEL)));

            shoppingItemsList.add(sili);
        }
        return shoppingItemsList;
    }

    private Cursor cursor;
    private ShoppingItemViewHolder svh;
    private IItemClickListener icListener;
    private ShoppingItemsList shoppingItemsList; // backing (in memory) data.
}
