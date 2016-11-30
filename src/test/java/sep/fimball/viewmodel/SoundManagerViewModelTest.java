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


public class SoundManagerViewModelTest
{

    //Es werden gemockte Settings erstellt, um die eigentlichen Settings nicht zu ändern, und ein neues SoundManagerViewModel mit den gemockten Settings zurück.
    private SoundManagerViewModel init()
    {

        Settings mockedSettings = mock(Settings.class);
        when(mockedSettings.sfxVolumeProperty()).thenReturn(new SimpleIntegerProperty(20));
        when(mockedSettings.musicVolumeProperty()).thenReturn(new SimpleIntegerProperty(30));
        when(mockedSettings.masterVolumeProperty()).thenReturn(new SimpleIntegerProperty(50));

        return new SoundManagerViewModel(mockedSettings);

    }

    @Test
    public void singletonTest()
    {
        assertThat(SoundManagerViewModel.getInstance() , is(not(nullValue())));
    }

    //Testet. ob die Lautstärken des SoundManagerViewModels stimmen
    @Test
    public void testVolumes()
    {
        SoundManagerViewModel testSoundManagerViewModel = init();
        assertThat("Die Musik-Lautstärke stimmt",testSoundManagerViewModel.musicVolumeProperty().get(), is(0.15));
        assertThat("Die Effekt-Lautstärke stimmt",testSoundManagerViewModel.sfxVolumeProperty().get(), is(0.1));

    }

    @Test
    public void testSoundStateChange()
    {
        //Fügt einen NotificationObserver zum SoundManagerVIewModel hinzu.
        NotificationObserver playObserver = new NotificationObserver();
        SoundManagerViewModel.getInstance().addPlayObserver(playObserver);

        assertThat("Es soll noch kein Sound abgepsielt werden",playObserver.getNumberOfNotifications(), is(0));

        //Die Observer werden über das Abspielen der Hintergrundmusik informiert.
        SoundManagerViewModel.getInstance().playMusic(Sounds.GAME);
        assertThat("Der abzuspielende Sound soll wiederholt werden",playObserver.getSound().isRepeating(), is(true));

        //Die Observer werden über das Abspielen des richtigen SoundClips informiert.
        Sound testSound = new Sound("test", false);
        SoundManager.getInstance().addSoundToPlay(testSound);
        assertThat("Der Observer wurde über das Abspielen des richtigen Sounds informiert",playObserver.getSound(), equalTo(testSound));

        //Die Observer werden über das Stoppen der Hintergrundmusik informiert.
        NotificationObserver stopObsever = new NotificationObserver();
        SoundManagerViewModel.getInstance().addStopObvserver(stopObsever);
        SoundManagerViewModel.getInstance().stopBackgroundMusic();
        assertThat("Der Observer wurde über das Stoppen benachrichtigt",stopObsever.getNumberOfNotifications(), is(1));
    }

    class NotificationObserver implements Observer
    {
        private int notifications;
        private Sound sound;

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

        int getNumberOfNotifications()
        {
            return notifications;
        }

        Sound getSound()
        {
            return sound;
        }
    }

}
