package com.example.asus.myexpense;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by ASUS on 2017/3/1.
 */

public class NewExpense extends Activity implements View.OnClickListener{

    TextView datePicker;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newexpense);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        datePicker = (TextView) findViewById(R.id.datePicker);
        datePicker.setInputType(InputType.TYPE_NULL);
        datePicker.requestFocus();
        setDateTimeField();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         return super.onOptionsItemSelected(item);
    }

    private void setDateTimeField() {
        datePicker.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                datePicker.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {
        fromDatePickerDialog.show();
    }


}
