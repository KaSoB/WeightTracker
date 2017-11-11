package com.example.kamil.weighttracker.model;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Kamil on 11.11.2017.
 */

public class EditCalendar {
    public static final String DATE_STRING_FORMAT = "dd/MM/yyyy";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_STRING_FORMAT, Locale.UK); // TODO

    private Calendar calendar = Calendar.getInstance();
    private EditText dateEditText;

    public EditCalendar(final Context context, EditText editText){
        dateEditText = editText;
        updateEditText();

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(Calendar.YEAR,i);
                        calendar.set(Calendar.MONTH,i1);
                        calendar.set(Calendar.DAY_OF_MONTH,i2);
                        updateEditText();
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateEditText(){
        dateEditText.setText(getStringDate());
    }

    public Date getFormattedDate() throws ParseException {
        return DATE_FORMAT.parse(dateEditText.getText().toString());
    }
    public String getStringDate(){
        return DATE_FORMAT.format(calendar.getTime());
    }

    public long getLongDate(){
        long dateInInteger = 0;
        try {
            Date date = getFormattedDate();
            dateInInteger = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateInInteger;
    }
    public static String getDateLongToString(long date){
        return DATE_FORMAT.format(date);
    }
}
