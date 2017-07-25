package mcssoft.com.todolist.adapter.shopping.summary;

import android.view.View;
import android.widget.TextView;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.adapter.base.ParentViewHolder;

public class ShoppingDetailsViewHolder extends ParentViewHolder {

    public ShoppingDetailsViewHolder(View view) {
        super(view);
        title = (TextView) view.findViewById(R.id.id_tv_shopping_summary_item);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
    }

    public TextView getTitle() { return title; }

    private TextView title;
}
