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
import java.util.Optional;

/**
 * SoundManagerView ist für das Abspielen der SoundEffekte sowie der Hintergrundmusik zuständig.
 */
public class SoundManagerView
{
    /**
     * Spielt die Hintergrundmusik ab.
     */
    private Optional<MediaPlayer> mediaPlayer;

    /**
     * Cashed geladenen AudioClips, damit diese nicht mehrmals geladen werden müssen.
     */
    private HashMap<String, AudioClip> loadedAudioClips;

    /**
     * Die Lautstärke, mit der die Hintergrundmusik abgespielt wird.
     */
    private DoubleProperty musicVolume;

    /**
     * Die Lautstärke, mit der die Soundeffekte abgespielt werden.
     */
    private DoubleProperty sfxVolume;

    /**
     * Erzeugt eine neue SoundManagerView.
     */
    public SoundManagerView()
    {
        SoundManagerViewModel soundManagerViewModel = SoundManagerViewModel.getInstance();

        mediaPlayer = Optional.empty();
        loadedAudioClips = new HashMap<>();

        // Holen der Lautstärke aus dem ViewModel
        musicVolume = new SimpleDoubleProperty();
        musicVolume.bind(soundManagerViewModel.musicVolumeProperty());
        sfxVolume = new SimpleDoubleProperty();
        sfxVolume.bind(soundManagerViewModel.sfxVolumeProperty());

        // Erstelle Listener zum stoppen der backgroundmusic
        Observer stopObserver = (o, args) -> stopBackgroundMusic();
        soundManagerViewModel.addStopObvserver(stopObserver);

        // Erstelle Listener zum abspielen der Sounds
        Observer playClipObserver = (o, clipPath) -> play((Sound) clipPath);
        soundManagerViewModel.addPlayObserver(playClipObserver);
    }

    /**
     * Spielt den gegebenen Sound ab.
     *
     * @param sound Der Sound der abgespielt werden soll.
     */
    private void play(Sound sound)
    {
        String soundPath = sound.getSoundPath();

        // Erstelle einen MediaPlayer, falls sich der Sound wiederholen soll.
        if (sound.isRepeating())
        {
            MediaPlayer newMediaPlayer = new MediaPlayer(new Media(soundPath));
            newMediaPlayer.setOnEndOfMedia(() -> newMediaPlayer.seek(Duration.ZERO));
            newMediaPlayer.volumeProperty().bind(musicVolume);
            newMediaPlayer.play();

            // Es kann sich immer nur ein Sound wiederholen
            mediaPlayer.ifPresent(MediaPlayer::dispose);

            mediaPlayer = Optional.of(newMediaPlayer);
        }
        // Spiele einen AudioClip ab, falls sich der Sound nicht wiederholen soll
        else
        {
            // Cache den AudioClip, falls er noch nicht existiert
            if (!loadedAudioClips.containsKey(soundPath))
            {
                AudioClip clip = new AudioClip(soundPath);
                clip.volumeProperty().bind(sfxVolume);
                loadedAudioClips.put(soundPath, clip);
            }

            // Spiele den AudioClip ab
            loadedAudioClips.get(soundPath).play();
        }
    }

    /**
     * Stoppt die Hintergrundmusik, falls eine abgespielt wird.
     */
    private void stopBackgroundMusic()
    {
        if (mediaPlayer.isPresent())
        {
            mediaPlayer.get().dispose();
            mediaPlayer = Optional.empty();
        }
    }
}
