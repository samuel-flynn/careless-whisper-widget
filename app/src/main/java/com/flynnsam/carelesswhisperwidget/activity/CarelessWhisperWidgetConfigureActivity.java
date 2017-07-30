package com.flynnsam.carelesswhisperwidget.activity;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.flynnsam.carelesswhisperwidget.R;
import com.flynnsam.carelesswhisperwidget.provider.CarelessWhisperAppWidgetProvider;

public class CarelessWhisperWidgetConfigureActivity extends AppCompatActivity {

    boolean maxVolumeOnClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_air_hown_widget_configure);

        Button okButton = (Button) findViewById(R.id.okButton);

        okButton.setOnClickListener(new OkOnClickListener());
    }

    protected class OkOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            CheckBox maxVolumeCheckBox = (CheckBox) findViewById(R.id.maxVolumeCheckBox);

            getSharedPreferences(getResources().getString(R.string.preference_repo_name), MODE_PRIVATE)
                    .edit()
                    .putBoolean(CarelessWhisperAppWidgetProvider.MAX_VOLUME_PREFERENCE_KEY, maxVolumeCheckBox.isChecked())
                    .commit();

            Intent intent = getIntent();
            Bundle extras = intent.getExtras();
            int appWidgetId = -1;
            if (extras != null) {
                appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID);
            } else {
                throw new IllegalStateException("Unable to get intent extras.");
            }Intent result = new Intent();
            result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            setResult(RESULT_OK, result);
            finish();
        }
    }
}
