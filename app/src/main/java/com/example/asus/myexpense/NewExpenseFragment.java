package com.example.asus.myexpense;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by ASUS on 2017/3/1.
 */

public class NewExpenseFragment extends Fragment{

    Database DBController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        DBController = new Database(getActivity());

        return inflater.inflate(R.layout.newexpense, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        super.onViewCreated(view, savedInstanceState);
        final EditText description = (EditText) view.findViewById(R.id.description);
        final EditText amount = (EditText) view.findViewById(R.id.amount);
        final TextView datePickerTextView = (TextView) view.findViewById(R.id.datePicker);

        datePickerTextView.setInputType(InputType.TYPE_NULL);
        datePickerTextView.requestFocus();

        view.findViewById(R.id.datePicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int yyyy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date =  String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear+1) + "-" + String.valueOf(year);
                        datePickerTextView.setText(date);
                    }
                }, dd, mm, yyyy);

                //initialize date picker calender with today's date
                datePicker.updateDate(yyyy, mm, dd);
                datePicker.setTitle("");
                datePicker.show();
            }
        });

        amount.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                amount.removeTextChangedListener(this);
                try {
                    String givenstring = s.toString();
                    double doubleVal;
                    if (givenstring.contains(",")) {
                        givenstring = givenstring.replaceAll(",", "");
                    }
                    doubleVal = Double.parseDouble(givenstring);
                    String formattedString = DateFormat.formatValue(doubleVal);
                    amount.setText(formattedString);
                    amount.setSelection(amount.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                amount.addTextChangedListener(this);
            }
        });




        Button add_expense_btn = (Button) view.findViewById(R.id.add_expense_btn);

        add_expense_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                //Add expense
                String description_text = description.getText().toString();
                String amount_string    = amount.getText().toString();
                String date_text        = datePickerTextView.getText().toString();
                double amount_val;

                try
                {amount_val = Double.parseDouble(amount_string.replaceAll(",", ""));}
                catch (NumberFormatException e)
                {
                    Toast.makeText(getActivity().getApplicationContext(),R.string.add_expense_error, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(description_text) || TextUtils.isEmpty(amount_string) || TextUtils.isEmpty(date_text))
                {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.add_expense_error, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    addExpense(description_text, amount_val, date_text);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction
                            .replace(R.id.containerView,new TabFragment())
                            .addToBackStack(null)
                            .commit();
                    Toast.makeText(getActivity().getApplicationContext(), R.string.add_expense_success, Toast.LENGTH_SHORT).show();
                }

            }//TabFragment.viewPager.setCurrentItem(2);
        }

        );
    }

    public void addExpense(String description, double amount, String date){
        Expense expense = new Expense(description, amount, date);
        DBController.addExpense(expense);
    }


}
