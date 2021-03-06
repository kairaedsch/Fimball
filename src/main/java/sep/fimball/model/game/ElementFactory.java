package sep.fimball.model.game;

import javafx.beans.property.ReadOnlyListProperty;
import sep.fimball.general.data.RectangleDouble;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.handler.Handler;
import sep.fimball.model.handler.HandlerManager;
import sep.fimball.model.physics.PhysicsHandler;
import sep.fimball.model.physics.element.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Die ElementFactory kümmert sich um die Erstellung von GameElements und deren PhysicElements.
 */
public class ElementFactory
{
    /**
     * Erstellt GameElements und deren PhysicElements aus einer Liste von PlacedElements.
     *
     * @param session        Die GameSession, welche die Elemente haben will.
     * @param placedElements Die Elemente aus deren GameElemente und PhysicsElemente erstellt werden.
     * @param physicsHandler Der PhysicsHandler für die neu erstellten PhysicsElementen.
     * @param handlerManager Der handlerManager der GameSession von den neu erstellten GameElementen.
     * @return Ein GeneratedElements, welches alle neu erstellten Game- und PhysicsElemente enthält.
     */
    public static GeneratedElements generateElements(GameSession session, ReadOnlyListProperty<PlacedElement> placedElements, PhysicsHandler<GameElement> physicsHandler, HandlerManager handlerManager)
    {
        BallGameElement ballGameElement;
        List<GameElement> gameElements = new ArrayList<>();
        List<PhysicsElement<GameElement>> physicsElements = new ArrayList<>();

        Optional<PlacedElement> ball = placedElements.stream().filter((placedElement -> placedElement.getBaseElement().getType() == BaseElementType.BALL)).findFirst();

        if (ball.isPresent())
        {
            // PhysicsElement der Kugel wird später hinzugefügt, da nur eine Kugel im Spielfeld existieren darf.
            ballGameElement = new BallGameElement(ball.get(), false);
            gameElements.add(ballGameElement);
        }
        else
        {
            throw new IllegalArgumentException("No ball found in PlacedElements!");
        }

        for (PlacedElement element : placedElements)
        {
            switch (element.getBaseElement().getType())
            {
                case BALL:
                    break;
                case RAMP:
                case NORMAL:
                    GameElement normalGameElement = new GameElement(element, false);
                    PhysicsElement<GameElement> normalPhysicsElement = new PhysicsElement<>(normalGameElement, normalGameElement.positionProperty().get(), normalGameElement.rotationProperty().get(), element.multiplierProperty().get(), normalGameElement.getPlacedElement().getBaseElement().getPhysics());
                    gameElements.add(normalGameElement);
                    physicsElements.add(normalPhysicsElement);
                    break;
                case PLUNGER:
                    PlungerGameElement plungerGameElement = new PlungerGameElement(element, false);
                    PlungerPhysicsElement<GameElement> plungerPhysicsElement = new PlungerPhysicsElement<>(physicsHandler, plungerGameElement, plungerGameElement.positionProperty().get(), plungerGameElement.rotationProperty().get(), element.multiplierProperty().get(), plungerGameElement.getPlacedElement().getBaseElement().getPhysics());
                    plungerGameElement.setPhysicsElement(plungerPhysicsElement);
                    gameElements.add(plungerGameElement);
                    physicsElements.add(plungerPhysicsElement);
                    handlerManager.addHandler(new Handler(plungerGameElement));
                    break;
                case LEFT_FLIPPER:
                case RIGHT_FLIPPER:
                    boolean left = element.getBaseElement().getType() == BaseElementType.LEFT_FLIPPER;
                    FlipperGameElement flipperGameElement = new FlipperGameElement(element, false, left);
                    FlipperPhysicsElement<GameElement> leftFlipperPhysicsElement = new FlipperPhysicsElement<>(physicsHandler, flipperGameElement, flipperGameElement.positionProperty().get(), element.multiplierProperty().get(), flipperGameElement.getPlacedElement().getBaseElement().getPhysics(), left);
                    flipperGameElement.setPhysicsElement(leftFlipperPhysicsElement);
                    gameElements.add(flipperGameElement);
                    physicsElements.add(leftFlipperPhysicsElement);
                    handlerManager.addHandler(new Handler(flipperGameElement));
                    break;
                case LIGHT:
                    gameElements.add(new GameElement(element, false));
                    break;
                case HOLE:
                    GameElement gameElement = new GameElement(element, false);
                    HolePhysicsElement<GameElement> physicsElement = new HolePhysicsElement<>(gameElement, gameElement.positionProperty().get(), gameElement.rotationProperty().get(), element.getBaseElement().getPhysics());
                    gameElements.add(gameElement);
                    physicsElements.add(physicsElement);
                    break;
                case SPINNER:
                    SpinnerGameElement spinnerGameElement = new SpinnerGameElement(element, false, ballGameElement, session);
                    PhysicsElement<GameElement> spinnerPhysicsElement = new PhysicsElement<>(spinnerGameElement, spinnerGameElement.positionProperty().get(), spinnerGameElement.rotationProperty().get(), element.multiplierProperty().get(), spinnerGameElement.getPlacedElement().getBaseElement().getPhysics());
                    gameElements.add(spinnerGameElement);
                    physicsElements.add(spinnerPhysicsElement);
                    handlerManager.addHandler(new Handler(spinnerGameElement));
                    break;
                default:
                    throw new IllegalArgumentException("At least one given PlacedElement does not have a correct BaseElementType");
            }
        }

        // Generiere Rahmen um den Automaten,
        RectangleDouble boundingBox = session.getPinballMachine().getBoundingBox();
        for (double p = boundingBox.getOrigin().getX(); p <= boundingBox.getWidth() + boundingBox.getOrigin().getX(); p += 4)
        {
            physicsElements.add(generatePhysicsBarrier(new Vector2(p, boundingBox.getOrigin().getY() + 2), 0));
        }
        for (double p = boundingBox.getOrigin().getY(); p <= boundingBox.getHeight() + boundingBox.getOrigin().getY(); p += 4)
        {
            physicsElements.add(generatePhysicsBarrier(new Vector2(boundingBox.getOrigin().getX() - 1, p), 90));
        }
        for (double p = boundingBox.getOrigin().getY(); p <= boundingBox.getHeight() + boundingBox.getOrigin().getY(); p += 4)
        {
            physicsElements.add(generatePhysicsBarrier(new Vector2(boundingBox.getOrigin().getX() + boundingBox.getWidth(), p), 90));
        }

        return new GeneratedElements(gameElements, physicsElements, ballGameElement);
    }

