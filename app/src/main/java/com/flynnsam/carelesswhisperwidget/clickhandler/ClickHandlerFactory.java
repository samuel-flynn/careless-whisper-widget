package com.flynnsam.carelesswhisperwidget.clickhandler;

import com.flynnsam.carelesswhisperwidget.options.PlaybackType;

/**
 * Created by admin on 2017-07-30.
 */

public class ClickHandlerFactory {

    public ClickHandler createHandler(PlaybackType playbackTypeOption) {
        switch (playbackTypeOption) {
            case ONE_TIME:
                return new OneTimeClickHandler();
            case LOOP:
                return new LoopClickHandler();
            default:
                throw new UnsupportedOperationException("A click handler hasn't been defined for playback type: "
                        + playbackTypeOption.toString());
        }
    }
}
