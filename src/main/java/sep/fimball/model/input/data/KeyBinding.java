package sep.fimball.model.input.data;

/**
 * Enthält eine Liste von Funktionen, die von einem Tastendruck des Spielers ausgelöst werden können. Welche Taste diese auslöst kann in den Settings festgelegt werden.
 */
public enum KeyBinding
{
    /**
     * Bewegt den linken Flipper beim Drücken der Taste nach oben, beim loslassen kehrt dieser in die Ausgangsposition zurück.
     */
    LEFT_FLIPPER("Linker Flipper"),

    /**
     * Bewegt den rechten Flipper beim Drücken der Taste nach oben, beim loslassen kehrt dieser in die Ausgangsposition zurück.
     */
    RIGHT_FLIPPER("Rechter Flipper"),

    /**
     * Zieht den Plunger auf, sodass dieser den Ball in's Spiel schießen kann.
     */
    PLUNGER("Plunger"),

    /**
     * Pausiert das Spiel. Falls dieses bereits pausiert ist, wird es fortgesetzt.
     */
    PAUSE("Pause"),

    /**
     * Stößt den Tisch etwas nach links. Kann bei zu häufiger Benutzung die Tilt-Funktion auslösen.
     */
    NUDGE_LEFT("Nudge Left"),

    /**
     * Stößt den Tisch etwas nach rechts. Kann bei zu häufiger Benutzung die Tilt-Funktion auslösen.
     */
    NUDGE_RIGHT("Nudge Right"),

    /**
     * Rotiert das ausgewählte BaseElementJson im Editor um den im BaseElementJson vorgegebenen Winkel nach rechts.
     */
    EDITOR_ROTATE("Editor rotate"),

    /**
     * Löscht das im Editor ausgewählte BaseElement.
     */
    EDITOR_DELETE("Editor delete");

    /**
     * Der Name der Funktion.
     */
    private String name;

    /**
     * Erstellt ein neues KeyBinding.
     *
     * @param name Name des KeyBindings.
     */
    KeyBinding(String name)
    {
        this.name = name;
    }

    /**
     * Gibt den Namen der Funktion zurück.
     *
     * @return den Namen der Funktion.
     */
    public String getName()
    {
        return name;
    }

}