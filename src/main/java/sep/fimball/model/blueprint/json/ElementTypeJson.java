package sep.fimball.model.blueprint.json;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.ElementTypeType;

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
            public String mediaTriggerId;

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
            public ForceObject forces;

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

            public static class ForceObject
            {
                public String forceType;
                public double strength;
            }
        }
    }

    public static class MediaElementType
    {
        /**
         * Bezeichnung des Elementtyps im Editor.
         */
        public String editorName;

        /**
         * Text, der den Elementtyp im Editor beschreibt.
         */
        public String editorDescription;

        public MediaTrigger[] triggers;


        public static class MediaTrigger
        {
            public String id;

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
        /**
         * Speichert die Punkte, die ein Treffer durch die Kugel bringt.
         */
        public boolean givesPoints;
    }
}
