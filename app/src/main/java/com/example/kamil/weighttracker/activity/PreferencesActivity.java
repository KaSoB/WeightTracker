package com.example.kamil.weighttracker.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import com.example.kamil.weighttracker.R;
import com.example.kamil.weighttracker.model.Weight;

/**
 * Created by Kamil on 15.11.2017.
 */

public class PreferencesActivity extends PreferenceActivity {
    public static Weight.Unit unit; // TODO: Non static value
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }
    public static class MyPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
