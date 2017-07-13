package mcssoft.com.todolist.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ParentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ParentViewHolder(View view) {
        super(view);
    }

    public ParentViewHolder(View view, boolean isEmptyView) {
        super(view);
    }

    @Override
    public void onClick(View view) { }
}
