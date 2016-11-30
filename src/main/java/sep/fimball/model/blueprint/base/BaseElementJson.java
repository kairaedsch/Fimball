package sep.fimball.model.blueprint.base;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.collider.WorldLayer;

/**
 * Diese Klasse stellt die JSON-Repräsentation eines BaseElements dar, das alle Informationen enthält, die gemeinsam ein Spielfeldelement bestimmen.
 */
public class BaseElementJson
{
    /**
     * Speichert den Typ des BaseElements.
     */
    public BaseElementType elementType;

    /**
     * Speichert den Typ des korrespondierenden PhysicElements.
     */
    public PhysicElementJson physicElement;

    /**
     * Speichert den Typ des korrespondierenden MediaElements.
     */
    public MediaElementJson mediaElement;

    /**
     * Speichert den Typ des korrespondierenden RuleElements.
     */
    public RuleElementJson ruleElement;

    /**
     * Diese Klasse enthält die zur Serialisierung und Erzeugung der Repräsentation eines Spielelements in der Physik wichtigen Informationen.
     */
    public static class PhysicElementJson
    {
        /**
         * Speichert den Punkt, um den das Element rotiert wird.
         */
        public Vector2 pivotPoint;

        /**
         * Speichert die Collider des PhysicElements.
         */
        public PhysicColliderJson[] colliders;

        /**
         * Diese Klasse enthält die Informationen über die Collider eines Spielelements.
         */
        public static class PhysicColliderJson
        {
            /**
             * Speichert die ID des Colliders.
             */
            public int colliderId;

            /**
             * Speichert die Ebene des Colliders.
             */
            public WorldLayer layer;

            /**
             * Speichert die Polygon-Collider des Element-Typs.
             */
            public PolygonJson[] polygonShapes;

            /**
             * Speichert die Kreis-Collider des Element-Typs.
             */
            public CircleJson[] circleShapes;

            /**
             * Die Kräfte, die bei Interaktion mit diesem Element-Typ auftreten.
             */
            public ColliderTypeJson collisionType;

            /**
             * Diese Klasse enthält die spezifischen Informationen über einen Collider mit der Form eines Polygons.
             */
            public static class PolygonJson
            {
                /**
                 * Speichert die Eckpunkte des Polygons.
                 */
                public Vector2[] vertices;
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
                 * Speichert den Namen der Kraft die bei Interaktion mit der Kugel auftreten.
                 */
                public String type;

                /**
                 * Speichert die Kraft, die der ColliderType auf die Kugel ausübt.
                 */
                public double strength;
            }
        }
    }

    /**
     * Diese Klasse enthält die Informationen über Animationen, die Eigenschaften im Editor und Soundeffekte des Spielelements.
     */
    public static class MediaElementJson
    {
        /**
         * Enthält die Eigenschaften des Elements im Editor.
         */
        public MediaElementGeneralJson general;

        /**
         * Enthält die Animation und Soundtracks des Objekts, falls vorhanden.
         */
        public MediaElementEventJson[] events;

        /**
         * Diese Klasse repräsentiert die Eigenschaften des Objekts im Editor.
         */
        public static class MediaElementGeneralJson
        {
            /**
             * Bezeichnung des Element-Typs im Editor.
             */
            public String editorName;

            /**
             * Text, der den Element-Typ im Editor beschreibt.
             */
            public String editorDescription;

            /**
             * Speichert, ob das Element rotiert werden kann.
             */
            public boolean canRotate;

            /**
             * Die Genauigkeit der Rotation beim Setzen und Bearbeiten im Editor, d.h. um wie viel Grad man Elemente von diesem Typ drehen kann.
             */
            public int rotationAccuracy;
        }

        /**
         * Diese Klasse enthält alle Informationen über Animation und Soundeffekt bei einem Zusammenstoß.
         */
        public static class MediaElementEventJson
        {
            /**
             * Speichert die Identifikationsnummer des Colliders.
             */
            public int colliderId;

            /**
             * Speichert den Soundeffekt, der beim Zusammenstoß abgespielt wird.
             */
            public String soundName;

            /**
             * Speichert die Animation, die dieser Element-Typ bei Kollision mit dem Collider abspielt.
             */
            public AnimationJson animation;

            /**
             * Diese Klasse enthält alle nötigen Informationen zur Serialisierung einer Animation.
             */
            public static class AnimationJson
            {
                /**
                 * Speichert, aus wie viele Bilder die Animation besteht.
                 */
                public int frameCount;

                /**
                 * Speichert die Dauer eines Bildes der Animation.
                 */
                public int duration;

                /**
                 * Speichert den Namen der Animation.
                 */
                public String animationName;
            }
        }
    }

    /**
     * Diese Klasse enthält die zur Serialisierung des Regelwerks nötigen Informationen.
     */
    public static class RuleElementJson
    {
        /**
         * Speichert, ob ein Zusammenstoß die Punkte des Spielers erhöht.
         */
        public RuleElementGeneralJson general;

        /**
         * Speichert, zu welchem Collider dieses Regelwerk gehört.
         */
        public RuleElementEventJson[] events;

        /**
         * Enthält die Information, ob ein Zusammenstoß die Punktzahl des Spielers erhöht.
         */
        public static class RuleElementGeneralJson
        {
            /**
             * Speichert, ob ein Treffer durch die Kugel Punkte bringt.
             */
            public boolean givesPoints;
        }

        /**
         * Enthält die Information, zu welchem Collider dieses Regelwerk gehört.
         */
        public static class RuleElementEventJson
        {
            /**
             * Speichert die Identifikationsnummer des Colliders, zu dem dieses Regelwerk gehört.
             */
            public int colliderId;
        }
    }
}
