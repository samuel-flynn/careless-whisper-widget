package com.flynnsam.carelesswhisperwidget.clickhandler;

import com.flynnsam.carelesswhisperwidget.options.PlaybackType;

/**
 * Factory class for creating {@link ClickHandler}s of the correct type for a given playback
 * type preference
 * Created by sam on 2017-07-30.
 */

public class ClickHandlerFactory {

    /**
     * Create the correct {@link ClickHandler} implementation for a given playback type option
     * @param playbackTypeOption The playback type the click handler is configured to
     * @return The {@link ClickHandler} that can handle click events for that option
     */
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
