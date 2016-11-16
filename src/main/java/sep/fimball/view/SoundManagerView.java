package sep.fimball.view;

import javafx.beans.property.DoubleProperty;
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

    DoubleProperty musicVolume;
    DoubleProperty sfxVolume;

    SoundManagerViewModel soundManagerViewModel;

    public SoundManagerView(SoundManagerViewModel soundManagerViewModel)
    {
        this.soundManagerViewModel = soundManagerViewModel;
        Observer playClipObserver = (o, clipPath) -> playClip((String) clipPath);
        Observer playMediaObserver = (o, mediaPath) -> playMedia((String) mediaPath);
        soundManagerViewModel.notifyToPlayClip(playClipObserver);
        soundManagerViewModel.notifyToPlayMedia(playMediaObserver);
        musicVolume.bind(soundManagerViewModel.musicVolumeProperty());
        sfxVolume.bind(soundManagerViewModel.sfxVolumeProperty());
    }

    private void playMedia(String mediaPath) {
        backgroundMusic = new Media(mediaPath);
        if (mediaPlayer != null)
            mediaPlayer.dispose();

        mediaPlayer = new MediaPlayer(backgroundMusic);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        mediaPlayer.setVolume(musicVolume.getValue());
        mediaPlayer.play();
    }


    private void playClip(String clipPath) {
        if (loadedAudioClips.containsKey(clipPath)) {
            loadedAudioClips.get(clipPath).play();
        } else {
            AudioClip clip = new AudioClip(clipPath);
            loadedAudioClips.put(clipPath, clip);
            clip.setVolume(sfxVolume.getValue());
            clip.play();
        }
    }

}
