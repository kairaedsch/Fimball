package sep.fimball.model.media;

import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;

import java.util.ArrayList;
import java.util.List;

public class LightManager
{
    private List<PlacedElement> lights;

    public LightManager() {
        lights = new ArrayList<>();
    }

    public void addLight (PlacedElement light){
        if (light.getBaseElement().getType() == BaseElementType.LIGHT) {
            lights.add(light);
        }
    }

    public void changeLightColors() {
        for (PlacedElement light : lights) {
            int random = (int) Math.random() * LightColor.values().length;
            LightColor color = LightColor.values()[random];
            //TODO
            // change light color
        }
    }
}
