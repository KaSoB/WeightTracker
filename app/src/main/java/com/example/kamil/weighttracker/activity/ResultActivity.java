package com.example.kamil.weighttracker.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
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
import com.example.kamil.weighttracker.model.WeightResult;
import com.example.kamil.weighttracker.utils.DecimalDigitsInputFilter;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    ResultsArrayAdapter resultsArrayAdapter;
    WeightDbHelper DbHelper;
    ArrayList<WeightResult> results;
    TextView resultTextView;
    ListView listView;
    WeightResult selectedItem;
    EditText resultEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultEditText = findViewById(R.id.ResultEditText);
        resultEditText.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(3,1)}); // TODO extract values from activities
        resultTextView = findViewById(R.id.ResultTextView);

        DbHelper = new WeightDbHelper(this);

        results = DbHelper.getData(); //TODO: Find another place;
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
                selectedItem = (WeightResult)adapterView.getItemAtPosition(i);
                String data = EditCalendar.getDateLongToString(selectedItem.getDataLong());
                String weight = WeightResult.getWeightIntToString(selectedItem.getWeightInGrams());
                resultTextView.setText(data + " " + weight);
                resultEditText.setText(weight);
            }
        });
    }

    public void EditOnClick(View view){ // TODO: REFACTOR OnClicks
        if(selectedItem != null) {
            int weightInGrams = WeightResult.getWeightStringToInt(resultEditText.getText().toString());
            selectedItem.setWeightInGrams(weightInGrams);
            DbHelper.updateResult(selectedItem.getId(),selectedItem);

            resultsArrayAdapter.notifyDataSetChanged();
            selectedItem = null;
            resultTextView.setText("");
            resultEditText.setText("");
        }
    }
    public void DeleteOnClick(View view){
        if(selectedItem != null){
            DbHelper.deleteResult(selectedItem.getId());
            results.remove(selectedItem);

            resultsArrayAdapter.notifyDataSetChanged();
            selectedItem = null;
            resultTextView.setText("");
            resultEditText.setText("");
        }
    }

}
