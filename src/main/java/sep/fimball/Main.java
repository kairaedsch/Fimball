package sep.fimball;

import javafx.application.Application;
import javafx.stage.Stage;
import sep.fimball.view.SceneContentManager;

/**
 * Created by kaira on 01.11.2016.
 */
public class Main
{
    public static void main(String args[])
    {
        Application app = new Application()
        {
            @Override
            public void start(Stage primaryStage) throws Exception
            {
                new SceneContentManager(primaryStage);
            }
        };
    }
}
