package edu.pdx.jwaldrip.ece558f16.project4;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;


/**
 * Created by jonathanwaldrip on 12/3/16.
 * Code Source BNRG Criminal Intent exercise
 */

public class ConfirmDeleteFragment extends DialogFragment {

    LoginDataBaseAdapter loginDataBaseAdapter;

    public static ConfirmDeleteFragment newInstance() {
        Bundle args = new Bundle();
        ConfirmDeleteFragment fragment = new ConfirmDeleteFragment();
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Creates object for LoginDataBaseAdapter to gain access to database
        loginDataBaseAdapter=new LoginDataBaseAdapter(MainActivity.context);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        // Inflate layout for dialog box
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_confirm_dialog, null);

        // Resturn dialog box with listeners for the confirm and cancel buttons
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.dialog_confirm_delete)
                .setPositiveButton(R.string.confirm_delete,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                loginDataBaseAdapter.clearAll();
                                Toast.makeText(MainActivity.context, ("History Cleared"), Toast.LENGTH_SHORT).show();
                            }
                        })
                .setNegativeButton(R.string.cancel_delete,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                .create();
    }

}