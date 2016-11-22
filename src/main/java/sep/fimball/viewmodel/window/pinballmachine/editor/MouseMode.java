package sep.fimball.viewmodel.window.pinballmachine.editor;

/**
 * MouseMode enthält die unterschiedlichen Modi der Benutzung der Elemente im Editor durch die Maus.
 */
public enum MouseMode
{
    /**
     * Die Maus wird zum "ziehen" der gesamten Spielfläche genutzt. Es wird die Position der Kamera geändert.
     */
    DRAGGING,

    /**
     * Die Maus wird zum platzieren von Spielelementen genutzt
     */
    PLACING,

    /**
     * Die Maus wird zur Auswahl von Spielelementen genutzt
     */
    SELECTING
}
