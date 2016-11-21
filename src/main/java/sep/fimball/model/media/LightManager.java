package sep.fimball.model.media;

import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Verwaltet die im Spielfeld platzierten Lichter.
 */
public class LightManager
{
    /**
     * Die Lichter, die verwaltet werden.
     */
    private List<PlacedElement> lights;

    /**
     * Erzeugt einen neuen LightManager.
     */
    public LightManager()
    {
        lights = new ArrayList<>();
    }

    /**
     * Fügt das gegebene PlacedElement der Liste der verwalteten Lichter hinzu, falls dieses ein Licht ist.
     *
     * @param light
     */
    public void addLight(PlacedElement light)
    {
        if (light.getBaseElement().getType() == BaseElementType.LIGHT)
        {
            lights.add(light);
        }
    }

    /**
     * Wechselt die Farbe der verwalteten Lichter zufällig auf andere verfügbaren Farben.
     */
    public void changeLightColors()
    {
        for (PlacedElement light : lights)
        {
            int random = (int) Math.random() * LightColor.values().length;
            LightColor color = LightColor.values()[random];
            //TODO
            // change light color
        }
    }
}
