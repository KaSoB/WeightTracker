package com.example.kamil.weighttracker.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;

import com.example.kamil.weighttracker.utils.DecimalDigitsInputFilter;
import com.example.kamil.weighttracker.model.EditCalendar;
import com.example.kamil.weighttracker.R;
import com.example.kamil.weighttracker.database.WeightDbHelper;
import com.example.kamil.weighttracker.model.WeightResult;

public class MainActivity extends AppCompatActivity {
    EditText weightEditText;
    EditText dateEditText;
    EditCalendar calendar;
    WeightDbHelper DbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DbHelper = new WeightDbHelper(this);

        weightEditText = findViewById(R.id.WeightEditText);
        weightEditText.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(3,1)});

        dateEditText = findViewById(R.id.DateEditText);
        calendar = new EditCalendar(this, dateEditText);
    }

    public void resultsButtonOnClick(View view){
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
    }
    public void addWeightOnClick(View view) {
        DbHelper.insertResult(loadResultFromEditTexts());
    }

    public WeightResult loadResultFromEditTexts(){
        int weightInGrams = WeightResult.getWeightStringToInt(weightEditText.getText().toString());
        long calendarLongDate = calendar.getLongDate();

        return new WeightResult(weightInGrams,calendarLongDate);
    }
}
