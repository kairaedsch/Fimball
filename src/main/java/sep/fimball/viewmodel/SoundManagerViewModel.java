package sep.fimball.viewmodel;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import sep.fimball.general.util.Observable;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.model.media.SoundManager;

import java.util.Observer;

/**
 * Diese Klasse gibt Anweisungen, die Soundclips, die Hintergrundmusik und die Lautstärke zu ändern, an das Model weiter und stellt der View diese Informationen zur Verfügung.
 */
public class SoundManagerViewModel
{
    /**
     * Das Observable, das angibt, dass ein Sound abgespielt werden soll.
     */
    private Observable clipObservable;

    /**
     * Das Observable, das angibt, dass eine Hintergrundmusik abgespielt werden soll.
     */
    private Observable mediaObservable;

    /**
     * Die Lautstärke der Hintergrundmusik.
     */
    private DoubleProperty musicVolume;

    /**
     * Die Lautstärke der Sounds.
     */
    private DoubleProperty sfxVolume;

    /**
     * Die aktuellen Einstellungen.
     */
    Settings settings;

    /**
     * Erzeugt ein neues SoundManagerViewModel, das sich an die Lautstärke-Properties in {@link Settings} bindet.
     */
    public SoundManagerViewModel()
    {
        clipObservable = new Observable();
        mediaObservable = new Observable();
        settings = Settings.getSingletonInstance();

        musicVolume = new SimpleDoubleProperty();
        musicVolume.bind(settings.musicVolumeProperty().divide(100).multiply(settings.masterVolumeProperty().divide(100)));
        sfxVolume = new SimpleDoubleProperty();
        sfxVolume.bind(settings.sfxVolumeProperty().divide(100).multiply(settings.masterVolumeProperty().divide(100)));

        Observer clipObserver = ((o, arg) -> playClip((String) arg));
        Observer mediaObserver = ((o, arg) -> playMedia((String) arg));
        SoundManager.getInstance().addClipObserver(clipObserver);
        SoundManager.getInstance().addMediaObserver(mediaObserver);
    }

    /**
     * Benachrichtigt die eingetragenen Observer darüber, dass sich der Soundclip, der abgespielt wird, auf den durch {@code clipPath} angegebenen Soundclip geändert hat.
     *
     * @param clipPath Der Pfad zur Datei des neuen Soundclips.
     */
    private void playClip(String clipPath)
    {
        clipObservable.setChanged();
        clipObservable.notifyObservers(clipPath);
    }

    /**
     * Benachrichtigt die eingetragenen Observer darüber, dass sich die Hintergrundmusik auf den durch {@code mediaPath} angegebenen Soundclip geändert hat.
     *
     * @param mediaPath Der Pfad zur Datei der neuen Hintergrundmusik.
     */
    private void playMedia(String mediaPath)
    {
        mediaObservable.setChanged();
        mediaObservable.notifyObservers(mediaPath);
    }

    /**
     * Registriert das übergebene Objekt als Observer auf Änderungen des gespielten Soundclips.
     *
     * @param playClipObserver Das Objekt, das bei Änderungen des Soundclips benachrichtigt werden soll.
     */
    // TODO Umbenennen
    public void addClipObserver(Observer playClipObserver)
    {
        clipObservable.addObserver(playClipObserver);
    }

    /**
     * Registriert das übergebene Objekt als Observer auf Änderungen der gespielten Hintergrundmusik.
     *
     * @param playMediaObserver Das Objekt, das bei Änderungen der Hintergrundmusik benachrichtigt werden soll.
     */
    // TODO Umbenennen
    public void addMediaObserver(Observer playMediaObserver)
    {
        mediaObservable.addObserver(playMediaObserver);
    }

    /**
     * Gibt die Lautstärke der Hintergrundmusik zurück.
     *
     * @return Die Lautstärke der Hintergrundmusik.
     */
    public DoubleProperty musicVolumeProperty()
    {
        return musicVolume;
    }

    /**
     * Gibt die Lautstärke der Soundeffekte zurück.
     *
     * @return Die Lautstärke der Soundeffekte.
     */
    public DoubleProperty sfxVolumeProperty()
    {
        return sfxVolume;
    }
}
