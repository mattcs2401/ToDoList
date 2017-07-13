package mcssoft.com.todolist.adapter.main;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.adapter.base.ParentViewHolder;
import mcssoft.com.todolist.interfaces.IItemClickListener;
import mcssoft.com.todolist.utility.Resources;

public class MainViewHolder extends ParentViewHolder implements View.OnClickListener {


    public MainViewHolder(View view) {
        super(view);
        tvEmpty = (TextView) view.findViewById(R.id.id_tv_empty);
        tvEmpty.setText(Resources.getInstance().getString(R.string.nothing_to_show));
    }

    public MainViewHolder(View view, IItemClickListener iclistener) {
        super(view);
        tvTitle = (TextView) view.findViewById(R.id.id_tv_shopping_title);
        tvDate = (TextView) view.findViewById(R.id.id_tv_date);
        tvNumItems = (TextView) view.findViewById(R.id.id_tv_num_items);
        tvItems = (TextView) view.findViewById(R.id.id_tv_items);
        ivDelete = (ImageView) view.findViewById(R.id.id_iv_delete);
        ivDelete.setOnClickListener(this);
        ivExpand = (ImageView) view.findViewById(R.id.id_iv_expand);
        ivExpand.setOnClickListener(this);
        this.icListener = iclistener;
    }

    @Override
    public void onClick(View view) {
        int position = getAdapterPosition(); // debug/testing purposes.
        icListener.onItemClick(view, position);
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Accessors">
    public TextView getTvTitle() { return tvTitle; }
    public TextView getTvDate() { return tvDate; }
    public TextView getTvNumItems() { return tvNumItems; }
    public TextView getTvItems() { return tvItems; }
    public ImageView getIvExpand() { return ivExpand; }
    public ImageView getIvDelete() { return ivDelete; }
    public TextView getEmptyView() { return tvEmpty; }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Private vars">
    private TextView tvTitle;
    private TextView tvDate;
    private TextView tvNumItems;
    private TextView tvItems;
    private TextView tvEmpty;
    private ImageView ivExpand;
    private ImageView ivDelete;
    private IItemClickListener icListener;
    //</editor-fold>
}
