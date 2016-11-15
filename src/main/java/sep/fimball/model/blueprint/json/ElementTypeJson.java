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

    public PhysicElementType physicElement;
    public MediaElementType mediaElement;
    public GameElementType gameElement;

    public static class PhysicElementType
    {
        public PhysicCollider[] colliders;

        public static class PhysicCollider
        {
            public String colliderId;
            public WorldLayer layer;

            /**
             * Speichert die Polygon-Collider des Elementtyps.
             */
            public Polygon[] polygonShapes;

            /**
             * Speichert die Kreis-Collider des Elementtyps.
             */
            public Circle[] circleShapes;

            /**
             * Die Kräfte, die bei Interaktion mit diesem Elementtyp auftreten.
             */
            public ColliderType collisionType;

            public static class Polygon
            {
                public boolean onGround;
                public Vector2[] points;
            }

            public static class Circle
            {
                public boolean onGround;
                public double x, y;
                public double radius;
            }

            public static class ColliderType
            {
                public String forceType;
                public double strength;
                public double accelX;
                public double accelY;
            }
        }
    }

    public static class MediaElementType
    {
        public MediaElementTypeGeneral general;

        public MediaElementEvent[] events;

        public static class MediaElementTypeGeneral
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

        public static class MediaElementEvent
        {
            public String colliderId;

            public String soundName;

            /**
             * Die Animationen, die dieser Elementtyp bei Kollision mit dem Collider abspielt.
             */
            public Animation animation;

            public static class Animation
            {
                public int frameCount;
                public double duration;
                public String animationName;
            }
        }
    }

    public static class GameElementType
    {
        public GameElementTypeGeneral general;

        public GameElementEvent[] events;

        public static class GameElementTypeGeneral
        {
            /**
             * Speichert die Punkte, die ein Treffer durch die Kugel bringt.
             */
            public boolean givesPoints;
        }

        public static class GameElementEvent
        {
            public String colliderId;
        }
    }
}
