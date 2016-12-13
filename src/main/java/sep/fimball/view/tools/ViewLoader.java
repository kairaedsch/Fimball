package sep.fimball.view.tools;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.control.Tab;
import javafx.scene.control.Tooltip;
import sep.fimball.view.ViewType;
import sep.fimball.viewmodel.LanguageManagerViewModel;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Der ViewLoader lädt eine FXML-Datei zusammen mit einer View, die als FxController im FXML eingetragen ist.
 *
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
            replaceLanguages(loader.getNamespace());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new IllegalStateException();
        }

        if (rootNode == null || view == null)
        {
            throw new IllegalStateException();
        }
    }

    private void replaceLanguages(ObservableMap<String, Object> fxmlProperties)
    {
        fxmlProperties.forEach(((s, o) ->
        {
            if (o != null)
            {
                if (o instanceof Labeled)
                {
                    Labeled labeled = (Labeled) o;
                    bind(labeled.textProperty(), labeled.getText());
                    installTooltip(labeled.textProperty(), labeled);
                }
                if (o instanceof Tab)
                {
                    Tab tab = (Tab) o;
                    bind(tab.textProperty(), tab.getText());
                }
                if (o instanceof Tooltip)
                {
                    Tooltip labeled = (Tooltip) o;
                    bind(labeled.textProperty(), labeled.getText());
                }
            }
        }));
    }

    private void bind(StringProperty textProperty, String labeledText)
    {
        if (labeledText.matches("!.*!"))
        {
            String keyValue = labeledText.replace("!", "");
            textProperty.bind(LanguageManagerViewModel.getInstance().textProperty(keyValue));
        }
    }

    private void installTooltip(StringProperty labeledText, Node node)
    {
        String regexPattern = "§.*§";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(labeledText.get());

        if (matcher.matches())
        {
            String tooltipKey = matcher.group().replace("§", "");
            Tooltip tooltip = new Tooltip();
            tooltip.textProperty().bind(LanguageManagerViewModel.getInstance().textProperty(tooltipKey));
            Tooltip.install(node, tooltip);

            if (!labeledText.isBound())
            {
                labeledText.set(labeledText.get().replaceAll(regexPattern, ""));
            }
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
