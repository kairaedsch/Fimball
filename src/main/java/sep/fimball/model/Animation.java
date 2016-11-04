package sep.fimball.model;

import javafx.beans.property.SimpleListProperty;
import sep.fimball.model.elementgreenprint.ElementGreenprint;

public class Animation
{
    class SubAnimation
    {
        private String[] framePaths;
        private double frameDuration;
        // TODO collider group wie???
    }

    // Maps name of animation to the paths of the animation frames
    private SimpleListProperty<SubAnimation> subAnimations;

    public Animation(ElementGreenprint.AnimationObject[] animObjects)
    {

    }
}