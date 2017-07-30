package com.flynnsam.carelesswhisperwidget.clickhandler;

import android.content.Context;

import com.flynnsam.soundboardmediaplayer.MediaPlayerProvider;

/**
 * Created by admin on 2017-07-30.
 */

public interface ClickHandler {

    void handleClick(Context context, MediaPlayerProvider mediaPlayerProvider);
}
