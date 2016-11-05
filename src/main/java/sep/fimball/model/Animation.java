package sep.fimball.model;

import javafx.beans.property.ListProperty;
import sep.fimball.model.elements.Element;

public class Animation
{
    class SubAnimation
    {
        private String[] framePaths;
        private double frameDuration;
        // TODO collider group wie???
    }

    // Maps name of animation to the paths of the animation frames
    private ListProperty<SubAnimation> subAnimations;

    public Animation(Element.AnimationObject[] animObjects)
    {

    }
}