package sep.fimball.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

/**
 * Created by kaira on 02.11.2016.
 */
public class ViewLoader<ViewT>
{
    private Node rootNode = null;
    private ViewT view = null;

    public ViewLoader(ViewType viewType)
    {
        load(viewType.getFxmlPath());
    }

    private void load(String fxmlPath)
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fxmlPath));

        try
        {
            rootNode = (Node) loader.load();
            view = loader.getController();
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

    public ViewT getView()
    {
        return view;
    }
}
