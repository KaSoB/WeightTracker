package com.example.kamil.weighttracker.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kamil.weighttracker.model.Weight;
import com.example.kamil.weighttracker.utils.DecimalDigitsInputFilter;
import com.example.kamil.weighttracker.model.EditCalendar;
import com.example.kamil.weighttracker.R;
import com.example.kamil.weighttracker.database.WeightDbHelper;
import com.example.kamil.weighttracker.model.Result;

import static com.example.kamil.weighttracker.activity.PreferencesActivity.unit;

public class MainActivity extends AppCompatActivity {
    private WeightDbHelper weightDb;
    private EditCalendar calendar;

    // LAYOUT
    private EditText weightEditText;
    private TextView weightUnitTextView;
    //

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add toolbar to activity_main
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        weightDb = new WeightDbHelper(this);

        weightUnitTextView = findViewById(R.id.WeightUnitTextView);
        setWeightUnit();

        weightEditText = findViewById(R.id.WeightEditText);
        weightEditText.setFilters(new InputFilter[]{ new DecimalDigitsInputFilter(
                getResources().getInteger(R.integer.WeightDigitsBeforeZero),
                getResources().getInteger(R.integer.WeightDigitsAfterZero))});

        EditText dateEditText = findViewById(R.id.DateEditText);
        calendar = new EditCalendar(this, dateEditText);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setWeightUnit();
    }
    private void setWeightUnit(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String textUnit = sharedPreferences.getString(getResources().getString(R.string.MassUnitsKey),"");
        unit = Weight.Unit.valueOf(textUnit);
        weightUnitTextView.setText(unit.toString());
    }
    public void resultsButtonOnClick(View view){
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
    }
    public void addWeightOnClick(View view) {
        weightDb.insertResult(createResult());
    }

    private Result createResult(){
        int weight = Weight.valueOf(weightEditText.getText().toString(),unit);
        long calendarLongDate = calendar.getLongDate();
        return new Result(calendarLongDate, new Weight(weight, Weight.Unit.g));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.SettingsItemMenu:
                startActivity(new Intent(this, PreferencesActivity.class));
                break;

            default:
                // leave empty
        }
        return super.onOptionsItemSelected(item);
    }
}
