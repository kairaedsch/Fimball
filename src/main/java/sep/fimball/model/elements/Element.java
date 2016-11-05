package sep.fimball.model.elements;

import sep.fimball.model.Vector2;

/**
 * Created by kaira on 03.11.2016.
 */
public class Element
{
    public class GreenprintCollider
    {
        public class PhysicsObjectPolygon
        {
            public String type;
            public boolean onGround;
            public Vector2[] points;
        }

        public class PhysicsObjectCircle
        {
            public String type;
            public boolean onGround;
            public double x, y;
            public double radius;
        }

        public String name;
        public PhysicsObjectPolygon[] physicsPolygons;
        public PhysicsObjectCircle[] physicsCircles;
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

    public String editorName;
    public String editorDescription;
    public double rotationAccuracy;
    public GreenprintCollider[] colliders;
    public AnimationObject[] animations;
    public ForceObject[] forces;
}
