package sep.fimball.model.blueprint.pinballmachine;

/**
 * Diese Klasse repräsentiert die JSON-Beschreibung eines Flipperautomaten.
 */
public class PinballMachineJson {

    /**
     * Speichert die ID des Flipperautomaten.
     */
    public String tableId;

    /**
     * Speichert den Namen des Flipperautomaten.
     */
    public String name;

    /**
     * Speichert die Liste der Highscores des Automaten.
     */
    public HighscoreJson[] highscores;

    /**
     * Diese Klasse repräsentiert die JSON-Beschreibung eines Highscores.
     */
    public static class HighscoreJson {

        /**
         * Speichert den Punktestand des Highscores.
         */
        public long score;

        /**
         * Speichert den Namen des Spielers, der den Highscore erreicht hat.
         */
        public String playerName;
    }

    /**
     * Speichert die Liste der platzierten Elemente.
     */
    public PlacedElementListJson placedElementList;
}
