package com.example.asus.myexpense;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.List;

/**
 * Created by ASUS on 2017/3/1.
 */

public class ListViewFragment extends ListFragment implements OnItemClickListener{

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Database DBController = new Database(getActivity());
        View rootView = inflater.inflate(R.layout.expense_list, container, false);
        List values = DBController.getExpensesList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Database DBController = new Database(getActivity());
        List values = DBController.getExpensesList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity() );
        // set title
        alertDialogBuilder.setTitle("Delete");
        // set dialog message
        alertDialogBuilder
                .setMessage(R.string.delete_confirmation)
                .setCancelable(false)
                //Decision=yes
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        Database DBController = new Database(getActivity());
                        DBController.deleteExpense(position);
                        Toast.makeText(getActivity(),"Deleted", Toast.LENGTH_SHORT).show();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction
                                .replace(R.id.containerView,new TabFragment())
                                .addToBackStack(null)
                                .commit();
                        }
                })
                //Decision=no
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        }


}

