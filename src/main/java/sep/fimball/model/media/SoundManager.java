package sep.fimball.model.media;

import sep.fimball.general.util.Observable;
import java.util.Observer;

/**
 * SoundManager stellt die Schnittstelle vom Model zum ViewModel zum Abspielen der Sounds dar. Dabei wird das ViewModel über das Observer-Pattern über Änderungen informiert.
 */
public class SoundManager
{
    /**
     * Die einzige existierende Instanz des SoundManager
     */
    private static SoundManager instance;

    /**
     * Das Observable, dass das Abspielen von Soundeffekten verwaltet
     */
    private Observable clipObservable;

    /**
     * Das Observable, dass das Abspielen von Musik verwaltet.
     */
    private Observable mediaObservable;

    /**
     * Gibt den bereits existierenden SoundManager oder einen neu angelegten zurück, falls noch keiner existiert.
     * @return Eine Instanz von SoundManager.
     */
    public static SoundManager getInstance()
    {
        if (instance == null)
        {
            instance = new SoundManager();
        }
        return instance;
    }

    /**
     * Erstellt einen neuen SoundManager.
     */
    private SoundManager()
    {
        clipObservable = new Observable();
        mediaObservable = new Observable();
    }

    /**
     * Fügt den gegebenen Observer zu dem {@code clipObservable} hinzu.
     * @param observer Der Observer, der hinzugefügt werden soll.
     */
    public void addClipObserver(Observer observer)
    {
        clipObservable.addObserver(observer);
    }

    /**
     * Fügt den gegebenen Observer zu dem {@code mediaObservable} hinzu.
     * @param observer Der Observer, der hinzugefügt werden soll.
     */
    public void addMediaObserver(Observer observer)
    {
        mediaObservable.addObserver(observer);
    }

    /**
     * Benachrichtigt die Observer, dass der in {@code clipPath} gespeicherte SoundClip abgespielt werden soll.
     * @param clipPath Der Pfad, an dem der abzuspielende SoundClip gespeichert ist.
     */
    public void addClipToPlay(String clipPath)
    {
        clipObservable.setChanged();
        clipObservable.notifyObservers(clipPath);
    }

    /**
     * Benachrichtigt die Observer, dass die in {@code clipPath} gespeicherte Musik abgespielt werden soll.
     * @param musicPath Der Pfad, an dem die abzuspielende Musik gespeichert ist.
     */
    public void addMediaToPlay(String musicPath)
    {
        mediaObservable.setChanged();
        mediaObservable.notifyObservers(musicPath);
    }
}
