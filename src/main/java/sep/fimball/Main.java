package sep.fimball;

import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.application.Application;
import javafx.stage.Stage;
import sep.fimball.model.blueprint.ElementTypeManager;
import sep.fimball.view.SceneManagerView;

/**
 * Created by kaira on 01.11.2016.
 */
public class Main extends Application
{
    public static void main(String args[])
    {
        ElementTypeManager.getInstance();
        SvgImageLoaderFactory.install();
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        new SceneManagerView(primaryStage);
    }
}
