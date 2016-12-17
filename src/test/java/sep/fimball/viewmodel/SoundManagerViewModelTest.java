package sep.fimball.viewmodel;

import javafx.beans.property.SimpleIntegerProperty;
import org.junit.Test;
import sep.fimball.general.data.Sounds;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.model.media.Sound;
import sep.fimball.model.media.SoundManager;

import java.util.Observable;
import java.util.Observer;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Testet die Klasse SoundManagerViewModel.
 */
public class SoundManagerViewModelTest
{

    /**
     * Gibt ein SoundManagerViewModel zurück, das mit gemockten Setttings erzeugt wird, um die eigentlichen Settings nicht zu
     * ändern.
     *
     * @return Ein SoundManagerViewModel mit den gemockten Settings.
     */
    private SoundManagerViewModel init()
    {

        Settings mockedSettings = mock(Settings.class);
        when(mockedSettings.sfxVolumeProperty()).thenReturn(new SimpleIntegerProperty(20));
        when(mockedSettings.musicVolumeProperty()).thenReturn(new SimpleIntegerProperty(30));
        when(mockedSettings.masterVolumeProperty()).thenReturn(new SimpleIntegerProperty(50));

        return new SoundManagerViewModel(mockedSettings);

    }

    /**
     * Testet, dass das Singleton-Verhalten von SoundManagerViewModel funktioniert.
     */
    @Test
    public void singletonTest()
    {
        assertThat(SoundManagerViewModel.getInstance(), is(not(nullValue())));
    }

    /**
     * Testet, ob die Lautstärken eines Test-SoundManagerViewModels stimmen.
     */
    @Test
    public void testVolumes()
    {
        SoundManagerViewModel testSoundManagerViewModel = init();
        assertThat("Die Musik-Lautstärke stimmt", testSoundManagerViewModel.musicVolumeProperty().get(), is(0.15));
        assertThat("Die Effekt-Lautstärke stimmt", testSoundManagerViewModel.sfxVolumeProperty().get(), is(0.1));

    }

    /**
     * Testet, dass das Abspielen von Sounds und Hintergrundmusik, sowie das Stoppen des Abspielens von Hintergrundmusik an die
     * Observer weitergeleitet werden.
     */
    @Test
    public void testSoundForwarding()
    {
        //Fügt einen NotificationObserver zum SoundManagerVIewModel hinzu.
        NotificationObserver playObserver = new NotificationObserver();
        SoundManagerViewModel.getInstance().addPlayObserver(playObserver);

        assertThat("Es soll noch kein Sound abgespielt werden", playObserver.getNumberOfNotifications(), is(0));

        //Die Observer werden über das Abspielen der Hintergrundmusik informiert.
        SoundManagerViewModel.getInstance().playMusic(Sounds.GAME);
        assertThat("Der abzuspielende Sound soll wiederholt werden", playObserver.getSound().isRepeating(), is(true));

        //Die Observer werden über das Abspielen des richtigen SoundClips informiert.
        Sound testSound = mock(Sound.class);
        SoundManager.getInstance().addSoundToPlay(testSound);
        assertThat("Der Observer wurde über das Abspielen des richtigen Sounds informiert", playObserver.getSound(), equalTo(testSound));

        //Die Observer werden über das Stoppen der Hintergrundmusik informiert.
        NotificationObserver stopObserver = new NotificationObserver();
        SoundManagerViewModel.getInstance().addStopObserver(stopObserver);
        SoundManagerViewModel.getInstance().stopBackgroundMusic();
        assertThat("Der Observer wurde über das Stoppen benachrichtigt", stopObserver.getNumberOfNotifications(), is(1));
    }

    /**
     * Stellt einen Observer da, der die Anzahl der Notifikationen zählt, die er erhält, sowie den Sound speichert, der
     * übergeben wird.
     */
    class NotificationObserver implements Observer
    {
        /**
         * Die Anzahl der Notifikationen zählt, die der Observer erhalten hat.
         */
        private int notifications;

        /**
         * Der Sound, der bei der letzten Notifikation übergeben wurde.
         */
        private Sound sound;

        /**
         * Erstelt einen neuen NotificationObserver.
         */
        NotificationObserver()
        {
            notifications = 0;
        }

        @Override
        public void update(Observable o, Object sound)
        {
            notifications++;
            this.sound = (Sound) sound;
        }

        /**
         * Gibt die Anzahl der Notifikationen, die der Observer erhalten hat, zurück.
         *
         * @return Die Anzahl der Notifikationen, die der Observer erhalten hat,
         */
        int getNumberOfNotifications()
        {
            return notifications;
        }

        /**
         * Gibt den Sound, der bei der letzten Notifikation übergeben wurde, zurück.
         *
         * @return Der Sound, der bei der letzten Notifikation übergeben wurde.
         */
        Sound getSound()
        {
            return sound;
        }
    }

}
