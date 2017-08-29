package mcssoft.com.todolist.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import mcssoft.com.todolist.R;
import mcssoft.com.todolist.utility.Resources;
import mcssoft.com.todolist.utility.ToDoEditText;

/**
 * Class to capture the value details entered for General Item.
 */
public class GeneralItemValue extends DialogFragment implements DialogInterface.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Bundle args = getArguments();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.layout_general_value, null);

        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
        ab.setView(rootView)
          .setTitle(Resources.getInstance().getString(R.string.giv_dialog_title))
          .setMessage(Resources.getInstance().getString(R.string.giv_dialog_message))
          .setNegativeButton(Resources.getInstance().getString(R.string.btn_cancel), this)
          .setPositiveButton(Resources.getInstance().getString(R.string.btn_ok), this);

        return ab.create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case AlertDialog.BUTTON_POSITIVE:
                // TBA.
//                break;
//            case AlertDialog.BUTTON_NEGATIVE:
        }
    }

    private View rootView;
    private ToDoEditText itemValueText;
}
