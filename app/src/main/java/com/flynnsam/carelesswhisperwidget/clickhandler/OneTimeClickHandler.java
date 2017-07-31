package com.flynnsam.carelesswhisperwidget.clickhandler;

import android.content.Context;

import com.flynnsam.carelesswhisperwidget.R;
import com.flynnsam.soundboardmediaplayer.OnCompletionPlayNextListener;
import com.flynnsam.soundboardmediaplayer.SoundboardMediaProvider;

/**
 * Click handler for widgets configured with the one-time option.
 * Created by sam on 2017-07-30.
 */

class OneTimeClickHandler implements ClickHandler {

    private static final int CW_INTRO_SOUND_ID = R.raw.intro;

    private static final int CW_ONE_TIME_SOUND_ID = R.raw.outro;

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleClick(Context context, SoundboardMediaProvider soundboardMediaProvider) {

        Integer currentlyPlayingTrack = soundboardMediaProvider.getCurrentlyPlayingSoundId();

        if (currentlyPlayingTrack == null) {
            soundboardMediaProvider.play(context, CW_INTRO_SOUND_ID, new OneTimePlayNextListener());

        } else {
            soundboardMediaProvider.stop();
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
        public Integer getNextTrackResId(SoundboardMediaProvider soundboardMediaProvider) {

            Integer currentlyPlayingSoundId = soundboardMediaProvider.getCurrentlyPlayingSoundId();

            if (currentlyPlayingSoundId != null && currentlyPlayingSoundId == CW_INTRO_SOUND_ID) {
                return CW_ONE_TIME_SOUND_ID;
            } else {
                return null;
            }
        }
    }
}
