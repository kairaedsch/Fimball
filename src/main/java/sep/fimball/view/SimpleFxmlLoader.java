package sep.fimball.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

/**
 * Created by kaira on 02.11.2016.
 */
public class SimpleFxmlLoader
{
    private Node rootNode = null;
    private Object fxController = null;

    public SimpleFxmlLoader(ViewType viewType)
    {
        load(viewType.getFxmlPath());
    }

    private void load(String fxmlPath)
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fxmlPath));

        try
        {
            rootNode = (Node) loader.load();
            fxController = loader.getController();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Node getRootNode()
    {
        return rootNode;
    }

    public Object getFxController()
    {
        return fxController;
    }
}
