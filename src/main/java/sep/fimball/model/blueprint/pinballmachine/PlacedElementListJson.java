package sep.fimball.model.blueprint.pinballmachine;

/**
 * Diese Klasse repräsentiert die JSON-Beschreibung einer Liste von platziereten Elementen.
 */
public class PlacedElementListJson {

    /**
     * Speichert die platzierten Spielelemente.
     */
    public PlacedElementJson[] elements;

    /**
     * Diese Klasse repräsentiert die JSON-Beschreibung eines platzierten Spielelements.
     */
    public static class PlacedElementJson {

        /**
         * Speichert die Id des BaseElements des platzierten Elements.
         */
        public String BaseElementId;

        /**
         * Speichert die x-Position des platzierten Elements.
         */
        public double positionX;

        /**
         * Speichert die y-Position des platzierten Elements.
         */
        public double positionY;

        /**
         * Speichert die Anzahl der Punkte, die das Treffen des Elements durch die Kugel bringt.
         */
        public int points;

        /**
         * TODO
         */
        public double multiplier;
    }
}
