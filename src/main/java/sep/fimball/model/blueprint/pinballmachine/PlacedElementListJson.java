package sep.fimball.model.blueprint.pinballmachine;

import sep.fimball.general.data.Vector2;

/**
 * Created by marc on 16.11.16.
 */
public class PlacedElementListJson {
    public PlacedElementJson[] elements;

    public static class PlacedElementJson {
        public String elementTypeId;
        public Vector2 position;
        public int points;
        public double multiplier;
    }
}
