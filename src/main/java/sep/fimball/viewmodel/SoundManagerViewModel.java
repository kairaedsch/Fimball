package sep.fimball.viewmodel;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import sep.fimball.general.data.Sounds;
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
     * Stellt sicher, dass es nur eine Instanz vom SoundManagerViewModel gibt.
     */
    private static SoundManagerViewModel instance;

    /**
     * Das Observable, das benachrichtigt wird, wenn ein Sound abgespielt werden soll.
     */
    private Observable playObservable;

    /**
     * Das Observable, das benachrichtigt wird, wenn die Musik stoppen soll.
     */
    private Observable stopObservable;

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
    private SoundManagerViewModel()
    {
        playObservable = new Observable();
        stopObservable = new Observable();
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
        playObservable.setChanged();
        playObservable.notifyObservers(sound);
    }

    /**
     * Benachrichtigt die eingetragenen Observer darüber, dass die Hintergrundmusik gestoppt werden soll.
     */
    public void stopBackgroundMusic() {
        stopObservable.setChanged();
        stopObservable.notifyObservers();
    }

    /**
     * Registriert das übergebene Objekt als Observer um es zu benachrichtigen, falls ein Sound abgespielt werden soll.
     *
     * @param playObserver Das Objekt, das bei Änderungen des Sounds benachrichtigt werden soll.
     */
    public void addPlayObserver(Observer playObserver)
    {
        playObservable.addObserver(playObserver);
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

    /**
     * Registriert das übergebene Objekt als Observer um es zu benachrichtigen, falls die Hintergrundmusik gestoppt werden soll.
     *
     * @param stopObserver Das Objekt, das benachrichtigt werden soll, wenn die Hintergrundmusik gestoppt werden soll.
     */
    public void addStopObvserver(Observer stopObserver)
    {
        stopObservable.addObserver(stopObserver);
    }

    /**
     * Gibt das bereits existierende SoundManagerViewModel oder ein neu angelegtes zurück, falls noch keines existiert.
     *
     * @return Eine Instanz von SoundManagerViewModel.
     */
    public static SoundManagerViewModel getInstance() {
        if (instance == null) {
            instance = new SoundManagerViewModel();
        }
        return instance;
    }

    /**
     * Benachrichtigt die Observer, dass die gegebene Hintergrundmusik abgespielt werden soll.
     * @param music Die Hintergrundmusik, die abgespielt werden soll.
     */
    public void playMusic(Sounds music)
    {
        playClip(new Sound(music.getSoundName(),true));
    }
}
