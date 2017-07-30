package com.flynnsam.carelesswhisperwidget.clickhandler;

import android.content.Context;

import com.flynnsam.carelesswhisperwidget.R;
import com.flynnsam.soundboardmediaplayer.MediaPlayerProvider;
import com.flynnsam.soundboardmediaplayer.OnCompletionPlayNextListener;

/**
 * Created by admin on 2017-07-30.
 */

class LoopClickHandler implements ClickHandler {

    private static final int CW_INTRO_SOUND_ID = R.raw.intro;

    private static final int CW_LOOP_SOUND_ID = R.raw.loop;

    @Override
    public void handleClick(Context context, MediaPlayerProvider mediaPlayerProvider) {

        Integer currentlyPlayingTrack = mediaPlayerProvider.getCurrentlyPlayingSoundId();

        if (currentlyPlayingTrack != null) {
            mediaPlayerProvider.stop();

        } else {
            mediaPlayerProvider.play(context, CW_INTRO_SOUND_ID);
            mediaPlayerProvider.setOnCompletionPlayNextListener(new LoopingPlayNextListener());
        }
    }

    private class LoopingPlayNextListener implements OnCompletionPlayNextListener {

        @Override
        public Integer getNextTrackResId() {
            return CW_LOOP_SOUND_ID;
        }
    }
}
