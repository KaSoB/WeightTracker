package com.example.kamil.weighttracker.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kamil.weighttracker.adapter.ResultsArrayAdapter;
import com.example.kamil.weighttracker.R;
import com.example.kamil.weighttracker.database.WeightDbHelper;
import com.example.kamil.weighttracker.model.EditCalendar;
import com.example.kamil.weighttracker.model.Result;
import com.example.kamil.weighttracker.model.Weight;
import com.example.kamil.weighttracker.utils.DecimalDigitsInputFilter;

import java.util.ArrayList;
import static com.example.kamil.weighttracker.activity.PreferencesActivity.unit;

public class ResultActivity extends AppCompatActivity {
    ResultsArrayAdapter resultsArrayAdapter;
    WeightDbHelper weightDb;
    ArrayList<Result> results;
    Result selectedItem;

    TextView resultTextView;
    ListView listView;
    EditText resultEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultEditText = findViewById(R.id.ResultEditText);
        resultEditText.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(
                getResources().getInteger(R.integer.WeightDigitsBeforeZero),
                getResources().getInteger(R.integer.WeightDigitsAfterZero))});
        resultTextView = findViewById(R.id.ResultTextView);

        weightDb = new WeightDbHelper(this);

        results = weightDb.getData();
        resultsArrayAdapter = new ResultsArrayAdapter(this,R.layout.resultlistviewelement,results);


        listView = findViewById(R.id.ResultsListView);
        listView.setAdapter(resultsArrayAdapter);

        // Add headers to listview
        ViewGroup headerView = (ViewGroup)getLayoutInflater().inflate(R.layout.headerslayout,
                listView,false);
        listView.addHeaderView(headerView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem = (Result)adapterView.getItemAtPosition(i);
                String data = EditCalendar.toString(selectedItem.getDataLong());
                String weight = selectedItem.getWeight().toString(unit);
                resultTextView.setText(data);
                resultEditText.setText(weight);
            }
        });
    }

    public void EditOnClick(View view){
        if(selectedItem != null) {
            int weightInGrams = Weight.valueOf(resultEditText.getText().toString(),unit);
            selectedItem.setWeight(weightInGrams, Weight.Unit.g);
            weightDb.updateResult(selectedItem.getId(),selectedItem);

            resultsArrayAdapter.notifyDataSetChanged();
            selectedItem = null;
            resultTextView.setText("");
            resultEditText.setText("");
        }
    }
    public void DeleteOnClick(View view){
        if(selectedItem != null){
            // delete item from database
            weightDb.deleteResult(selectedItem.getId());
            // delete item from list view
            results.remove(selectedItem);
            resultsArrayAdapter.notifyDataSetChanged();
            selectedItem = null;
            resultTextView.setText("");
            resultEditText.setText("");
        }
    }

}
