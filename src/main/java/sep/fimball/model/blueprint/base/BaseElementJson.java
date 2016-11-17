package sep.fimball.model.blueprint.base;

/**
 * Diese Klasse stellt einen Elementtyp im Allgemeinen dar, der im Flipperautomat genutzt werden kann, um dieses serialisiert abzuspeichern (z.B. Bumper).
 */
public class BaseElementJson
{
    /**
     * Speichert den Typ des BaseElements.
     */
    public String elementType;

    /**
     * Speichert den Typ des korrespondierenden PhysicElements.
     */
    public PhysicElementTypeJson physicElement;

    /**
     * Speichert den Typ des korrespondierenden MediaElements.
     */
    public MediaElementTypeJson mediaElement;

    /**
     * Speichert den Typ des korrespondierenden RuleElements.
     */
    public RuleElementTypeJson ruleElement;

    /**
     * Diese Klasse enthält die zur Serialisierung und Erzeugung der Repräsentation eines Spielelements in der Physik wichtigen Informationen.
     */
    public static class PhysicElementTypeJson
    {
        /**
         * Speichert die Collider des PhysicElements.
         */
        public PhysicColliderJson[] colliders;

        /**
         * Diese Klasse enthält die Informationen über die Collider eines Spielelements
         */
        public static class PhysicColliderJson
        {
            /**
             * Speichert die ID des Colliders.
             */
            public String colliderId;

            /**
             * Speichert die Ebene des Colliders
             */
            public String layer;

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

                public VerticeJson[] vertices;

                /**
                 * Diese Klasse repäsentiert eine Kante eines Polygons.
                 */
                public static class VerticeJson {

                    /**
                     * Speichert die x-Richtung der Kante.
                     */
                    public double xDirection;

                    /**
                     * Speichert die y-Richtung der Kante.
                     */
                    public double yDirection;
                }
            }

            /**
             * Diese Klasse enthält die spezifischen Informationen über kreisförmige Collider eines Objekts.
             */
            public static class CircleJson
            {
                /**
                 * Speichert die x-Position des Mittelpunktes des CircleColliders.
                 */
                public double x;

                /**
                 * Speichert die y-Position des Mittelpunktes des CircleColliders.
                 */
                public double y;

                /**
                 * Speichert den Radius des CircleColliders.
                 */
                public double radius;
            }

            /**
             * Diese Klasse enthält alle allgemeinen Informationen über einen Collider.
             */
            public static class ColliderTypeJson
            {
                /**
                 * Speichert den Named des Typs des Colliders.
                 */
                public String type;

                /**
                 * Speichert die Kraft, die der ColliderType auf die Kugel ausübt.
                 */
                public double strength;

                /**
                 * Speichert die Beschleunigung, die der ColliderType auf die Kugel ausübt, in x-Richtung.
                 */
                public double accelX;

                /**
                 * Speichert die Beschleunigung, die der ColliderType auf die Kugel ausübt, in x-Richtung.
                 */
                public double accelY;
            }
        }
    }

    /**
     * Diese Klasse enthält die Informationen über Bilder, Animationen, die Eigenschaften im Editor und Soundeffekte des Spielelements.
     */
    public static class MediaElementTypeJson
    {
        /**
         * TODO
         */
        public MediaElementTypeGeneralJson general;

        /**
         * TODO
         */
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
            /**
             * TODO
             */
            public String colliderId;

            /**
             * TODO
             */
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
                /**
                 * TODO
                 */
                public int frameCount;

                /**
                 * TODO
                 */
                public double duration;

                /**
                 * TODO
                 */
                public String animationName;
            }
        }
    }

    /**
     * TODO
     */
    public static class RuleElementTypeJson
    {
        /**
         * TODO
         */
        public RuleElementTypeGeneralJson general;

        /**
         * TODO
         */
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
            /**
             * TODO
             */
            public String colliderId;
        }
    }
}
