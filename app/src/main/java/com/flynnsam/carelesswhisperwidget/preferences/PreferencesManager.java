package com.flynnsam.carelesswhisperwidget.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.flynnsam.carelesswhisperwidget.R;
import com.flynnsam.carelesswhisperwidget.options.PlaybackType;

/**
 * Created by admin on 2017-07-30.
 */

public class PreferencesManager {

    private Context context;

    public PreferencesManager(Context context) {
        this.context = context;
    }

    public void putPlaybackTypePref(int widgetId, PlaybackType playbackTypeForWidget) {

        String preferenceKey = getPreferenceKey(widgetId);

        getPreferenceStore()
                .edit()
                .putString(preferenceKey, playbackTypeForWidget.toString())
                .apply();

    }

    public PlaybackType getPlaybackTypePref(int widgetId) {

        String preferenceKey = getPreferenceKey(widgetId);

        String playbackOptionStr = getPreferenceStore()
                .getString(preferenceKey, null);

        if (playbackOptionStr == null) {
            return null;
        }

        PlaybackType playbackType = PlaybackType.valueOf(playbackOptionStr);

        if (playbackType == null) {
            Log.w(this.getClass().getName(), String.format("Unknown playback type string [%1$s]", playbackOptionStr));
            return null;
        }

        return playbackType;
    }

    private String getPreferenceKey(int widgetId) {
        return context.getResources().getString(R.string.preference_playback_option) + String.valueOf(widgetId);
    }

    private SharedPreferences getPreferenceStore() {
        return context.getSharedPreferences(context.getResources().getString(R.string.preference_repo_name), context.MODE_PRIVATE);
    }
}
