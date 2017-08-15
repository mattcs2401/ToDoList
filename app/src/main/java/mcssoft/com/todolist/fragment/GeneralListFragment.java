package mcssoft.com.todolist.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.adapter.general.GeneralAdapter;
import mcssoft.com.todolist.database.Database;
import mcssoft.com.todolist.database.Schema;
import mcssoft.com.todolist.interfaces.IItemClickListener;


public class GeneralListFragment extends Fragment implements IItemClickListener {

    //<editor-fold defaultstate="collapsed" desc="Region: Lifecycle">
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        args = getArguments();    // TBA.
        setData();                // set backing data.
        setAdapter();             // set adapter associated with the recycler view.
        setRecyclerView(rootView);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Interface">
    /**
     * Interface IItemClickListener returns here.
     * @param view The selected Adapter item view.
     * @param position Row position of the Adapter's item.
     */
    @Override
    public void onItemClick(View view, int position) {
        String tba = "TBA";
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Utility">
    private int getDbRowId(int position) {
        adapter.getItemId(position);
        Cursor cursor = adapter.getCursor();
        int dbRowId = cursor.getInt(cursor.getColumnIndex(Schema.GENERAL_ROWID));
        return dbRowId;
    }

    private void setData() {
        cursor = Database.getInstance().getAllGeneral();
    }

    private void setAdapter() {
        adapter = new GeneralAdapter();
        if(cursor == null || cursor.getCount() < 1) {
            adapter.setEmptyView(true);
        } else {
            adapter.setEmptyView(false);
            adapter.setData(cursor);
        }
        adapter.setOnItemClickListener(this);
     }

    private void setRecyclerView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.id_rv_fragment);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.scrollToPosition(0);
        recyclerView.setLayoutManager(llm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    //</editor-fold>

    private Bundle args;
    private Cursor cursor;
    private View rootView;
    private GeneralAdapter adapter;
}
