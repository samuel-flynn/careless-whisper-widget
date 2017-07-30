package com.flynnsam.carelesswhisperwidget.options;

import com.flynnsam.carelesswhisperwidget.R;

/**
 * Enumeration of the various playback options.
 * Created by Sam on 2017-07-29.
 */

public enum PlaybackType {

    LOOP(R.string.loop_option_desc),
    ONE_TIME(R.string.one_time_option_desc);

    private int labelResId;

    PlaybackType(int labelResId) {
        this.labelResId = labelResId;

    }

    /**
     * Get the UI label to display on the configuration activity for this playback option.
     * @return The label to display
     */
    public int getLabelResId() {
        return labelResId;
    }
}
