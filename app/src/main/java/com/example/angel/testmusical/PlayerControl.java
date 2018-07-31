package com.example.angel.testmusical;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.net.Uri;
import android.widget.SeekBar;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerControl {

    private Context context;
    private BandwidthMeter bandwidthMeter;
    private SimpleExoPlayer player;
    private AudioManager audioManager;
    private boolean isPlaying = false;

    @BindView(R.id.volumeSeekBar)
    public SeekBar volumeSeekBar;

    private PlayerView playerView;

    PlayerControl(Context context, PlayerView playerView) {
        this.context = context;
        this.playerView = playerView;

        ButterKnife.bind(this, (Activity) context);

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
        playerView.setPlayer(player);

        int maxMusicVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        volumeSeekBar.setMax(maxMusicVolume);
        volumeSeekBar.setProgress(maxMusicVolume);
        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(player.getAudioStreamType(), i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }

    public void play(Uri audioUri) {
        String userAgentPrefix = context.getResources().getString(R.string.user_agentp_prefix);
        String userAgent = Util.getUserAgent(context, userAgentPrefix);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, userAgent);
        MediaSource audioSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(audioUri);
        playerView.setControllerShowTimeoutMs(0); //Impide que se oculten los controles
        player.setPlayWhenReady(true);
        player.prepare(audioSource);
        isPlaying = true;

    }


    public void setImage(Bitmap bmp) {
        playerView.setDefaultArtwork(bmp);
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void stop() {
        player.stop();
        isPlaying = false;
    }

    public void pause() {
        player.setPlayWhenReady(false);
        isPlaying = false;
    }

    public void resume() {
        player.setPlayWhenReady(true);
        isPlaying = true;
    }
}
