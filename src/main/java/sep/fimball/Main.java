package sep.fimball;

import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.application.Application;
import javafx.stage.Stage;
import sep.fimball.view.SceneContentManager;

/**
 * Created by kaira on 01.11.2016.
 */
public class Main extends Application
{
    public static void main(String args[])
    {
        SvgImageLoaderFactory.install();
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        new SceneContentManager(primaryStage);
    }
}
