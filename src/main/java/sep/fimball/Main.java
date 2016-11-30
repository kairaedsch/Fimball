package sep.fimball;

import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import sep.fimball.view.SceneManagerView;
import sep.fimball.viewmodel.SceneManagerViewModel;

/**
 * Stellt den Einstiegspunkt der Applikation dar.
 */
public class Main extends Application
{
    /**
     * Der Einstiegspunkt der Applikation.
     *
     * @param args Argumente welche an die Applikation gegeben werden
     */
    public static void main(String args[])
    {
        SvgImageLoaderFactory.install();
        launch();
    }

    /**
     * Startet das User-Interface der Applikation und damit die gesamte Anwendung.
     *
     * @param primaryStage Die von JavaFX erstellte Stage, auf der gearbeitet wird
     * @throws Exception Falls Probleme beim Starten von JavaFX auftreten
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setOnCloseRequest(t ->
        {
            Platform.exit();
            System.exit(0);
        });

        new SceneManagerView(primaryStage, new SceneManagerViewModel());
    }
}
