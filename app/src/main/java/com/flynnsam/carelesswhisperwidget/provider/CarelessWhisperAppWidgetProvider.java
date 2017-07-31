package com.flynnsam.carelesswhisperwidget.provider;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.flynnsam.carelesswhisperwidget.R;
import com.flynnsam.carelesswhisperwidget.clickhandler.ClickHandler;
import com.flynnsam.carelesswhisperwidget.clickhandler.ClickHandlerFactory;
import com.flynnsam.carelesswhisperwidget.exception.UnconfiguredWidgetException;
import com.flynnsam.carelesswhisperwidget.options.PlaybackType;
import com.flynnsam.carelesswhisperwidget.preferences.PreferencesManager;
import com.flynnsam.soundboardmediaplayer.SoundboardMediaProvider;
import com.flynnsam.soundboardmediaplayer.SoundboardMediaProviderFactory;

/**
 * The widget provider that registers the button's action and handles that action's reception.
 * Created by Sam on 2017-01-20.
 */
public class CarelessWhisperAppWidgetProvider extends AppWidgetProvider {

    private static final String LOGGER_TAG = CarelessWhisperAppWidgetProvider.class.getName();

    private static final int PLAY_ACTION_RESOUCE = R.string.play_action;

    private static final ClickHandlerFactory CLICK_HANDLER_FACTORY = new ClickHandlerFactory();

    private static final SoundboardMediaProvider SOUNDBOARD_MEDIA_PROVIDER = SoundboardMediaProviderFactory.createSoundboardMediaProvider();

    /**
     * Get the play action string name
     * @param context The context used to load the string resource
     * @return The play action string
     */
    private static String getPlayActionStr(final Context context) {
        return context.getResources().getString(PLAY_ACTION_RESOUCE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager widgetManager, int[] appWidgetIds) {

        String playActionStr = getPlayActionStr(context);

        for (int widgetId : appWidgetIds) {

            Log.d(LOGGER_TAG, String.format("On-update for widget with ID [%1$s]", widgetId));

            int buttonID = R.id.airhornblaster;
            int layoutID = R.layout.widgetbutton;

            Intent playButtonIntent = new Intent(context, this.getClass());
            playButtonIntent.setAction(playActionStr);
            playButtonIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            PendingIntent pIntent = PendingIntent.getBroadcast(context, widgetId, playButtonIntent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), layoutID);
            views.setOnClickPendingIntent(buttonID, pIntent);

            widgetManager.updateAppWidget(widgetId, views);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        super.onReceive(context, intent);
        String playActionStr = getPlayActionStr(context);

        if (playActionStr.equals(intent.getAction())) {

            int widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
                throw new UnconfiguredWidgetException("Unable to retrieve widget ID from playAction event.");
            }

            PlaybackType widgetPlaybackType = new PreferencesManager(context).getPlaybackTypePref(widgetId);

            Log.d(LOGGER_TAG, String.format("Handling on-click event for widget ID [%1$s] with playback type [%2$s]", widgetId, widgetPlaybackType));

            ClickHandler clickHandler = CLICK_HANDLER_FACTORY.createHandler(widgetPlaybackType);

            clickHandler.handleClick(context, SOUNDBOARD_MEDIA_PROVIDER);
        }
    }
}
