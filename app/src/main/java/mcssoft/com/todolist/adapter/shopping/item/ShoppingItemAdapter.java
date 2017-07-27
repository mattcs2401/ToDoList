package mcssoft.com.todolist.adapter.shopping.item;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mcssoft.com.todolist.interfaces.IItemClickListener;
import mcssoft.com.todolist.R;
import mcssoft.com.todolist.model.items.ShoppingItemsList;

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

    public void setData(ShoppingItemsList shoppingItemsList) {
        this.shoppingItemsList = shoppingItemsList;
        notifyDataSetChanged();
    }

    public void setCheck(int position, boolean setCheck) {
        if(setCheck) {
            shoppingItemsList.get(position).setRefSelect("Y");
        } else {
            shoppingItemsList.get(position).setRefSelect("N");
        }
        notifyItemChanged(position);
    }

    private ShoppingItemViewHolder svh;
    private IItemClickListener icListener;
    private ShoppingItemsList shoppingItemsList; // backing data.
}
