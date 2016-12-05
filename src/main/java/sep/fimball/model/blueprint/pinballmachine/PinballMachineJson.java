package sep.fimball.model.blueprint.pinballmachine;

/**
 * Diese Klasse repräsentiert die JSON-Beschreibung eines Flipperautomaten ohne seine Elemente.
 */
public class PinballMachineJson
{
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
    public static class HighscoreJson
    {
        /**
         * Speichert den Punktestand des Highscores.
         */
        public long score;

        /**
         * Speichert den Namen des Spielers, der den Highscore erreicht hat.
         */
        public String playerName;
    }
}
