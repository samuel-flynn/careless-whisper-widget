package com.flynnsam.carelesswhisperwidget.provider;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.flynnsam.carelesswhisperwidget.R;
import com.flynnsam.carelesswhisperwidget.clickhandler.ClickHandler;
import com.flynnsam.carelesswhisperwidget.clickhandler.ClickHandlerFactory;
import com.flynnsam.carelesswhisperwidget.options.PlaybackType;
import com.flynnsam.carelesswhisperwidget.preferences.PreferencesManager;
import com.flynnsam.soundboardmediaplayer.MediaPlayerProvider;

/**
 * The widget provider that registers the button's action and handles that action's reception.
 * <p>
 * Created by Sam on 2017-01-20.
 */
public class CarelessWhisperAppWidgetProvider extends AppWidgetProvider {

    private static final int PLAY_ACTION_RESOUCE = R.string.play_action;

    private static final ClickHandlerFactory clickHandlerFactory = new ClickHandlerFactory();

    private MediaPlayerProvider mediaPlayer = null;

    private static String getPlayActionStr(final Context context) {
        return context.getResources().getString(PLAY_ACTION_RESOUCE);
    }

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

            PlaybackType widgetPlaybackType = new PreferencesManager(context).getPlaybackTypePref(widgetId);

            ClickHandler clickHandler = clickHandlerFactory.createHandler(widgetPlaybackType);

            clickHandler.handleClick(context, mediaPlayer);
        }
    }
}
