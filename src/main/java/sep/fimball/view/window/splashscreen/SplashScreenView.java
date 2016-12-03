package sep.fimball.view.window.splashscreen;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import sep.fimball.general.data.Config;
import sep.fimball.view.window.WindowView;
import sep.fimball.viewmodel.window.splashscreen.SplashScreenViewModel;

import java.io.File;

/**
 * SplashScreenView ist für die Darstellung des Splashscreens am Anfang des Spiels zuständig.
 */
public class SplashScreenView extends WindowView<SplashScreenViewModel>
{
    /**
     * Der Container, der die Elemente der Splashscreens enthält.
     */
    @FXML
    private VBox splashLayout;

    /**
     * Die Leiste, die anzeigt, wie weit das Laden ist.
     */
    @FXML
    private ProgressBar loadProgress;

    /**
     * Der Text, der beim Laden angezeigt werden soll.
     */
    @FXML
    private Label progressText;

    /**
     * Das anzeigte Bild.
     */
    @FXML
    private
    ImageView splash;

    @Override
    public void setViewModel(SplashScreenViewModel splashScreenViewModel)
    {
        progressText.textProperty().bind(splashScreenViewModel.getProgressText());
        loadProgress.progressProperty().bind(splashScreenViewModel.getLoadProgress());
        File imageFile = new File(Config.pathToLogo());
        Image loadedImage = new Image(imageFile.toURI().toString(), true);
        splash.setImage(loadedImage);
        splashLayout.setEffect(new DropShadow());
    }
}
