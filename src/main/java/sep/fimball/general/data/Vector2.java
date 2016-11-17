package sep.fimball.general.data;

import java.awt.*;

/**
 * Vector2 stellt einen Vektor mit zwei Komponenten dar.
 */
public class Vector2
{
    /**
     * X Komponente des Vektors.
     */
    private double x;

    /**
     * Y Komponente des Vektors.
     */
    private double y;

    /**
     * Konstruiert einen Vektor bei dem sowohl die X als auch Y Komponente 0 sind.
     */
    public Vector2()
    {
        x = 0.0;
        y = 0.0;
    }

    /**
     * Konstruiert einen Vektor mit den übergebenen Parametern.
     * @param x x-Komponente des Vektors
     * @param y y-Komponente des Vektors
     */
    public Vector2(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Erzeugt eine Vektor aus einem gegebenen Punkt
     * @param p Der Punkt aus dem der Vektor erzeugt wird
     */
    public Vector2(Point p)
    {
        this.x = p.x;
        this.y = p.y;
    }

    /**
     * Addiert einen anderen Vektor auf diesen Vektor
     * @param otherVec Der zu addierende Vektor
     */
    public void add(Vector2 otherVec)
    {
        this.x += otherVec.getX();
        this.y += otherVec.getY();
    }

    /**
     * Subtrahiert einen anderen Vektor von diesem Vektor
     * @param otherVec Der zu subtrahierende Vektor
     */
    public void sub(Vector2 otherVec)
    {
        this.x -= otherVec.getX();
        this.y -= otherVec.getY();
    }

    /**
     * Skaliert einen Vektor mit dem gegebenen Skalar
     * @param scalar Das Skalar mit dem der Vektor skaliert wird
     */
    public void scale(double scalar)
    {
        this.x *= scalar;
        this.y *= scalar;
    }

    /**
     * @return Die L2-Norm des Vektors
     */
    public double magnitude()
    {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Berechnet das Skalarprodukt dieses Vektors mit einem gegebenen Vektor
     * @param other Der Vektor mit dem das Skalarprodukt berechnet werden soll
     * @return Das Skalarprodukt
     */
    public double dot(Vector2 other)
    {
        return (this.x * other.getX()) + (this.y * other.getY());
    }

    /**
     * Normiert den Vektor so dass er eine Norm von 1 hat
     */
    public void normalize()
    {
        double norm = magnitude();
        this.x /= norm;
        this.y /= norm;
    }

    /**
     * Rotiert den Vektor um den gegebene Winkel
     * @param radianAngle Der Winkel um den rotiert werden soll, gegeben in Radianten
     */
    public void rotate(double radianAngle)
    {
        this.x = (Math.cos(radianAngle) * this.x) - (Math.sin(radianAngle) * this.y);
        this.y = (Math.sin(radianAngle) * this.x) + (Math.cos(radianAngle) * this.y);
    }

    /**
     * Gibt den normierten Vektor zurück ohne die Normierung an diesem Vektor zu übernehmen
     * @return Der normierte Vektor
     */
    public Vector2 normalized()
    {
        double norm = magnitude(this);
        return new Vector2(getX() / norm, getY() / norm);
    }

    /**
     * Gibt den Winkel zwischen diesem Vektor und einem anderen zurück
     * @param otherVec Der andere Vektor
     * @return Winkel zwischen den beiden Vektoren in Radianten
     */
    public double angleBetween(Vector2 otherVec)
    {
        return Vector2.angleBetween(this, otherVec);
    }

    /**
     * Addiert zwei Vektoren und gibt das Ergebnis zurück
     * @param vecOne Der erste Vektor
     * @param vecTwo Der zu addierende Vektor
     * @return Das Ergebnis der Addition
     */
    public static Vector2 add(Vector2 vecOne, Vector2 vecTwo)
    {
        return new Vector2(vecOne.getX() + vecTwo.getX(), vecOne.getY() + vecTwo.getY());
    }

    /**
     * Subtrahiert zwei Vektoren und gibt das Ergebnis zurück
     * @param vecOne Der erste Vektor
     * @param vecTwo Der zu subtrahierende Vektor
     * @return Das Ergebnis der Subtraktion
     */
    public static Vector2 sub(Vector2 vecOne, Vector2 vecTwo)
    {
        return new Vector2(vecOne.getX() - vecTwo.getX(), vecOne.getY() - vecTwo.getY());
    }

    /**
     * Skaliert einen gegebenen Vektor mit einem Skalar und gibt das Ergebnis zurück
     * @param vecOne Der Vektor
     * @param scalar Das Skalar
     * @return Das Ergebnis der Skalierung
     */
    public static Vector2 scale(Vector2 vecOne, double scalar)
    {
        return new Vector2(vecOne.getX() * scalar, vecOne.getY() * scalar);
    }

    /**
     * Projiziert einen Vektor auf einen anderen
     * @param source Der Vektor der projiziert werden soll
     * @param target Der Vektor auf den projiziert wird
     * @return Der projizierte Vektor
     */
    public static Vector2 project(Vector2 source, Vector2 target)
    {
        Vector2 targetNorm = normalize(target);
        double targetLength = dot(source, targetNorm);
        return scale(target, targetLength);
    }

    /**
     * Gibt die L2-Norm eines gegebenen Vektors zurück
     * @param vecOne Der Vektor dessen L2-Norm bestimmt werden soll
     * @return Die L2-Norm des Vektors
     */
    public static double magnitude(Vector2 vecOne)
    {
        return Math.sqrt(vecOne.getX() * vecOne.getX() + vecOne.getY() * vecOne.getY());
    }

    /**
     * Gibt das Skalarprodukt von zwei Vektoren zurück
     * @param vecOne Der erste Vektor
     * @param vecTwo Der zweite Vektor
     * @return Das Skalarprodukt der beiden Vektoren
     */
    public static double dot(Vector2 vecOne, Vector2 vecTwo)
    {
        return (vecOne.getX() * vecTwo.getX()) + (vecOne.getY() * vecTwo.getY());
    }

    /**
     * Normiert den gegebenen Vektor und gibt das Ergebnis zurück.
     * @param vecOne Der zu normierende Vektor
     * @return Der normierte Vektor
     */
    public static Vector2 normalize(Vector2 vecOne)
    {
        double norm = magnitude(vecOne);
        vecOne.setX(vecOne.getX() / norm);
        vecOne.setY(vecOne.getY() / norm);
        return vecOne;
    }

    /**
     * Normiert den gegebenen Vektor wobei ein neuer erstellt wird und der Vektor der Klasse nicht geändert wird
     * @param input Der zu normierende Vektor
     * @return Der normierte Vektor
     */
    public static Vector2 normalized(Vector2 input)
    {
        double norm = magnitude(input);
        return new Vector2(input.getX() / norm, input.getY() / norm);
    }

    /**
     * Gibt den Winkel zwischen zwei Vektoren in Radianten zurück
     * @param vecOne Der erste Vektor
     * @param vecTwo Der zweite Vektor
     * @return Der Winkel zwischen den beiden Vektoren
     */
    public static double angleBetween(Vector2 vecOne, Vector2 vecTwo)
    {
        return Math.acos(Vector2.dot(vecOne.normalized(), vecTwo.normalized()));
    }

    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    @Override
    public String toString()
    {
        return "{" + x + "|" + y + "}";
    }
}
