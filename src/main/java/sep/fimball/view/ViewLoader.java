package sep.fimball.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

/**
 * Der ViewLoader lädt eine FXML-Datei zusammen mit einer View, welche als FxController im FXML eigetragen ist.
 */
public class ViewLoader<ViewT>
{
    /**
     * Die aus der FXML-Datei geladene RootNode.
     */
    private Node rootNode = null;

    /**
     * Die zur rootNode gehörende View (FxController).
     */
    private ViewT view = null;

    /**
     * Erzeugt einen ViewLoader und lädt die zur viewType gehörende FXML-Datei mit deren zugehöriger View (FxController).
     * @param viewType
     */
    public ViewLoader(ViewType viewType)
    {
        load(viewType.getFxmlPath());
    }

    /**
     * Lädt aus der gegebenen FXML-Datei, setzt deren RootNode als {@code rootNode} und lädt und setzt die zur {@code rootNode} gehörende View.
     * @param fxmlPath Der Pfad zur FXML-Datei aus der geladen werden soll.
     */
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

    /**
     * Gibt die RootNode der geladenen FXML-Datei zurück.
     * @return Die RootNode.
     */
    public Node getRootNode()
    {
        return rootNode;
    }

    /**
     * Gibt die View (FxController) der geladenen FXML-Datei zurück.
     * @return Die View.
     */
    public ViewT getView()
    {
        return view;
    }
}
