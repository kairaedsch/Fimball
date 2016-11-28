package sep.fimball.view.window;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sep.fimball.general.data.Config;
import sep.fimball.viewmodel.window.SplashScreenViewModel;

import java.io.File;

public class SplashScreenView extends WindowView<SplashScreenViewModel>
{

    private static final int SPLASH_WIDTH = 510;
    @FXML
    private VBox splashLayout;

    @FXML
    private ProgressBar loadProgress;

    @FXML
    private Label progressText;

    @FXML
    ImageView splash;

    Pane root;

    SplashScreenViewModel splashScreenViewModel;



    @Override
    public void setViewModel(SplashScreenViewModel splashScreenViewModel)
    {
        this.splashScreenViewModel = splashScreenViewModel;
        progressText.textProperty().bind(splashScreenViewModel.getProgressText());
        loadProgress.progressProperty().bind(splashScreenViewModel.getLoadProgress());
        init();

    }

    private void init()
    {
        File imageFile = new File(Config.pathToLogo());
        Image loadedImage = new Image(imageFile.toURI().toString(), true);
        splash.setImage(loadedImage);
        splash.setFitWidth(SPLASH_WIDTH);
        splash.setFitHeight(SPLASH_WIDTH);
        loadProgress.setMaxWidth(SPLASH_WIDTH );
        progressText.setPrefWidth(SPLASH_WIDTH);
        splashLayout.setEffect(new DropShadow());


    }



}
