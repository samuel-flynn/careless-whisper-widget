package com.flynnsam.carelesswhisperwidget.clickhandler;

import android.content.Context;
import android.util.Log;

import com.flynnsam.carelesswhisperwidget.R;
import com.flynnsam.soundboardmediaplayer.MediaPlayerProvider;
import com.flynnsam.soundboardmediaplayer.OnCompletionPlayNextListener;

/**
 * Click handler for widgets configured with the continuous loop option.
 * Created by sam on 2017-07-30.
 */

class LoopClickHandler implements ClickHandler {

    private static final String LOGGER_TAG = LoopClickHandler.class.getName();

    private static final int CW_INTRO_SOUND_ID = R.raw.intro;

    private static final int CW_LOOP_SOUND_ID = R.raw.loop;

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleClick(Context context, MediaPlayerProvider mediaPlayerProvider) {

        Integer currentlyPlayingTrack = mediaPlayerProvider.getCurrentlyPlayingSoundId();

        Log.d(LOGGER_TAG, String.format("Handling loop click. Currently playing sound ID: [%1$s]", currentlyPlayingTrack));

        if (currentlyPlayingTrack != null) {
            mediaPlayerProvider.stop();

        } else {
            mediaPlayerProvider.play(context, CW_INTRO_SOUND_ID, new LoopingPlayNextListener());
        }
    }

    /**
     * On-completion listener that always queues up the loop to play next.
     */
    private class LoopingPlayNextListener implements OnCompletionPlayNextListener {

        /**
         * {@inheritDoc}
         */
        @Override
        public Integer getNextTrackResId(MediaPlayerProvider mediaPlayerProvider) {
            return CW_LOOP_SOUND_ID;
        }
    }
}
