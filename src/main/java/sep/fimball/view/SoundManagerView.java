package sep.fimball.view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import sep.fimball.general.data.Config;
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
     *  Hashed die bereits geladenen AudioClips, damit diese nicht mehrmals geladen werden müssen.
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

        Observer playClipObserver = (o, clipPath) -> playClip((String) clipPath);
        Observer playMediaObserver = (o, mediaPath) -> playMedia((String) mediaPath);

        soundManagerViewModel.notifyToPlayClip(playClipObserver);
        soundManagerViewModel.notifyToPlayMedia(playMediaObserver);

    }

    /**
     * Lädt die durch {@code clipName} gegebene Datei und setzt die darin enthaltene Musik als Hintergrundmusik.
     *
     * @param clipName Der Name der Musikdatei.
     */
    private void playMedia(String clipName) {
        String clipPath = Config.pathToSound(clipName);
        backgroundMusic = new Media(clipPath);
        if (mediaPlayer != null)
            mediaPlayer.dispose();

        mediaPlayer = new MediaPlayer(backgroundMusic);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        mediaPlayer.volumeProperty().bind(musicVolume);
        mediaPlayer.play();
    }

    /**
     * Lädt den angegeben Soundclip und spielt ihn ab.
     *
     * @param clipName Der Name der Sound-Datei.
     */
    private void playClip(String clipName) {
        String clipPath = Config.pathToSound(clipName);
        if (loadedAudioClips.containsKey(clipPath)) {
            loadedAudioClips.get(clipPath).play();
        } else {
            AudioClip clip = new AudioClip(clipPath);
            loadedAudioClips.put(clipPath, clip);
            clip.volumeProperty().bind(sfxVolume);
            clip.play();
        }
    }

}
