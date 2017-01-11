package sep.fimball.general.data;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.scene.paint.Color;

/**
 * Die Config für das Design.
 */
public class DesignConfig
{
    /**
     * Die maximale zusätzliche Größe des Balls wenn er die maximale Höhe hat.
     */
    public final static double BALL_SIZE_SCALE_WHEN_LIFTED = 0.25;

    /**
     * Die Standardbreite des Anwendungsfensters.
     */
    public final static int DEFAULT_STAGE_WIDTH = 1280;

    /**
     * Die Standardhöhe des Anwendungsfensters.
     */
    public final static int DEFAULT_STAGE_HEIGHT = 720;

    /**
     * Die Transparenz der Zwischenebene.
     */
    public final static double STAGE_DIVIDER_LAYER_OPACITY = 0.6;

    /**
     * Der Parameter des Blur Effekts welcher auf die Zwischenebene angewandt wird.
     */
    public final static int STAGE_DIVIDER_LAYER_BLUR = 13;

    /**
     * Die Hintergrundfarbe des Flipperautomaten.
     */
    public final static Color PRIMARY_COLOR = new Color(219 / 255.0, 93 / 255.0, 93 / 255.0, 1);

    /**
     * Die hellere Hintergrundfarbe des Flipperautomaten.
     */
    public final static Color PRIMARY_COLOR_LIGHT = new Color(255 / 255.0, 147 / 255.0, 147 / 255.0, 1);

    /**
     * Die hellere Hintergrundfarbe des Flipperautomaten.
     */
    public final static Color PRIMARY_COLOR_LIGHT_LIGHT = new Color(255 / 255.0, 187 / 255.0, 187 / 255.0, 1);

    /**
     * Die Komplementfarbe des Flipperautomaten.
     */
    public final static Color COMPLEMENT_COLOR = new Color(219 / 255.0, 170 / 255.0, 93 / 255.0, 1);

    /**
     * Die dunklere Komplementfarbe des Flipperautomaten.
     */
    public final static Color COMPLEMENT_COLOR_DARK = new Color(165 / 255.0, 119 / 255.0, 47 / 255.0, 1);

    /**
     * Die Sekundärfarbe.
     */
    public final static Color SECONDARY_COLOR = new Color(68 / 255.0, 102 / 255.0, 143 / 255.0, 1);

    /**
     * Die dunklere Sekundärfarbe.
     */
    public final static Color SECONDARY_COLOR_DARK = new Color(37 / 255.0, 69 / 255.0, 108 / 255.0, 1);

    /**
     * Die Zeitspanne, in der der LightChanger aktiv ist.
     */
    public static final int LIGHT_CHANGE_DEFAULT_DURATION = 10000;

    /**
     * Die Rate, mit der sich die Light-Change-Loop wiederholt.
     */
    public static final int LIGHT_CHANGE_TICK_RATE = 25;

    /**
     * Die Blinkrate des Rechtecks der aktuellen Auswahl im Editor.
     */
    public final static double BORDER_BLINK_RATE = 1000.0;

    /**
     * Breite des Rahmens eines Automaten während eines Spiels.
     */
    public final static double AUTOMATE_BORDER_WIDTH = 2;

    /**
     * Gibt an, aus wie vielen Pixeln eine Grid-Einheit besteht.
     */
    public final static int PIXELS_PER_GRID_UNIT = 15;

    /**
     * Sehr kleiner Wert welcher beim Zeichnen von Bildern genutzt wird um Artefakte zu vermeiden.
     */
    public final static double ANTI_GRAPHIC_STRIPES_EXTRA_SIZE = 0.02;

    /**
     * Der konstante String der CSS anweist das Hintergrundbild auf nichts zu setzen.
     */
    public final static String CSS_NO_IMAGE = "-fx-background-image: none";

    /**
     * Der Präfix für CSS um das Hintergrundbild auf eine Datei zu setzen.
     */
    private final static String CSS_URL_PREFIX = "-fx-background-image: url(\"";

    /**
     * Der Postfix für CSS um eine Pfadangabe abzuschließen.
     */
    private final static String CSS_URL_POSTFIX = "\");";

    /**
     * Weist CSS an, dass das Bild genau in den übergeordneten Container passen soll. Es wird zentriert und soll sich nicht wiederholen.
     */
    private final static String CSS_IMAGE_CONTAIN = "-fx-background-size: contain; -fx-background-repeat: no-repeat; -fx-background-position: center;";

    /**
     * Gibt den URL-Pfad, der durch {@code path} spezifizierten CSS-Datei zurück.
     *
     * @param path Der Pfad, der die CSS-Datei spezifiziert.
     * @return Der URL-Pfad.
     */
    public static StringExpression backgroundImageCss(ReadOnlyStringProperty path)
    {
        return Bindings.concat(CSS_URL_PREFIX, Bindings.createStringBinding(() -> DataPath.escapePathToUrl(path.get()), path), CSS_URL_POSTFIX);
    }

    /**
     * Bietet gleiche Funktionalität wie {@see fillBackgroundImageCss} gibt es allerdings als StringExpression zurück um das Ganze in Bindings zu nutzen.
     *
     * @param path Der Pfad zur Bilddatei.
     * @return Der CSS Befehl welcher das Hintergrundbild setzt und für Bindings genutzt werden kann.
     */
    public static StringExpression fillBackgroundImageCss(ReadOnlyStringProperty path)
    {
        return Bindings.concat(DesignConfig.backgroundImageCss(path), CSS_IMAGE_CONTAIN);
    }

    /**
     * Erzeugt den CSS Befehl um ein Hintergrundbild welches in einer Datei gespeichert ist zentriert und ohne Wiederholung so zu setzen dass es in den übergeordneten Container passt.
     *
     * @param path Der Pfad zur Bilddatei.
     * @return Der CSS Befehl welcher das Hintergrundbild setzt.
     */
    public static String fillBackgroundImageCss(String path)
    {
        return DesignConfig.backgroundImageCss(path) + CSS_IMAGE_CONTAIN;
    }

    /**
     * Erzeugt den CSS Befehl um ein Hintergrundbild welches in einer Datei gespeichert ist zu setzen.
     *
     * @param path Der Pfad zur Bilddatei.
     * @return Der CSS Befehl welcher das Hintergrundbild setzt.
     */
    private static String backgroundImageCss(String path)
    {
        return CSS_URL_PREFIX + DataPath.escapePathToUrl(path) + CSS_URL_POSTFIX;
    }
}
