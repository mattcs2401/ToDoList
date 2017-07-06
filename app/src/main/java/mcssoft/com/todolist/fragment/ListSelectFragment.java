package mcssoft.com.todolist.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.interfaces.IListSelect;
import mcssoft.com.todolist.utility.Resources;

public class ListSelectFragment extends DialogFragment
        implements DialogInterface.OnClickListener {

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Setup the interface.
        iListSelect = (IListSelect) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_type_select, null);

        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
        ab.setView(view)
          .setTitle(Resources.getInstance().getString(R.string.list_select_title))
          .setMessage(Resources.getInstance().getString(R.string.list_select_message))
          .setNegativeButton(Resources.getInstance().getString(R.string.btn_cancel), this)
          .setPositiveButton(Resources.getInstance().getString(R.string.btn_ok), this);

        return ab.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case AlertDialog.BUTTON_POSITIVE:
                int id = ((RadioGroup) view.findViewById(R.id.id_rg_list_select)).getCheckedRadioButtonId();
                iListSelect.iSelected(id);
                break;
            case AlertDialog.BUTTON_NEGATIVE:
                iListSelect.iSelected(-1);
        }
    }

    private View view;
    public IListSelect iListSelect = null;
}
