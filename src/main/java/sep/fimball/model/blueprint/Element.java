package sep.fimball.model.blueprint;

import sep.fimball.general.data.Vector2;

/**
 * Created by kaira on 03.11.2016.
 */
public class Element
{
    public String editorName;
    public String editorDescription;
    public double rotationAccuracy;
    // TODO make enum
    public String elementType;
    public Collider[] colliders;

    public class Collider
    {
        public Polygon[] polygons;
        public Circle[] circles;
        public AnimationObject animations;
        public ForceObject forces;
        public boolean givesPoints;

        public class Polygon
        {
            public boolean onGround;
            public Vector2[] points;
        }

        public class Circle
        {
            public boolean onGround;
            public double x, y;
            public double radius;
        }

        public class AnimationObject
        {
            public int frameCount;
            public double duration;
            public String frameName;
        }

        public class ForceObject
        {
            public String forceType;
            public double strength;
        }
    }
}
