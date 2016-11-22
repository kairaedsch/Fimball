package sep.fimball;

import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.application.Application;
import javafx.stage.Stage;
import sep.fimball.general.data.Config;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.view.SceneManagerView;

/**
 * Stellt den Einstiegspunkt der Applikation dar
 */
public class Main extends Application
{
    /**
     * Der Einstiegspunkt der Applikation
     *
     * @param args Argumente welche an die Applikation gegeben werden
     */
    public static void main(String args[])
    {
        Config.config();
        BaseElementManager.getInstance();
        SvgImageLoaderFactory.install();
        launch();
    }

    /**
     * Startet das Userinterface der Applikation
     *
     * @param primaryStage Die von JavaFX erstellte Stage, auf der gearbeitet wird
     * @throws Exception Falls Probleme beim Starten von JavaFX auftreten
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        new SceneManagerView(primaryStage);
    }
}
