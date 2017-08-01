package mcssoft.com.todolist.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.adapter.shopping.item.ShoppingItemAdapter;
import mcssoft.com.todolist.interfaces.IItemClickListener;
import mcssoft.com.todolist.interfaces.IShoppingListItemSelect;
import mcssoft.com.todolist.model.items.ShoppingItemsList;
import mcssoft.com.todolist.utility.Resources;


public class ShoppingItemFragment extends Fragment implements IItemClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.shopping_item_fragment, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        iItemSelect = (IShoppingListItemSelect) getActivity();
        pageNo = getArguments().getInt(Resources.getInstance().getString(R.string.bundle_key));
        setShoppingAdapter();     // set adapter associated with the recycler view.
        setRecyclerView(rootView);// set the recycler view.
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Interface">
    /**
     * Interface IItemClickListener returns here.
     * @param view The selected Adapter item view.
     * @param position Row position of the Adapter's item.
     */
    @Override
    public void onItemClick(View view, int position) {
        // from shopping item view holder.
        if(view instanceof CheckBox) {
            if(((CheckBox) view.findViewById(R.id.id_cb_shopping_item)).isChecked()) {
                adapter.setCheck(position, true);
                iItemSelect.iItemSelected(pageNo, position, true);
            } else {
                adapter.setCheck(position, false);
                iItemSelect.iItemSelected(pageNo, position, false);
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Utility">
    private void setShoppingAdapter() {
        String key = Resources.getInstance().getString(R.string.bundle_data_key);
        adapter = new ShoppingItemAdapter();
        adapter.setData((ShoppingItemsList) getArguments().getParcelable(key));
        adapter.setOnItemClickListener(this);
     }

    private void setRecyclerView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.id_rv_shopping_item);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.scrollToPosition(0);
        recyclerView.setLayoutManager(llm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    //</editor-fold>

    private int pageNo;
    private View rootView;
    private ShoppingItemAdapter adapter;
    private IShoppingListItemSelect iItemSelect;
}
