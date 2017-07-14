package mcssoft.com.todolist.adapter.shopping.item;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import mcssoft.com.todolist.interfaces.IItemClickListener;
import mcssoft.com.todolist.R;
import mcssoft.com.todolist.adapter.base.ParentViewHolder;

public class ShoppingItemViewHolder extends ParentViewHolder {

    public ShoppingItemViewHolder(View view) {
        super(view);
        this.view = view;
        cbShoppingItem = (CheckBox) view.findViewById(R.id.id_cb_shopping_item);
        cbShoppingItem.setOnClickListener(this);
        tvShoppingItem = (TextView) view.findViewById(R.id.id_tv_shopping_item);
    }

    @Override
    public void onClick(View view) {
        int position = getAdapterPosition(); // debug/testing purposes.
        icListener.onItemClick(view, position);
    }

    public void setItemClickListener(IItemClickListener iclistener) {
        this.icListener = iclistener;
        view.setOnClickListener(this);
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Accessors">
    public CheckBox getCbShoppingItem() { return cbShoppingItem; }
    public TextView gettvShoppingItem() { return tvShoppingItem; }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Private vars">
    private View view;
    private CheckBox cbShoppingItem;
    private TextView tvShoppingItem;
    private IItemClickListener icListener;
    //</editor-fold>
}
