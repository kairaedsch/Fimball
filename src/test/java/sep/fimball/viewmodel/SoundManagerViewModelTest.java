package sep.fimball.viewmodel;

import org.junit.Test;
import sep.fimball.general.data.Sounds;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.model.media.Sound;
import sep.fimball.model.media.SoundManager;

import java.util.Observable;
import java.util.Observer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by marc on 28.11.16.
 */
public class SoundManagerViewModelTest
{
    private final double TEST_VOLUME = 10.0;
    private Settings settings = Settings.getSingletonInstance();

    @Test (timeout = 1000)
    public void singletonTest()
    {
        assertTrue(SoundManagerViewModel.getInstance() != null);
    }

    @Test (timeout = 1000)
    public void testVolumes()
    {
        assertTrue(SoundManagerViewModel.getInstance().sfxVolumeProperty().get() == settings.sfxVolumeProperty().divide(100.0).multiply(settings.masterVolumeProperty().divide(100.0)).get());
        Settings.getSingletonInstance().sfxVolumeProperty().setValue(TEST_VOLUME);
        assertTrue(SoundManagerViewModel.getInstance().sfxVolumeProperty().get() == TEST_VOLUME / 100 * settings.masterVolumeProperty().divide(100.0).get());
        assertTrue(SoundManagerViewModel.getInstance().musicVolumeProperty().get() == settings.musicVolumeProperty().divide(100.0).multiply(settings.masterVolumeProperty().divide(100.0)).get());
        Settings.getSingletonInstance().musicVolumeProperty().setValue(TEST_VOLUME);
        assertTrue(SoundManagerViewModel.getInstance().musicVolumeProperty().get() == TEST_VOLUME / 100 * settings.masterVolumeProperty().divide(100.0).get());
    }

    @Test (timeout = 2000)
    public void testSoundStateChange()
    {
        NotificationObserver notificationObserver = new NotificationObserver();
        SoundManagerViewModel.getInstance().addPlayObserver(notificationObserver);
        assertEquals(notificationObserver.getNumberOfNotifications(), 0);
        SoundManagerViewModel.getInstance().playMusic(Sounds.GAME);
        assertEquals(notificationObserver.getNumberOfNotifications(), 1);
        SoundManager.getInstance().addSoundToPlay(new Sound("test", false));
        assertEquals(notificationObserver.getNumberOfNotifications(), 2);
        SoundManagerViewModel.getInstance().addStopObvserver(notificationObserver);
        SoundManagerViewModel.getInstance().stopBackgroundMusic();
        assertEquals(notificationObserver.getNumberOfNotifications(), 3);
    }

    class NotificationObserver implements Observer
    {
        private int notifications;

        NotificationObserver()
        {
            notifications = 0;
        }

        @Override
        public void update(Observable o, Object arg)
        {
            notifications++;
        }

        int getNumberOfNotifications()
        {
            return notifications;
        }
    }

}
