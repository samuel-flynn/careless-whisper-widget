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

    private static final String PLAY_ACTION = "com.flynnsam.carelesswhisperwidget.PlayAction";

    private static final int CW_INTRO_RESOURCE = R.raw.intro;

    private static final int CW_LOOP_RESOURCE = R.raw.loop;

    private static final int CW_OUTRO_RESOURCE = R.raw.outro;

    public MediaPlayerProvider mediaPlayer = null;

    @Override
    public void onUpdate(Context context, AppWidgetManager widgetManager, int[] appWidgetIds) {

        for (int widgetId : appWidgetIds) {

            int buttonID = R.id.airhornblaster;
            int layoutID = R.layout.widgetbutton;

            Intent playAirHornIntent = new Intent(context, this.getClass());
            playAirHornIntent.setAction(PLAY_ACTION);
            PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, playAirHornIntent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), layoutID);
            views.setOnClickPendingIntent(buttonID, pIntent);

            widgetManager.updateAppWidget(widgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (PLAY_ACTION.equals(intent.getAction())) {
            handleMaxVolumePref(context);
            synchronized (this) {
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayerProvider();
                }
            }
            mediaPlayer.play(context);
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
}
