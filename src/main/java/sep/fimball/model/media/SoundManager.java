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
     * // TODO is false
     * Das Observable, dass das Abspielen von Soundeffekten verwaltet
     */
    private Observable observable;

    /**
     * Gibt den bereits existierenden SoundManager oder einen neu angelegten zurück, falls noch keiner existiert.
     *
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
        observable = new Observable();
    }

    /**
     * // TODO is false
     * Fügt den gegebenen Observer zu dem {@code clipObservable} hinzu.
     *
     * @param observer Der Observer, der hinzugefügt werden soll.
     */
    public void addObserver(Observer observer)
    {
        observable.addObserver(observer);
    }

    /**
     * // TODO is false
     * Benachrichtigt die Observer, dass der in {@code clipPath} gespeicherte SoundClip abgespielt werden soll.
     */
    public void addSoundToPlay(Sound sound)
    {
        observable.setChanged();
        observable.notifyObservers(sound);
    }
}
