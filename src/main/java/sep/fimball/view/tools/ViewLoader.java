package sep.fimball.view.tools;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.control.Tab;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
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

    /**
     * Ersetzt alle Texte in der Anwendung die Language Keys als Text haben mit dem Text der entsprechenden Sprache. Ebenfalls werden
     * eventuell vorhandene Tooltips gesetzt.
     *
     * @param fxmlProperties Der Namespace der FXML Datei.
     */
    private void replaceLanguages(ObservableMap<String, Object> fxmlProperties)
    {
        fxmlProperties.forEach(((s, o) ->
        {
            if (o != null)
            {
                if (o instanceof Labeled)
                {
                    Labeled labeled = (Labeled) o;
                    bindToLanguage(labeled.textProperty(), labeled.getText());
                    installTooltip(labeled.textProperty(), labeled);
                }
                if (o instanceof Tab)
                {
                    Tab tab = (Tab) o;
                    bindToLanguage(tab.textProperty(), tab.getText());
                }
                if (o instanceof Tooltip)
                {
                    Tooltip labeled = (Tooltip) o;
                    bindToLanguage(labeled.textProperty(), labeled.getText());
                }
                if(o instanceof Pane){
                    Pane pane = (Pane) o;
                    installToolTip(pane);
                }

            }
        }));
    }

    /**
     * Installiert einen Tooltip bei der Pane. Die Sprache des Tooltips wird automatisch auf die aktuell ausgewählte Sprache gesetzt.
     * @param pane Die Pane, bei der der Tooltip installiert werden soll.
     */
    private void installToolTip(Pane pane)
    {
        if(pane.accessibleHelpProperty().get() != null) {
            Tooltip tooltip = new Tooltip();
            if (pane.getAccessibleHelp().matches("§.*§"))
            {
                String keyValue = pane.getAccessibleHelp().replace("§", "");
                tooltip.textProperty().bind(LanguageManagerViewModel.getInstance().textProperty(keyValue));

                pane.setOnMouseEntered(t ->
                {
                    Node node =(Node)t.getSource();
                    Tooltip.install(node, tooltip);
                });
            }

        }
    }

    /**
     * Bindet den Text eines Nodes welcher Text haben kann an die ausgewählte Sprache.
     *
     * @param textProperty die Property die bestimmt welcher Text beim Node angezeigt wird.
     * @param labeledText der Text der ersetzt werden soll.
     */
    private void bindToLanguage(StringProperty textProperty, String labeledText)
    {
        if (labeledText.matches("!.*!"))
        {
            String keyValue = labeledText.replace("!", "");
            textProperty.bind(LanguageManagerViewModel.getInstance().textProperty(keyValue));
        }
    }

    /**
     * Installiert einen Tooltip beim Node. Die Sprache des Tooltips wird automatisch auf die aktuell ausgewählte Sprache gesetzt.
     *
     * @param labeledText der Text der ersetzt werden soll.
     * @param node der Node bei dem der Tooltip installiert werden soll.
     */
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
