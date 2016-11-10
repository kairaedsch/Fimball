package sep.fimball.model;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;
import java.util.Map;

/**
 * Der Soundmanager kümmert sich um die Verwaltung von MusicClips und AudioClips. Dabei ist zu beachten dass AudioClips komplett in den Arbeitsspeicher geladen werden wohingegen MusicClips gestreamt werden. Mithilfe des SoundManager können sowohl Hintergrundmusik als auch Soundeffekte abgespielt werden. Der Soundmanager hat eine Liste welche speichert zu welcher Datei ein SoundClip/MusicClip gehört.
 */
public class SoundManager
{
    /**
     * Stellt sicher, dass es nur eine Instanz von Settings gibt.
     */
    private static SoundManager singletonInstance;

    /**
     * Gibt den bereits existierenden Soundmanager oder einen neu angelegten zurück, falls noch keiner existiert.
     * @return
     */
    public static SoundManager getSingletonInstance()
    {
        if (singletonInstance == null)
            singletonInstance = new SoundManager();

        return singletonInstance;
    }

    /**
     * Hält eine Refernz auf die aktuell abgespielte Musik.
     */
    private MediaPlayer currentMusic;

    /**
     * Eine Menge von Hintergrundmusik.
     */
    private Map<MusicClip, Media> musicClips;

    /**
     * Eine Menge von Sounds.
     */
    private Map<SoundClip, AudioClip> fxClips;

    /**
     * Erzeugt eine neue Instanz von Soundmanager.
     */
    private SoundManager()
    {
        musicClips = new HashMap<>();
        fxClips = new HashMap<>();

        // TODO load music and sound clips here
    }

    /**
     * Spielt die angegebene Musik in einer Dauerschleife ab.
     * @param clip
     */
    public void playMusicClip(MusicClip clip)
    {
        if (currentMusic != null)
            currentMusic.dispose();

        currentMusic = new MediaPlayer(musicClips.get(clip));
        currentMusic.play(); // TODO loop
    }

    /**
     * Spielt den angegebenen SoundClip ab.
     * @param clip
     */
    public void playFxClip(SoundClip clip)
    {
        fxClips.get(clip).play();
    }
}