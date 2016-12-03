package sep.fimball.viewmodel.window.splashscreen;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import sep.fimball.viewmodel.window.WindowType;
import sep.fimball.viewmodel.window.WindowViewModel;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;

/**
 * Das SplashScreenViewModel stellt der View Informationen über den Fortschritt des Ladens im Splashscreen bereit.
 */
public class SplashScreenViewModel extends WindowViewModel
{
    /**
     * Der Text, der während des Lade-Fortschritts anzeigt wird.
     */
    private StringProperty progressText;

    /**
     * Der Fortschritt des Ladens.
     */
    private DoubleProperty loadProgress;

    /**
     * Erzeugt ein neues SplashScreenViewModel.
     */
    public SplashScreenViewModel()
    {
        super(WindowType.SPLASH_SCREEN);
        progressText = new SimpleStringProperty();
        loadProgress = new SimpleDoubleProperty();
        startLoading();
    }

    /**
     * Benachrichtigt den ScemeManager, dass das Hauptmenü angezeigt werden soll.
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
        final Task<ObservableList<String>> messageTask = new Task<ObservableList<String>>()
        {
            @Override
            protected ObservableList<String> call() throws InterruptedException
            {
                ObservableList<String> messages = FXCollections.observableArrayList();
                ObservableList<String> availableMessages = FXCollections.observableArrayList("Elements loaded", "Machines loaded", "Settings loaded", "Sounds loaded");

                updateMessage("Initialising");
                for (int i = 0; i < availableMessages.size(); i++)
                {
                    Thread.sleep(40);
                    updateProgress(i + 1, availableMessages.size());
                    String nextMessage = availableMessages.get(i);
                    messages.add(nextMessage);
                    updateMessage(nextMessage);
                }
                Thread.sleep(40);
                updateMessage("Everything done");

                return messages;
            }
        };

        bindToTask(messageTask, this::showMainMenu);
        new Thread(messageTask).start();
    }


    /**
     * Bindet den {@code progressText} und den {@code loadProgress} an die Properties des Tasks.
     *
     * @param task              Der Task, an den gebunden werden soll.
     * @param CompletionHandler Der Handler, der das Ende des Tasks händelt.
     */
    private void bindToTask(Task<?> task, CompletionHandler CompletionHandler)
    {
        progressText.bind(task.messageProperty());

        loadProgress.bind(task.progressProperty());
        task.stateProperty().addListener((observableValue, oldState, newState) ->
        {
            if (newState == Worker.State.SUCCEEDED)
            {
                loadProgress.unbind();
                loadProgress.set(1);

                CompletionHandler.complete();
            } // todo add code to gracefully handle other task states.
        });


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
     * Händelt das Abschließen eines Tasks.
     */
    public interface CompletionHandler
    {
        /**
         * Wird aufgerufen, wenn der Task fertig ist.
         */
        void complete();
    }
}
