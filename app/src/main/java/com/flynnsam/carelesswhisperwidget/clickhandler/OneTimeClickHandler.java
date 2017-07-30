package com.flynnsam.carelesswhisperwidget.clickhandler;

import android.content.Context;

import com.flynnsam.carelesswhisperwidget.R;
import com.flynnsam.soundboardmediaplayer.MediaPlayerProvider;
import com.flynnsam.soundboardmediaplayer.OnCompletionPlayNextListener;

/**
 * Click handler for widgets configured with the one-time option.
 * Created by sam on 2017-07-30.
 */

class OneTimeClickHandler implements ClickHandler {

    private static final int CW_INTRO_SOUND_ID = R.raw.intro;

    private static final int CW_ONE_TIME_SOUND_ID = R.raw.outro;

    private static final int CW_LOOP_SOUND_ID = R.raw.loop;

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleClick(Context context, MediaPlayerProvider mediaPlayerProvider) {

        Integer currentlyPlayingTrack = mediaPlayerProvider.getCurrentlyPlayingSoundId();

        if (currentlyPlayingTrack == null) {
            mediaPlayerProvider.play(context, CW_INTRO_SOUND_ID, new OneTimePlayNextListener());

        } else if (currentlyPlayingTrack == CW_LOOP_SOUND_ID) {
            mediaPlayerProvider.switchTrack(context, CW_ONE_TIME_SOUND_ID, new OneTimePlayNextListener());

        } else {
            mediaPlayerProvider.stop();
        }
    }

    /**
     * The on-completion listener that queues the one-time track after the intro, but nothing if
     * anything else is playing.
     */
    private class OneTimePlayNextListener implements OnCompletionPlayNextListener {

        /**
         * {@inheritDoc}
         */
        @Override
        public Integer getNextTrackResId(MediaPlayerProvider mediaPlayerProvider) {

            Integer currentlyPlayingSoundId = mediaPlayerProvider.getCurrentlyPlayingSoundId();

            if (currentlyPlayingSoundId != null && currentlyPlayingSoundId == CW_INTRO_SOUND_ID) {
                return CW_ONE_TIME_SOUND_ID;
            } else {
                return null;
            }
        }
    }
}
