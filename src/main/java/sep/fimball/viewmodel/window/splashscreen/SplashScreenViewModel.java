package sep.fimball.viewmodel.window.splashscreen;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Duration;
import sep.fimball.viewmodel.LanguageManagerViewModel;
import sep.fimball.viewmodel.window.WindowType;
import sep.fimball.viewmodel.window.WindowViewModel;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Das SplashScreenViewModel stellt der View Informationen über den Fortschritt des Ladens im Splashscreen bereit.
 */
public class SplashScreenViewModel extends WindowViewModel
{
    /**
     * Der Abstand zwischen der Aktualisierungen des Lade-Fortschritts.
     */
    private static final double LOOP_TICK = 0.5;

    /**
     * Die Anzahl der Texte, die bem Fortschritt angezeigt werden sollen.
     */
    private final int numberOfTexts;

    /**
     * Der Text, der während des Lade-Fortschritts anzeigt wird.
     */
    private StringProperty progressText;

    /**
     * Der Fortschritt des Ladens.
     */
    private DoubleProperty loadProgress;

    /**
     * Die Texte, die nacheinander beim Laden angezeigt werden sollen.
     */
    private Queue<String> texts;

    /**
     * Erzeugt ein neues SplashScreenViewModel, das den Ladevorgang startet.
     */
    public SplashScreenViewModel()
    {
        super(WindowType.SPLASH_SCREEN);
        progressText = new SimpleStringProperty(getTextByKey("splashscreen.loading.key"));
        loadProgress = new SimpleDoubleProperty(0);
        texts = new ArrayDeque<>();
        offerTexts();
        numberOfTexts = texts.size();
        startLoading();
    }

    /**
     * Gibt den Fortschritt des Ladens zurück.
     *
     * @return Der Fortschritt des Ladens.
     */
    public DoubleProperty getLoadProgress()
    {
        return loadProgress;
    }

    /**
     * Gibt den Text, der das Laden beschreibt, zurück.
     *
     * @return Der Text, der das Laden beschreibt.
     */
    public StringProperty getProgressText()
    {
        return progressText;
    }

    /**
     * Fügt Texte in die Text-Queue ein.
     */
    private void offerTexts()
    {
        texts.offer(getTextByKey("splashscreen.elements.key"));
        texts.offer(getTextByKey("splashscreen.machines.key"));
        texts.offer(getTextByKey("splashscreen.settings.key"));
        texts.offer(getTextByKey("splashscreen.sounds.key"));
    }

    /**
     * Gibt den zum Key gehörenden Text zurück.
     *
     * @param key Der Key, dessen zugehöriger Text zurückgegeben werden soll.
     * @return Der zum Key gehörende Text.
     */
    private String getTextByKey(String key)
    {
        return LanguageManagerViewModel.getInstance().textProperty(key).get();
    }

    /**
     * Benachrichtigt den SceneManager, dass das Hauptmenü angezeigt werden soll.
     */
    private void showMainMenu()
    {
        sceneManager.setWindow(new MainMenuViewModel());
    }

    /**
     * Startet das simulierte Laden.
     */
    private void startLoading()
    {
        Timeline loop = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(LOOP_TICK), (event -> loopUpdate()));
        loop.setCycleCount(texts.size() + 1);
        loop.getKeyFrames().add(keyFrame);
        loop.play();
        loop.statusProperty().addListener(((observable, oldStatus, newStatus) ->
        {
            if (newStatus == Animation.Status.STOPPED)
            {
                showMainMenu();
            }
        }));
    }

    /**
     * Aktualisiert den Lade-Fortschritt.
     */
    private void loopUpdate()
    {
        progressText.set(texts.poll());
        loadProgress.set(loadProgress.get() + (1.0 / numberOfTexts));
    }
}
