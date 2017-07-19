package mcssoft.com.todolist.adapter.shopping.details;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.adapter.shopping.ShoppingViewHolder;
import mcssoft.com.todolist.database.Schema;
import mcssoft.com.todolist.model.ShoppingList;
import mcssoft.com.todolist.model.ShoppingListItem;

public class ShoppingDetailsAdapter extends RecyclerView.Adapter {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch(viewType) {
            case HEADER_VIEW:
                view = inflater.inflate(R.layout.shopping_details_header_row, parent, false);
                return new ShoppingDetailsHeaderViewHolder(view);
            case SHOPPING_VIEW:
                view = inflater.inflate(R.layout.shopping_details_item_row, parent, false);
                return new ShoppingDetailsViewHolder(view);
        }
        throw new RuntimeException("No match for " + viewType + ".");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ShoppingListItem sListItem = shoppingList.getShoppingListItem(position);
        if(holder instanceof ShoppingDetailsHeaderViewHolder) {
            ((ShoppingDetailsHeaderViewHolder) holder).getHeaderTitle().setText(sListItem.getDescription());
        } else if (holder instanceof ShoppingDetailsViewHolder) {
            ((ShoppingDetailsViewHolder) holder).getTitle().setText(sListItem.getValue());
        }
    }

    @Override
    public int getItemCount() {
        return shoppingList.getSize();
    }

    @Override
    public int getItemViewType(int position) {
        if(shoppingList.getShoppingListItem(position).isHeader()) {
            return HEADER_VIEW;
        } else {
            return SHOPPING_VIEW;
        }
    }

    public void swapCursor(Cursor cursor) {
        this.cursor = cursor;
        generateDataItems();
    }

    private void generateDataItems() {
        idCodeNdx = cursor.getColumnIndex(Schema.REF_ITEM_CODE);
        idDescNdx = cursor.getColumnIndex(Schema.REF_ITEM_DESC);
        idValueNdx = cursor.getColumnIndex(Schema.REF_ITEM_VALUE);

        shoppingList = new ShoppingList();
        cursor.moveToFirst();

        String code = cursor.getString(idCodeNdx);
        String oldCode = code;

        // Set first header and row.
        setHeaderRow(cursor.getString(idDescNdx));
        setContentRow(code, cursor.getString(idDescNdx), cursor.getString(idValueNdx));

        // Set subsequent rows or headers.
        while(cursor.moveToNext()) {
            code = cursor.getString(idCodeNdx);
            if(code.equals(oldCode)) {
                setContentRow(code, cursor.getString(idDescNdx), cursor.getString(idValueNdx));
            } else {
                oldCode = code;
                setHeaderRow(cursor.getString(idDescNdx));
                setContentRow(code, cursor.getString(idDescNdx), cursor.getString(idValueNdx));
            }
        }
    }

    private void setContentRow(String code, String desc, String value) {
        shoppingList.putShoppingListItem(new ShoppingListItem(code, desc, value));
    }

    private void setHeaderRow(String desc) {
        shoppingList.putShoppingListItem(new ShoppingListItem(true, desc));
    }

    private int idCodeNdx;
    private int idDescNdx;
    private int idValueNdx;
    private Cursor cursor;             // backing data.
    private ShoppingList shoppingList; // backing data as list.

    private static final int HEADER_VIEW = 0;
    private static final int SHOPPING_VIEW = 1;
}
