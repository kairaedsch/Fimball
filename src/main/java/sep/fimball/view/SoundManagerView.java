package sep.fimball.view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import sep.fimball.model.media.Sound;
import sep.fimball.viewmodel.SoundManagerViewModel;

import java.util.HashMap;
import java.util.Observer;

/**
 * SoundManagerView ist für das Abspielen der Effektsounds sowie der Hintergrundmusik zuständig.
 */
public class SoundManagerView
{
    /**
     * Spielt die Hintergrundmusik ab.
     */
    private MediaPlayer mediaPlayer;

    /**
     * Hashed die bereits geladenen AudioClips, damit diese nicht mehrmals geladen werden müssen.
     */
    private HashMap<String, AudioClip> loadedAudioClips;

    /**
     * Die Hintergrundmusik, die abgespielt werden soll.
     */
    private Media backgroundMusic;

    /**
     * Die Lautstärke, mit der die Hintergrundmusik abgespielt werden soll.
     */
    private DoubleProperty musicVolume;

    /**
     * Die Lautstärke, mit der die Soundeffekte abgespielt werden.
     */
    private DoubleProperty sfxVolume;

    /**
     * Das zum SoundManager gehörende SoundManagerViewModel.
     */
    private SoundManagerViewModel soundManagerViewModel;

    /**
     * Erzeugt eine neue SoundManagerView.
     */
    public SoundManagerView()
    {
        this.soundManagerViewModel = new SoundManagerViewModel();

        musicVolume = new SimpleDoubleProperty();
        musicVolume.bind(soundManagerViewModel.musicVolumeProperty());
        sfxVolume = new SimpleDoubleProperty();
        sfxVolume.bind(soundManagerViewModel.sfxVolumeProperty());

        loadedAudioClips = new HashMap<>();

        Observer playClipObserver = (o, clipPath) -> play((Sound) clipPath);

        soundManagerViewModel.addObserver(playClipObserver);
    }

    /**
     * Spielt den gegebenen Sound ab.
     *
     * @param sound Der Sound der abgespielt werden soll.
     */
    private void play(Sound sound)
    {
        String soundPath = sound.getSoundPath();
        if(sound.isRepeating())
        {
            backgroundMusic = new Media(soundPath);
            if (mediaPlayer != null)
                mediaPlayer.dispose();

            mediaPlayer = new MediaPlayer(backgroundMusic);
            mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
            mediaPlayer.volumeProperty().bind(musicVolume);
            mediaPlayer.play();
        }
        else
        {
            if (!loadedAudioClips.containsKey(soundPath))
            {
                AudioClip clip = new AudioClip(soundPath);
                loadedAudioClips.put(soundPath, clip);
                clip.volumeProperty().bind(sfxVolume);
                clip.play();
            }

            loadedAudioClips.get(soundPath).play();
        }
    }
}
