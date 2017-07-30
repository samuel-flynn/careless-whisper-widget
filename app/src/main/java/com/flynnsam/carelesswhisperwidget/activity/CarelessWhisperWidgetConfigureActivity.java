package com.flynnsam.carelesswhisperwidget.activity;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.flynnsam.carelesswhisperwidget.R;
import com.flynnsam.carelesswhisperwidget.options.PlaybackType;
import com.flynnsam.carelesswhisperwidget.preferences.PreferencesManager;

public class CarelessWhisperWidgetConfigureActivity extends AppCompatActivity {

    protected String selectedPlaybackOption = null;

    protected SparseArray<PlaybackType> playbackTypeRadioOptions = new SparseArray<PlaybackType>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_careless_whisper_widget_configure);

        Button okButton = (Button) findViewById(R.id.okButton);
        okButton.setOnClickListener(new OkOnClickListener());

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.playback_option_radio_group);
        radioGroup.setOnCheckedChangeListener(new PlaybackRadioCheckChangedListener());

        boolean defaultOption = true;

        for (PlaybackType playbackType : PlaybackType.values()) {

            RadioButton playbackOption = new RadioButton(this);

            playbackTypeRadioOptions.put(playbackOption.getId(), playbackType);
            playbackOption.setText(getText(playbackType.getLabelResId()));


            if (defaultOption) {
                playbackOption.setSelected(true);
                selectedPlaybackOption = playbackType.toString();
            }

            radioGroup.addView(playbackOption);
        }
    }

    protected class OkOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            Intent intent = getIntent();

            int widgetId = intent.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

            new PreferencesManager(v.getContext()).putPlaybackTypePref(widgetId, PlaybackType.valueOf(selectedPlaybackOption));

            Intent result = new Intent();
            result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            setResult(RESULT_OK, result);
            finish();
        }
    }

    protected class PlaybackRadioCheckChangedListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            selectedPlaybackOption = playbackTypeRadioOptions.get(checkedId).toString();
        }
    }
}
