package sep.fimball.viewmodel;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import sep.fimball.general.util.Observable;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.model.media.Sound;
import sep.fimball.model.media.SoundManager;

import java.util.Observer;

/**
 * Diese Klasse gibt Anweisungen, die Sounds und die Lautstärke zu ändern, an das Model weiter und stellt der View diese Informationen zur Verfügung.
 */
public class SoundManagerViewModel
{
    /**
     * Das Observable, das benachrichtigt wird, wenn ein Sound abgespielt werden soll.
     */
    private Observable observable;

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
     * Erzeugt ein neues SoundManagerViewModel, welches sich an die Lautstärke-Properties in {@link Settings} bindet.
     */
    public SoundManagerViewModel()
    {
        observable = new Observable();
        settings = Settings.getSingletonInstance();

        musicVolume = new SimpleDoubleProperty();
        musicVolume.bind(settings.musicVolumeProperty().divide(100.0).multiply(settings.masterVolumeProperty().divide(100.0)));
        sfxVolume = new SimpleDoubleProperty();
        sfxVolume.bind(settings.sfxVolumeProperty().divide(100.0).multiply(settings.masterVolumeProperty().divide(100.0)));

        Observer observer = ((o, sound) -> playClip((Sound) sound));
        SoundManager.getInstance().addObserver(observer);
    }

    /**
     * Benachrichtigt die eingetragenen Observer darüber, dass ein Sound abgespielt werden soll.
     *
     * @param sound Der Pfad zur Datei des neuen Sounds.
     */
    private void playClip(Sound sound)
    {
        observable.setChanged();
        observable.notifyObservers(sound);
    }

    /**
     * Registriert das übergebene Objekt als Observer um es zu benachrichtigen, falls ein Sound abgespielt werden soll.
     *
     * @param playObserver Das Objekt, das bei Änderungen des Sounds benachrichtigt werden soll.
     */
    public void addObserver(Observer playObserver)
    {
        observable.addObserver(playObserver);
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
