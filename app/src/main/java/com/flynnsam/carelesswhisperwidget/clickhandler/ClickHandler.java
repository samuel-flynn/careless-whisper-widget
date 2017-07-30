package com.flynnsam.carelesswhisperwidget.clickhandler;

import android.content.Context;

import com.flynnsam.soundboardmediaplayer.MediaPlayerProvider;

/**
 * Interface for handling widget clicks and starting, stopping, and queueing the correct tracks for
 * a given {@link com.flynnsam.carelesswhisperwidget.options.PlaybackType}. Use {@link ClickHandlerFactory}
 * to create the correct instance.
 * Created by sam on 2017-07-30.
 */

public interface ClickHandler {

    /**
     * Given a click on the widget, take the current {@link MediaPlayerProvider}'s state and determine
     * what should be played next.
     * @param context The initiating android context
     * @param mediaPlayerProvider The media player provider that is responsible for playing sounds
     */
    void handleClick(Context context, MediaPlayerProvider mediaPlayerProvider);
}
