package sep.fimball.model.blueprint.base;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.WorldLayer;

/**
 * Diese Klasse stellt einen Elementtyp im Allgemeinen dar, der im Flipperautomat genutzt werden kann, um dieses serialisiert abzuspeichern (z.B. Bumper).
 */
public class BaseElementJson
{
    public BaseElementType elementType;

    public PhysicElementTypeJson physicElement;
    public MediaElementTypeJson mediaElement;
    public RuleElementTypeJson ruleElement;

    /**
     * Diese Klasse enthält die zur Serialisierung und Erzeugung der Repräsentation eines Spielelements in der Physik wichtigen Informationen.
     */
    public static class PhysicElementTypeJson
    {
        public PhysicColliderJson[] colliders;

        /**
         * Diese Klasse enthält die Informationen über die Collider eines Spielelements
         */
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

            /**
             * Diese Klasse enthält die spezifischen Informationen über einen Collider mit der Form eines Polygons.
             */
            public static class PolygonJson
            {
                public Vector2[] vertices;
            }

            /**
             * Diese Klasse enthält die spezifischen Informationen über kreisförmige Collider eines Objekts.
             */
            public static class CircleJson
            {
                public double x, y;
                public double radius;
            }

            /**
             * Diese Klasse enthält alle allgemeinen Informationen über einen Collider.
             */
            public static class ColliderTypeJson
            {
                public String type;
                public double strength;
                public double accelX;
                public double accelY;
            }
        }
    }

    /**
     * Diese Klasse enthält die Informationen über Bilder, Animationen, die Eigenschaften im Editor und Soundeffekte des Spielelements.
     */
    public static class MediaElementTypeJson
    {
        public MediaElementTypeGeneralJson general;

        public MediaElementEventJson[] events;

        /**
         * TODO
         */
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

            public boolean canRotate;
            /**
             * Die Genaugigkeit der Rotation beim Setzen und Bearbeiten im Editor, d.h. um wie viel Grad man Elemente von diesem Typ drehen kann.
             */
            public double rotationAccuracy;
        }

        /**
         * TODO
         */
        public static class MediaElementEventJson
        {
            public String colliderId;

            public String soundName;

            /**
             * Die Animationen, die dieser Elementtyp bei Kollision mit dem Collider abspielt.
             */
            public AnimationJson animation;

            /**
             * TODO
             */
            public static class AnimationJson
            {
                public int frameCount;
                public double duration;
                public String animationName;
            }
        }
    }

    /**
     * TODO
     */
    public static class RuleElementTypeJson
    {
        public RuleElementTypeGeneralJson general;

        public RuleElementEventJson[] events;

        /**
         * TODO
         */
        public static class RuleElementTypeGeneralJson
        {
            /**
             * Speichert die Punkte, die ein Treffer durch die Kugel bringt.
             */
            public boolean givesPoints;
        }

        /**
         * TODO
         */
        public static class RuleElementEventJson
        {
            public String colliderId;
        }
    }
}
