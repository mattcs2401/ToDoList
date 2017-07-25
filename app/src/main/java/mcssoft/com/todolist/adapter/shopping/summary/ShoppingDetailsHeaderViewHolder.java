package mcssoft.com.todolist.adapter.shopping.summary;

import android.view.View;
import android.widget.TextView;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.adapter.base.ParentViewHolder;

public class ShoppingDetailsHeaderViewHolder extends ParentViewHolder {

    public ShoppingDetailsHeaderViewHolder(View view) {
        super(view);
        headerTitle = (TextView) view.findViewById(R.id.id_tv_shopping_summary_item);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
    }

    public TextView getHeaderTitle() { return headerTitle; }

    private TextView headerTitle;
}
