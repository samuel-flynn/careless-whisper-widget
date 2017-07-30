package com.flynnsam.carelesswhisperwidget.activity;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.flynnsam.carelesswhisperwidget.R;
import com.flynnsam.carelesswhisperwidget.options.PlaybackType;
import com.flynnsam.carelesswhisperwidget.preferences.PreferencesManager;

/**
 * The configuration class for the widget initialization activity.
 * Created by sam on 2017-07-29.
 */
public class CarelessWhisperWidgetConfigureActivity extends AppCompatActivity {

    private static final String LOGGER_TAG = CarelessWhisperWidgetConfigureActivity.class.getName();

    protected PlaybackType selectedPlaybackOption = null;

    protected SparseArray<PlaybackType> playbackTypeRadioOptions = new SparseArray<>();

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(LOGGER_TAG, "Starting configure activity");

        setContentView(R.layout.activity_careless_whisper_widget_configure);

        Button okButton = (Button) findViewById(R.id.okButton);
        okButton.setOnClickListener(new OkOnClickListener());

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.playback_option_radio_group);
        radioGroup.setOnCheckedChangeListener(new PlaybackRadioCheckChangedListener());

        boolean defaultOption = true;

        for (PlaybackType playbackType : PlaybackType.values()) {

            RadioButton playbackOption = new RadioButton(this);

            radioGroup.addView(playbackOption);

            int radioButtonId = playbackOption.getId();

            Log.d(LOGGER_TAG, String.format("Adding [%2$s] as radio button ID [%1$d]",
                    radioButtonId, playbackType.toString()));

            playbackTypeRadioOptions.put(radioButtonId, playbackType);
            playbackOption.setText(getText(playbackType.getLabelResId()));


            if (defaultOption) {
                playbackOption.setChecked(true);
                selectedPlaybackOption = playbackType;
                defaultOption = false;
            }
        }
    }

    /**
     * Click listener for the OK button on the configuration activity.
     */
    private class OkOnClickListener implements View.OnClickListener {

        /**
         * {@inheritDoc}
         */
        @Override
        public void onClick(View v) {

            if (selectedPlaybackOption != null) {

                Intent intent = getIntent();

                int widgetId = intent.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

                Log.d(LOGGER_TAG, String.format("Saving preference [%2$s] for widget ID [%1$s]", widgetId, selectedPlaybackOption));

                new PreferencesManager(v.getContext()).putPlaybackTypePref(widgetId, selectedPlaybackOption);

                Intent result = new Intent();
                result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
                setResult(RESULT_OK, result);
                finish();
            }
        }
    }

    /**
     * Listener for a change in the radio buttons.
     */
    private class PlaybackRadioCheckChangedListener implements RadioGroup.OnCheckedChangeListener {

        /**
         * {@inheritDoc}
         */
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

            Log.d(LOGGER_TAG, String.format("Radio button changed to [%1$d]", checkedId));

            selectedPlaybackOption = playbackTypeRadioOptions.get(checkedId);
        }
    }
}
