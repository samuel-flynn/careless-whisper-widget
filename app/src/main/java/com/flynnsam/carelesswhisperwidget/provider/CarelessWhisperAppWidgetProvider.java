package com.flynnsam.carelesswhisperwidget.provider;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.widget.RemoteViews;

import com.flynnsam.soundboardmediaplayer.MediaPlayerProvider;
import com.flynnsam.carelesswhisperwidget.R;

/**
 * The widget provider that registers the button's action and handles that action's reception.
 *
 * Created by Sam on 2017-01-20.
 */
public class CarelessWhisperAppWidgetProvider extends AppWidgetProvider {

    private static final int PLAY_ACTION_RESOUCE = R.string.play_action;

    private static final int CW_INTRO_RESOURCE = R.raw.intro;

    private static final int CW_LOOP_RESOURCE = R.raw.loop;

    private static final int CW_OUTRO_RESOURCE = R.raw.outro;

    public MediaPlayerProvider mediaPlayer = null;

    @Override
    public void onUpdate(Context context, AppWidgetManager widgetManager, int[] appWidgetIds) {

        String playActionStr = getPlayActionStr(context);

        for (int widgetId : appWidgetIds) {

            int buttonID = R.id.airhornblaster;
            int layoutID = R.layout.widgetbutton;

            Intent playAirHornIntent = new Intent(context, this.getClass());
            playAirHornIntent.setAction(playActionStr);
            playAirHornIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, playAirHornIntent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), layoutID);
            views.setOnClickPendingIntent(buttonID, pIntent);

            widgetManager.updateAppWidget(widgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        super.onReceive(context, intent);
        String playActionStr = getPlayActionStr(context);

        int widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            throw new RuntimeException("Unable to retrieve widget ID"); // TODO throw a more appropriate exception
        }

        if (playActionStr.equals(intent.getAction())) {
            synchronized (this) {
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayerProvider();
                }
            }

            // TODO pickup point for tomorrow: How do I play queue what I need, if necessary, interrupt otherwise
            mediaPlayer.play(context, resourcesToPlay);
        }
    }

    protected void handleMaxVolumePref(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_repo_name),
                Context.MODE_PRIVATE);

        boolean setMaxVol = prefs.getBoolean(
                context.getResources().getString(R.string.max_volume_preference_key), false);

        if (setMaxVol) {
            AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            audio.setStreamVolume(AudioManager.STREAM_MUSIC, audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        }
    }

    public static String getPlayActionStr(final Context context) {
        return context.getResources().getString(PLAY_ACTION_RESOUCE);
    }
}
