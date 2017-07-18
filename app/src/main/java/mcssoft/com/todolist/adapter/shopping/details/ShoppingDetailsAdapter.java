package mcssoft.com.todolist.adapter.shopping.details;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import mcssoft.com.todolist.database.Schema;
import mcssoft.com.todolist.model.ShoppingList;

public class ShoppingDetailsAdapter extends RecyclerView.Adapter {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        //return shoppingList;
        return 0;
        
    }

    public void swapCursor(Cursor cursor) {
        this.cursor = cursor;
        cursor.moveToFirst();
//        idColNdx = cursor.getColumnIndex(Schema.SLIST_ROWID);
//        idDateNdx = cursor.getColumnIndex(Schema.SLIST_DATE);

    }

    private Cursor cursor;
    private ShoppingList shoppingList;
}
