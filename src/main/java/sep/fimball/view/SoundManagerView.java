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
import java.util.function.Function;

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
     * Sound-Object der Hintergrundmusik.
     */
    private Optional<Sound> mediaPlayerSound;

    /**
     * Cashed geladenen AudioClips, damit diese nicht mehrmals geladen werden müssen.
     */
    private HashMap<String, AudioClip> loadedAudioClips;

    /**
     * Der Zeitpunkt, zu dem das letzte Mal AudioClips abgespielt wurden.
     */
    private long lastPlayedAudioClips;

    /**
     * Die Anzahl der seit {@code lastPlayedAudioClips} abgespielten AudioClips.
     */
    private int count;

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
        mediaPlayerSound = Optional.empty();
        loadedAudioClips = new HashMap<>();
        lastPlayedAudioClips = 0;
        count = 0;

        // Holen der Lautstärke aus dem ViewModel
        musicVolume = new SimpleDoubleProperty();
        musicVolume.bind(soundManagerViewModel.musicVolumeProperty());
        sfxVolume = new SimpleDoubleProperty();
        sfxVolume.bind(soundManagerViewModel.sfxVolumeProperty());

        // Erstelle Listener zum stoppen der Hintergrundmusik
        Observer stopObserver = (o, args) -> stopBackgroundMusic();
        soundManagerViewModel.addStopObserver(stopObserver);

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
        // Erstelle einen MediaPlayer, falls sich der Sound wiederholen soll.
        if (sound.isRepeating())
        {
            if (!mediaPlayerSound.isPresent() || !mediaPlayerSound.get().getSoundPath().equals(sound.getSoundPath()))
            {
                Optional<MediaPlayer> newMediaPlayer = tryToLoad((path) -> new MediaPlayer(new Media(path)), sound.getSoundPath());

                if (newMediaPlayer.isPresent())
                {
                    newMediaPlayer.get().setOnEndOfMedia(() -> newMediaPlayer.get().seek(Duration.ZERO));
                    newMediaPlayer.get().volumeProperty().bind(musicVolume);
                    newMediaPlayer.get().play();

                    // Es kann sich immer nur ein Sound wiederholen
                    mediaPlayer.ifPresent(MediaPlayer::dispose);

                    mediaPlayer = newMediaPlayer;
                    mediaPlayerSound = Optional.of(sound);
                }
            }
        }
        // Spiele einen AudioClip ab, falls sich der Sound nicht wiederholen soll
        else
        {
            long now = System.currentTimeMillis();

            if(now - lastPlayedAudioClips > 250)
            {
                lastPlayedAudioClips = now;
                count = 0;
            }

            if (count < 25)
            {
                count++;

                String soundPath = sound.getSoundPath();

                // Cache den AudioClip, falls er noch nicht existiert
                if (!loadedAudioClips.containsKey(soundPath))
                {
                    Optional<AudioClip> audioClip = tryToLoad(AudioClip::new, soundPath);

                    if (audioClip.isPresent())
                    {
                        audioClip.get().volumeProperty().bind(sfxVolume);
                        loadedAudioClips.put(soundPath, audioClip.get());
                        audioClip.get().play();
                    }
                }
                else
                {
                    // Spiele den AudioClip ab
                    loadedAudioClips.get(soundPath).play();
                }
            }
        }
    }

    /**
     * Versucht den {@code creator} mit dem Parameter {@code input} auszuführen.
     *
     * @param creator Der Creator, welcher ein Object vom Typ {@code R} erstellen soll.
     * @param input   Der Input für den {@code creator}.
     * @param <T>     Der Typ vom {@code input}.
     * @param <R>     Der Typ vom Output von {@code creator}.
     * @return Gibt entweder ein Optional mit dem vom {@code creator} zurück gegebenen Object zurück, oder ein Optional.empty, falls ein Fehler aufgetreten ist.
     */
    private <T, R> Optional<R> tryToLoad(Function<T, R> creator, T input)
    {
        try
        {
            return Optional.of(creator.apply(input));
        }
        /* Wir fangen hier Throwable, da es egal ist, wenn wir Sounds nicht abspielen können. Wir wollen aber auf gar keinen Fall,
         * dass das gesamte Spiel abstürzt, nur weil ein Sound nicht abgespielt werden kann. */
        catch (Throwable t)
        {
            System.err.println("Kann nicht laden: \"" + input + "\"");
            t.printStackTrace();
            return Optional.empty();
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
