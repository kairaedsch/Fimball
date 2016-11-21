package sep.fimball.general.data;

import java.awt.*;

/**
 * Vector2 stellt einen Vektor mit zwei Komponenten dar.
 */
public class Vector2
{
    /**
     * Erste Komponente des Vektors.
     */
    private double x;

    /**
     * Zweite Komponente des Vektors.
     */
    private double y;

    /**
     * Konstruiert einen Vektor, bei dem sowohl die erste als auch die zweite Komponente 0 sind.
     */
    public Vector2()
    {
        x = 0.0;
        y = 0.0;
    }

    /**
     * Konstruiert einen Vektor mit den übergebenen Parametern.
     *
     * @param x Erste Komponente des Vektors.
     * @param y Zweite Komponente des Vektors.
     */
    public Vector2(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Erzeugt einen Vektor aus einem gegebenen Punkt.
     *
     * @param p Der Punkt, aus dem der Vektor erzeugt wird.
     */
    public Vector2(Point p)
    {
        this.x = p.x;
        this.y = p.y;
    }

    /**
     * Addiert einen anderen Vektor auf diesen Vektor.
     *
     * @param otherVec Der zu addierende Vektor.
     */
    public Vector2 add(Vector2 otherVec)
    {
        this.x += otherVec.getX();
        this.y += otherVec.getY();
        return this;
    }

    /**
     * Subtrahiert einen anderen Vektor von diesem Vektor.
     *
     * @param otherVec Der zu subtrahierende Vektor.
     */
    public Vector2 sub(Vector2 otherVec)
    {
        this.x -= otherVec.getX();
        this.y -= otherVec.getY();
        return this;
    }

    /**
     * Skaliert einen Vektor mit dem gegebenen Skalar.
     *
     * @param scalar Der Skalar, mit dem der Vektor skaliert wird.
     */
    public Vector2 scale(double scalar)
    {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    /**
     * Gibt die Länge des Vektors in der euklidischen Norm zurück.
     *
     * @return Die L2-Norm des Vektors.
     */
    public double magnitude()
    {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Berechnet das Skalarprodukt dieses Vektors mit einem gegebenen Vektor.
     *
     * @param other Der Vektor, mit dem das Skalarprodukt berechnet werden soll.
     * @return Das Skalarprodukt der beiden Vektoren.
     */
    public double dot(Vector2 other)
    {
        return (this.x * other.getX()) + (this.y * other.getY());
    }

    /**
     * Normiert den Vektor.
     */
    public Vector2 normalize()
    {
        double norm = magnitude();
        this.x /= norm;
        this.y /= norm;
        return this;
    }

    /**
     * Dreht den Vektor gegen den Uhrzeigersinn um den durch {@code pivot} gegebenen Pivotpunkt im angegeben Winkel.
     *
     * @param radianAngle Der Winkel als Radiant.
     * @param pivot Der Vektor zwischen Nullpunkt und dem Pivotpunkt.
     */
    public Vector2 rotate(double radianAngle, Vector2 pivot)
    {
        this.sub(pivot);
        this.rotate(radianAngle);
        this.add(pivot);
        return this;
    }

    /**
     * Dreht den Vektor gegen den Uhrzeigersinn um den gegebene Winkel.
     *
     * @param radianAngle Der Winkel, um den rotiert werden soll, gegeben in Radianten.
     */
    public Vector2 rotate(double radianAngle)
    {
        this.x = (Math.cos(radianAngle) * this.x) - (Math.sin(radianAngle) * this.y);
        this.y = (Math.sin(radianAngle) * this.x) + (Math.cos(radianAngle) * this.y);
        return this;
    }

    /**
     * Gibt den normierten Vektor zurück, ohne die Normierung an diesem Vektor zu übernehmen.
     *
     * @return Der normierte Vektor.
     */
    public Vector2 normalized()
    {
        double norm = magnitude(this);
        return new Vector2(getX() / norm, getY() / norm);
    }

    /**
     * Gibt den Winkel zwischen diesem Vektor und einem anderen zurück.
     *
     * @param otherVec Der andere Vektor.
     * @return Winkel zwischen den beiden Vektoren in Radianten.
     */
    public double angleBetween(Vector2 otherVec)
    {
        return Vector2.angleBetween(this, otherVec);
    }

    /**
     * Dreht einen Vektor um den gegebenen Radianten.
     * @param vec Der Vektor der gedreht werden soll.
     * @param radianAngle Der Radiant um den gedreht wird.
     * @return Ein neuer gedrehter Vektor.
     */
    public static Vector2 rotate(Vector2 vec, double radianAngle)
    {
        double rotatedX = (Math.cos(radianAngle) * vec.getX()) - (Math.sin(radianAngle) * vec.getY());
        double rotatedY = (Math.sin(radianAngle) * vec.getX()) + (Math.cos(radianAngle) * vec.getY());
        return new Vector2(rotatedX, rotatedY);
    }

    /**
     * Dreht einen Vektor um einen Pivotpunkt um den gegebenen Radianten.
     * @param vec Der Vektor der gedreht werden soll.
     * @param radianAngle Der Radiant um den gedreht wird.
     * @param pivot Der Punkt um den gedreht wird.
     * @return Der gegebene Vektor nach der Drehung.
     */
    public static Vector2 rotate(Vector2 vec, double radianAngle, Vector2 pivot)
    {
        Vector2 originPoint = Vector2.sub(vec, pivot);
        Vector2 rotatedVec = Vector2.rotate(originPoint, radianAngle);
        return Vector2.add(rotatedVec, pivot);
    }

    /**
     * Addiert zwei Vektoren und gibt das Ergebnis zurück.
     *
     * @param vecOne Der erste Vektor.
     * @param vecTwo Der zu addierende Vektor.
     * @return Das Ergebnis der Addition.
     */
    public static Vector2 add(Vector2 vecOne, Vector2 vecTwo)
    {
        return new Vector2(vecOne.getX() + vecTwo.getX(), vecOne.getY() + vecTwo.getY());
    }

    /**
     * Subtrahiert zwei Vektoren und gibt das Ergebnis zurück.
     *
     * @param vecOne Der erste Vektor.
     * @param vecTwo Der zu subtrahierende Vektor.
     * @return Das Ergebnis der Subtraktion.
     */
    public static Vector2 sub(Vector2 vecOne, Vector2 vecTwo)
    {
        return new Vector2(vecOne.getX() - vecTwo.getX(), vecOne.getY() - vecTwo.getY());
    }

    /**
     * Skaliert einen gegebenen Vektor mit einem Skalar und gibt das Ergebnis zurück.
     *
     * @param vecOne Der Vektor.
     * @param scalar Der Skalar.
     * @return Das Ergebnis der Skalierung.
     */
    public static Vector2 scale(Vector2 vecOne, double scalar)
    {
        return new Vector2(vecOne.getX() * scalar, vecOne.getY() * scalar);
    }

    /**
     * Projiziert einen Vektor auf einen Anderen.
     *
     * @param source Der Vektor, der projiziert werden soll.
     * @param target Der Vektor, auf den projiziert wird.
     * @return Der projizierte Vektor.
     */
    public static Vector2 project(Vector2 source, Vector2 target)
    {
        Vector2 targetNorm = normalize(target);
        double targetLength = dot(source, targetNorm);
        return scale(target, targetLength);
    }

    /**
     * Gibt die euklidische Norm eines gegebenen Vektors zurück.
     *
     * @param vecOne Der Vektor, dessen L2-Norm bestimmt werden soll.
     * @return Die L2-Norm des Vektors.
     */
    public static double magnitude(Vector2 vecOne)
    {
        return Math.sqrt(vecOne.getX() * vecOne.getX() + vecOne.getY() * vecOne.getY());
    }

    /**
     * Gibt das Skalarprodukt von zwei Vektoren zurück.
     *
     * @param vecOne Der erste Vektor.
     * @param vecTwo Der zweite Vektor.
     * @return Das Skalarprodukt der beiden Vektoren.
     */
    public static double dot(Vector2 vecOne, Vector2 vecTwo)
    {
        return (vecOne.getX() * vecTwo.getX()) + (vecOne.getY() * vecTwo.getY());
    }

    /**
     * Normiert den gegebenen Vektor und gibt das Ergebnis zurück.
     *
     * @param vecOne Der zu normierende Vektor.
     * @return Der normierte Vektor.
     */
    public static Vector2 normalize(Vector2 vecOne)
    {
        double norm = magnitude(vecOne);
        vecOne.setX(vecOne.getX() / norm);
        vecOne.setY(vecOne.getY() / norm);
        return vecOne;
    }

    /**
     * Gibt einen Vektor zurück, der senkrecht auf dem gegebenen Vektor steht.
     *
     * @param vec Der Vektor, zu dem eine Normale gesucht wird.
     * @return Die Normale des gegebenen Vektors.
     */
    public static Vector2 createNormal(Vector2 vec)
    {
        return new Vector2(vec.getY(), -vec.getX());
    }

    /**
     * Mittelt zwei gewichtete Vektoren.
     *
     * @param vecOne Der erste Vektor.
     * @param vecTwo Der zweite Vektor.
     * @param t Die Gewichtung des zweiten Vektors.
     * @return Ein neuer Vektor der zwischen den beiden Gegebenen liegt.
     */
    public static Vector2 lerp(Vector2 vecOne, Vector2 vecTwo, double t)
    {
        double xLerped = (1 - t) * vecOne.getX() + (t * vecTwo.getX());
        double yLerped = (1 - t) * vecOne.getY() + (t * vecTwo.getY());
        return new Vector2(xLerped, yLerped);
    }

    /**
     * Normiert den gegebenen Vektor, wobei ein Neuer erstellt wird und der übergebene Vektor nicht verändert wird.
     *
     * @param input Der zu normierende Vektor.
     * @return Der normierte Vektor.
     */
    public static Vector2 normalized(Vector2 input)
    {
        double norm = magnitude(input);
        return new Vector2(input.getX() / norm, input.getY() / norm);
    }

    /**
     * Gibt den Winkel zwischen zwei Vektoren als Radianten zurück.
     *
     * @param vecOne Der erste Vektor.
     * @param vecTwo Der zweite Vektor.
     * @return Der Winkel zwischen den beiden Vektoren.
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

    /**
     * Gibt einen Vektor als String in der Form {Erste Komponente|Zweite Komponente} zurück.
     *
     * @return Eine textuelle Repräsentation des Vektors.
     */
    @Override
    public String toString()
    {
        return "{" + x + "|" + y + "}";
    }

    public Vector2 round()
    {
        x = Math.round(x);
        y = Math.round(y);
        return this;
    }

    /**
     * Beschränkt die Länge des jeweiligen Vektors.
     *
     * @param max Die maximale Länge.
     * @return Der jeweilige Vektor mit gegebenenfalls beschränkter Länge.
     */
    public Vector2 clamp(double max)
    {
        if(magnitude(this) > max)
        {
            double v = max / magnitude(this);

            x = x * v;
            y = y * v;
        }
        return this;
    }
}
