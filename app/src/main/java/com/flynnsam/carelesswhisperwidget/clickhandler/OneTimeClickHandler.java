package com.flynnsam.carelesswhisperwidget.clickhandler;

import android.content.Context;

import com.flynnsam.carelesswhisperwidget.R;
import com.flynnsam.soundboardmediaplayer.MediaPlayerProvider;
import com.flynnsam.soundboardmediaplayer.OnCompletionPlayNextListener;

/**
 * Created by admin on 2017-07-30.
 */

class OneTimeClickHandler implements ClickHandler {

    private static final int CW_INTRO_SOUND_ID = R.raw.intro;

    private static final int CW_ONE_TIME_SOUND_ID = R.raw.outro;

    private static final int CW_LOOP_SOUND_ID = R.raw.loop;

    @Override
    public void handleClick(Context context, MediaPlayerProvider mediaPlayerProvider) {

        Integer currentlyPlayingTrack = mediaPlayerProvider.getCurrentlyPlayingSoundId();

        if (currentlyPlayingTrack != null) {
            switchToOneTimeOrStop(context, mediaPlayerProvider, currentlyPlayingTrack);

        } else {
            mediaPlayerProvider.play(context, CW_INTRO_SOUND_ID);
            mediaPlayerProvider.setOnCompletionPlayNextListener(new LoopingPlayNextListener(currentlyPlayingTrack));
        }
    }

    private void switchToOneTimeOrStop(Context context, MediaPlayerProvider mediaPlayerProvider, int currentlyPlayingTrack) {

        if (currentlyPlayingTrack == CW_LOOP_SOUND_ID) {
            mediaPlayerProvider.switchTrack(context, CW_ONE_TIME_SOUND_ID);

        } else {
            mediaPlayerProvider.stop();
        }
    }


    private class LoopingPlayNextListener implements OnCompletionPlayNextListener {

        private final Integer currentlyPlayingSoundId;

        public LoopingPlayNextListener(Integer currentlyPlayingSoundId) {
            this.currentlyPlayingSoundId = currentlyPlayingSoundId;
        }

        @Override
        public Integer getNextTrackResId() {

            if (currentlyPlayingSoundId == null) {
                return CW_ONE_TIME_SOUND_ID;
            } else {
                return null;
            }
        }
    }
}
