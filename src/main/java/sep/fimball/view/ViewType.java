package sep.fimball.view;

/**
 * Das ViewType-Interface stellt sicher, dass eine Methode zur Rückgabe eines FXML-Datei-Pfades existiert.
 */
public interface ViewType
{
    /**
     * Gibt einen FXML-Datei-Pfad zurück.
     *
     * @return Der Pfad einer FXML-Datei.
     */
    public String getFxmlPath();
}
