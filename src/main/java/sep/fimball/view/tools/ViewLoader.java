package sep.fimball.view.tools;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import sep.fimball.view.ViewType;

import java.io.IOException;

/**
 * Der ViewLoader lädt eine FXML-Datei zusammen mit einer View, die als FxController im FXML eingetragen ist.
 * @param <ViewT> Die Klasse der View (FxController).
 */
public class ViewLoader<ViewT>
{
    /**
     * Die aus der FXML-Datei geladene RootNode.
     */
    private Node rootNode;

    /**
     * Die zur rootNode gehörende View (FxController).
     */
    private ViewT view;

    /**
     * Erzeugt einen ViewLoader und lädt die zur {@code viewType} gehörende FXML-Datei mit zugehöriger View (FxController).
     *
     * @param viewType Die Art der View, die geladen werden soll.
     * @throws IllegalStateException Falls das Laden fehlschlägt.
     */
    public ViewLoader(ViewType viewType) throws IllegalStateException
    {
        load(viewType.getFxmlPath());
    }

    /**
     * Lädt aus der gegebenen FXML-Datei, setzt die RootNode als {@code rootNode}, lädt die eingestellte Sprache und lädt und setzt die zur {@code rootNode} gehörende View.
     *
     * @param fxmlPath Der Pfad zur FXML-Datei, aus der geladen werden soll.
     * @throws IllegalStateException Falls das Laden fehlschlägt.
     */
    private void load(String fxmlPath) throws IllegalStateException
    {
        rootNode = null;
        view = null;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fxmlPath));
        try
        {
            rootNode = loader.load();
            view = loader.getController();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new IllegalStateException();
        }

        if(rootNode == null || view == null)
        {
            throw new IllegalStateException();
        }
    }

    /**
     * Gibt die RootNode der geladenen FXML-Datei zurück.
     *
     * @return Die RootNode.
     */
    public Node getRootNode()
    {
        return rootNode;
    }

    /**
     * Gibt die View (FxController) der geladenen FXML-Datei zurück.
     *
     * @return Die View.
     */
    public ViewT getView()
    {
        return view;
    }
}