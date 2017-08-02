package mcssoft.com.todolist.fragment.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.interfaces.INothingSelected;
import mcssoft.com.todolist.utility.Resources;

public class NothingSelectedFragment extends DialogFragment
        implements DialogInterface.OnClickListener {

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        iNothingSelected = (INothingSelected) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity()); //, AlertDialog.THEME_HOLO_LIGHT);
        ab.setTitle(Resources.getInstance().getString(R.string.nothing_selected_title))
          .setMessage(Resources.getInstance().getString(R.string.nothing_selected_message))
          .setNegativeButton(Resources.getInstance().getString(R.string.btn_cancel), this)
          .setPositiveButton(Resources.getInstance().getString(R.string.btn_continue), this);

        return ab.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case AlertDialog.BUTTON_POSITIVE:
                iNothingSelected.iNoSelect(true);
                break;
            case AlertDialog.BUTTON_NEGATIVE:
                iNothingSelected.iNoSelect(false);
                break;
        }
    }

    public INothingSelected iNothingSelected = null;
}
