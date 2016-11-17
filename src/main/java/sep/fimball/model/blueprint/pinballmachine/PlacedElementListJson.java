package sep.fimball.model.blueprint.pinballmachine;

import sep.fimball.general.data.Vector2;

/**
 * Diese Klasse repräsentiert die JSON-Beschreibung einer Liste von platzierten Elementen.
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
         * Speichert die Position des platzierten Elements.
         */
        public Vector2 position;

        /**
         * Speichert die y-Position des platzierten Elements.
         */
        public double rotation;

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
