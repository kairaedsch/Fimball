package sep.fimball.model.blueprint;

import sep.fimball.general.data.Vector2;

/**
 * Diese Klasse stellt einen Elementtyp im Allgemeinen dar, der im Flipperautomat genutzt werden kann, um dieses serialisiert abzuspeichern (z.B. Bumper).
 */
public class ElementType
{
    /**
     * Bezeichnung des Elementtyps im Editor.
     */
    public String editorName;

    /**
     * Text, der den Elementtyp im Editor beschreibt.
     */
    public String editorDescription;

    /**
     * Die Genaugigkeit der Rotation beim Setzen und Bearbeiten im Editor, d.h. um wie viel Grad man Elemente von diesem Typ drehen kann.
     */
    public double rotationAccuracy;

    /**
     * TODO
     */
    // TODO make enum
    public String elementType;

    /**
     * Die Collider, die dieser Elementtyp hat.
     */
    public Collider[] colliders;

    public static class Collider
    {
        /**
         * Speichert die Polygon-Collider des Elementtyps.
         */
        public Polygon[] polygons;

        /**
         * Speichert die Kreis-Collider des Elementtyps.
         */
        public Circle[] circles;

        /**
         * Die Animationen, die dieser Elementtyp bei Kollision mit dem Collider abspielt.
         */
        public AnimationObject animations;

        /**
         * Die Kräfte, die bei Interaktion mit diesem Elementtyp auftreten.
         */
        public ForceObject forces;

        /**
         * Speichert die Punkte, die ein Treffer durch die Kugel bringt.
         */
        public boolean givesPoints;

        /**
         * Die Kräfte, die bei Interaktion mit diesem Elementtyp auftreten.
         */
        public String soundName;

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

        public static class AnimationObject
        {
            public int frameCount;
            public double duration;
            public String animationName;
        }

        public static class ForceObject
        {
            public String forceType;
            public double strength;
        }
    }
}
