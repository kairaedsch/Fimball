package sep.fimball.view;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import sep.fimball.viewmodel.SoundManagerViewModel;

import java.util.HashMap;
import java.util.Observer;

public class SoundManagerView
{
    MediaPlayer mediaPlayer;

    HashMap<String, AudioClip> loadedAudioClips;
    Media backgroundMusic;

    SoundManagerViewModel soundManagerViewModel;

    public SoundManagerView(SoundManagerViewModel soundManagerViewModel)
    {
        this.soundManagerViewModel = soundManagerViewModel;
        Observer playClipObserver = (o, clipPath) -> playClip((String) clipPath);
        Observer playMediaObserver = (o, mediaPath) -> playMedia((String) mediaPath);
        soundManagerViewModel.notifyToPlayClip(playClipObserver);
        soundManagerViewModel.notifyToPlayMedia(playMediaObserver);
    }

    private void playMedia(String mediaPath) {
        backgroundMusic = new Media(mediaPath);
        if (mediaPlayer != null)
            mediaPlayer.dispose();

        mediaPlayer = new MediaPlayer(backgroundMusic);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        mediaPlayer.play();
    }


    private void playClip(String clipPath) {
        if (loadedAudioClips.containsKey(clipPath)) {
            loadedAudioClips.get(clipPath).play();
        } else {
            AudioClip clip = new AudioClip(clipPath);
            loadedAudioClips.put(clipPath, clip);
            clip.play();
        }
    }

}
