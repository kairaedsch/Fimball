package sep.fimball.view;

/**
 * Das ViewType-Interface stellt sicher, das eine Methode zur Rückgabe eines FXML-Datei-Pfades existiert.
 */
public interface ViewType
{
    /**
     * Gibt einen FXML-Datei-Pfad zurück.
     * @return
     */
    public String getFxmlPath();
}