    /**
     * Erzeugt ein PhysicsElement mit den gegebenen Eigenschaften als ein hinderniss_linie_4.
     *
     * @param position Die Position des PhysicsElements.
     * @param rotation Die Rotation des PhysicsElements.
     * @return Das PhysicsElement.
     */
    private static PhysicsElement<GameElement> generatePhysicsBarrier(Vector2 position, double rotation)
    {
        PlacedElement placedElement = new PlacedElement(BaseElementManager.getInstance().getElement("hinderniss_linie_4"), position, 0, 0, rotation);
        GameElement gameElement = new GameElement(placedElement, false);
        return new PhysicsElement<>(gameElement, gameElement.positionProperty().get(), gameElement.rotationProperty().get(), placedElement.multiplierProperty().get(), gameElement.getPlacedElement().getBaseElement().getPhysics());
    }

    /**
     * Speichert GameElemente und PhysicsElemente sowie ein Optionales BallGameElement.
     */
    public static class GeneratedElements
    {
        /**
         * Die Liste der GameElemente.
         */
        private List<GameElement> gameElements;

        /**
         * Die Liste der PhysicsElemente.
         */
        private List<PhysicsElement<GameElement>> physicsElements;

        /**
         * Das Optionale BallGameElement.
         */
        private BallGameElement ballGameElement;

        /**
         * Erstellt ein neues GeneratedElements.
         *
         * @param gameElement     Die Liste der GameElemente.
         * @param physicsElement  Die Liste der PhysicsElemente.
         * @param ballGameElement Die Kugel.
         */
        public GeneratedElements(List<GameElement> gameElement, List<PhysicsElement<GameElement>> physicsElement, BallGameElement ballGameElement)
        {
            this.gameElements = gameElement;
            this.physicsElements = physicsElement;
            this.ballGameElement = ballGameElement;
        }

        /**
         * Gibt die Liste der GameElemente zurück.
         *
         * @return Die Liste der GameElemente.
         */
        public List<GameElement> getGameElements()
        {
            return gameElements;
        }

        /**
         * Gibt die Liste der PhysicsElemente zurück.
         *
         * @return Die Liste der PhysicsElemente.
         */
        public List<PhysicsElement<GameElement>> getPhysicsElements()
        {
            return physicsElements;
        }

        /**
         * Gibt das Optionale BallGameElement zurück.
         *
         * @return Das Optionale BallGameElement.
         */
        public BallGameElement getBallGameElement()
        {
            return ballGameElement;
        }
    }
}
