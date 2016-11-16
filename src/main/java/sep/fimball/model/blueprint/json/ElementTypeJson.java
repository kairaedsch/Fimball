package sep.fimball.model.blueprint.json;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.elementtype.ElementTypeType;
import sep.fimball.model.physics.WorldLayer;

/**
 * Diese Klasse stellt einen Elementtyp im Allgemeinen dar, der im Flipperautomat genutzt werden kann, um dieses serialisiert abzuspeichern (z.B. Bumper).
 */
public class ElementTypeJson
{
    /**
     * Die Genaugigkeit der Rotation beim Setzen und Bearbeiten im Editor, d.h. um wie viel Grad man Elemente von diesem Typ drehen kann.
     */
    public double rotationAccuracy;

    public ElementTypeType elementType;

    public PhysicElementTypeJson physicElement;
    public MediaElementTypeJson mediaElement;
    public RuleElementTypeJson ruleElement;

    public static class PhysicElementTypeJson
    {
        public PhysicColliderJson[] colliders;

        public static class PhysicColliderJson
        {
            public String colliderId;
            public WorldLayer layer;

            /**
             * Speichert die Polygon-Collider des Elementtyps.
             */
            public PolygonJson[] polygonShapes;

            /**
             * Speichert die Kreis-Collider des Elementtyps.
             */
            public CircleJson[] circleShapes;

            /**
             * Die Kräfte, die bei Interaktion mit diesem Elementtyp auftreten.
             */
            public ColliderTypeJson collisionType;

            public static class PolygonJson
            {
                public boolean onGround;
                public Vector2[] points;
            }

            public static class CircleJson
            {
                public boolean onGround;
                public double x, y;
                public double radius;
            }

            public static class ColliderTypeJson
            {
                public String forceType;
                public double strength;
                public double accelX;
                public double accelY;
                public double accelerationSpeed;
            }
        }
    }

    public static class MediaElementTypeJson
    {
        public MediaElementTypeGeneralJson general;

        public MediaElementEventJson[] events;

        public static class MediaElementTypeGeneralJson
        {
            /**
             * Bezeichnung des Elementtyps im Editor.
             */
            public String editorName;

            /**
             * Text, der den Elementtyp im Editor beschreibt.
             */
            public String editorDescription;
        }

        public static class MediaElementEventJson
        {
            public String colliderId;

            public String soundName;

            /**
             * Die Animationen, die dieser Elementtyp bei Kollision mit dem Collider abspielt.
             */
            public AnimationJson animation;

            public static class AnimationJson
            {
                public int frameCount;
                public double duration;
                public String animationName;
            }
        }
    }

    public static class RuleElementTypeJson
    {
        public RuleElementTypeGeneralJson general;

        public RuleElementEventJson[] events;

        public static class RuleElementTypeGeneralJson
        {
            /**
             * Speichert die Punkte, die ein Treffer durch die Kugel bringt.
             */
            public boolean givesPoints;
        }

        public static class RuleElementEventJson
        {
            public String colliderId;
        }
    }
}
