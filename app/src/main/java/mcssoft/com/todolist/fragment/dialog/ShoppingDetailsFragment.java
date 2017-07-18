package mcssoft.com.todolist.fragment.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.adapter.shopping.details.ShoppingDetailsAdapter;
import mcssoft.com.todolist.database.Database;
import mcssoft.com.todolist.interfaces.IListSelect;
import mcssoft.com.todolist.utility.Resources;

public class ShoppingDetailsFragment extends DialogFragment
        implements DialogInterface.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        dbRowId = args.getInt(Resources.getInstance().getString(R.string.sl_details_rowid_key));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.shopping_details, null);

        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
        ab.setView(rootView)
          .setTitle(Resources.getInstance().getString(R.string.sl_details_dialog_title))
//          .setMessage("")
          .setNegativeButton(Resources.getInstance().getString(R.string.btn_cancel), this)
          .setPositiveButton(Resources.getInstance().getString(R.string.btn_ok), this);

        return ab.create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setCursor();                   // backing data raw form.
        setShoppingDetailsAdapter();   // set adapter associated with the recycler rootView.
        setRecyclerView(rootView);     // set the recycler rootView.
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case AlertDialog.BUTTON_POSITIVE:
//                int id = ((RadioGroup) rootView.findViewById(R.id.id_rg_list_select)).getCheckedRadioButtonId();
//                iListSelect.iSelected(id);
                break;
            case AlertDialog.BUTTON_NEGATIVE:
//                iListSelect.iSelected(-1);
        }
    }

    private void setCursor() {
        cursor = Database.getInstance().getShopping(dbRowId);
    }

    private void setShoppingDetailsAdapter() {
        adapter = new ShoppingDetailsAdapter();
        adapter.swapCursor(cursor);

    }

    private void setRecyclerView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.id_rv_shopping_details);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.scrollToPosition(0);
        recyclerView.setLayoutManager(llm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private View rootView;
    private int dbRowId;
    private Cursor cursor;
    private ShoppingDetailsAdapter adapter;

}
