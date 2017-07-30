package com.flynnsam.carelesswhisperwidget.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.flynnsam.carelesswhisperwidget.R;
import com.flynnsam.carelesswhisperwidget.options.PlaybackType;

/**
 * Utility class for managing widget preferences
 * Created by sam on 2017-07-30.
 */

public class PreferencesManager {

    private Context context;

    /**
     * Create a new preference manager.
     * @param context Android context used to get shared preferences store
     */
    public PreferencesManager(Context context) {
        this.context = context;
    }

    /**
     * Put the playback type preference for a given widget to the shared preferences store.
     * @param widgetId The widget ID to put the preference for
     * @param playbackTypeForWidget The selected playback option
     */
    public void putPlaybackTypePref(int widgetId, PlaybackType playbackTypeForWidget) {

        String preferenceKey = getPreferenceKey(widgetId);

        getPreferenceStore()
                .edit()
                .putString(preferenceKey, playbackTypeForWidget.toString())
                .apply();

    }

    /**
     * Get the playback type preference for a given widget from the shared preferences store.
     * @param widgetId The widget ID to get the preference for
     * @return The playback type preference for that widget, or {@code null} if either no
     * preference for that widget ID exists in the store, or if the stored option is unknown
     */
    public PlaybackType getPlaybackTypePref(int widgetId) {

        String preferenceKey = getPreferenceKey(widgetId);

        String playbackOptionStr = getPreferenceStore()
                .getString(preferenceKey, null);

        if (playbackOptionStr == null) {
            return null;
        }

        try {
            return PlaybackType.valueOf(playbackOptionStr);

        } catch (IllegalArgumentException e) {
            Log.w(this.getClass().getName(), String.format("Unknown playback type string [%1$s]", playbackOptionStr));
            return null;
        }
    }

    /**
     * Get the preference key to use for a given widget ID.
     * @param widgetId Widget ID to get the preference key for
     * @return The preference key to use in the preference store
     */
    private String getPreferenceKey(int widgetId) {
        return context.getResources().getString(R.string.preference_playback_option) + String.valueOf(widgetId);
    }

    /**
     * Get the preference store to use for this widget.
     * @return the preference store
     */
    private SharedPreferences getPreferenceStore() {
        return context.getSharedPreferences(context.getResources().getString(R.string.preference_repo_name), Context.MODE_PRIVATE);
    }
}
