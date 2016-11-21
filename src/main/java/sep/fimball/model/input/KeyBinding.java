package sep.fimball.model.input;

/**
 * Enthält eine Liste von Funktionen, die von einem Tastendruck des Spielers ausgelöst werden können. Welche Taste diese auslöst kann in den Settings festgelegt werden.
 */
public enum KeyBinding
{
    /**
     * Bewegt den linken Flipper beim Drücken der Taste nach oben, beim loslassen kehrt dieser in die Ausgangsposition zurück.
     */
    LEFT_FLIPPER,

    /**
     * Bewegt den rechten Flipper beim Drücken der Taste nach oben, beim loslassen kehrt dieser in die Ausgangsposition zurück.
     */
    RIGHT_FLIPPER,

    /**
     * Zieht den Plunger auf, sodass dieser den Ball in's Spiel schießen kann.
     */
    PLUNGER,

    /**
     * Pausiert das Spiel. Falls dieses bereits pausiert ist, wird es fortgesetzt.
     */
    PAUSE,

    /**
     * Stößt den Tisch etwas nach links. Kann bei zu häufiger Benutzung die Tilt-Funktion auslösen.
     */
    NUDGE_LEFT,

    /**
     * Stößt den Tisch etwas nach rechts. Kann bei zu häufiger Benutzung die Tilt-Funktion auslösen.
     */
    NUDGE_RIGHT,

    /**
     * Rotiert das ausgewählte BaseElementJson im Editor um den im BaseElementJson vorgegebenen Winkel nach rechts.
     */
    EDITOR_ROTATE,

    /**
     * Löscht das im Editor ausgewählte BaseElement.
     */
    EDITOR_DELETE,

    DEBUG_LEFT,
    DEBUG_RIGHT
}