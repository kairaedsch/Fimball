package sep.fimball.viewmodel.window;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;

public class SplashScreenViewModel extends WindowViewModel
{
    private StringProperty progressText;
    private DoubleProperty loadProgress;
    private MainMenuViewModel mainMenuViewModel;

    public SplashScreenViewModel()
    {
        super(WindowType.SPLASH_SCREEN);
        progressText = new SimpleStringProperty();
        loadProgress = new SimpleDoubleProperty();
        mainMenuViewModel = new MainMenuViewModel();
        start();
    }


    public void showMainMenu()
    {
        sceneManager.setWindow(mainMenuViewModel);
    }


    public void start()
    {
        final Task<ObservableList<String>> messageTask = new Task<ObservableList<String>>()
        {
            @Override
            protected ObservableList<String> call() throws InterruptedException
            {
                ObservableList<String> messages =
                        FXCollections.<String>observableArrayList();
                ObservableList<String> availableMessages =
                        FXCollections.observableArrayList(
                                "Elements loaded", "Machines loaded", "Settings loaded", "Sounds loaded");

                updateMessage("Initialising");
                for (int i = 0; i < availableMessages.size(); i++)
                {
                    Thread.sleep(400);
                    updateProgress(i + 1, availableMessages.size());
                    String nextMessage = availableMessages.get(i);
                    messages.add(nextMessage);
                    updateMessage(nextMessage);
                }
                Thread.sleep(400);
                updateMessage("Everything done");

                return messages;
            }
        };

        showSplash(
                messageTask,
                () -> showMainMenu()
        );
        new Thread(messageTask).start();
    }


    private void showSplash(
            Task<?> task,
            InitCompletionHandler initCompletionHandler
    )
    {

        progressText.bind(task.messageProperty());

        loadProgress.bind(task.progressProperty());
        task.stateProperty().addListener((observableValue, oldState, newState) ->
        {
            if (newState == Worker.State.SUCCEEDED)
            {
                loadProgress.unbind();
                loadProgress.set(1);

                initCompletionHandler.complete();
            } // todo add code to gracefully handle other task states.
        });


    }

    public DoubleProperty getLoadProgress()
    {
        return loadProgress;
    }

    public StringProperty getProgressText()
    {
        return progressText;
    }

    public interface InitCompletionHandler
    {
        void complete();
    }
}
