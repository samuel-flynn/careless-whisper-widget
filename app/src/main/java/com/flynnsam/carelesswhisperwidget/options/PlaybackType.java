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

    private PlaybackType(int labelResId) {
        this.labelResId = labelResId;

    }

    public int getLabelResId() {
        return labelResId;
    }
}
