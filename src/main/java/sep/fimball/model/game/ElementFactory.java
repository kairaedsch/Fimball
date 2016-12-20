package sep.fimball.model.game;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.handler.Handler;
import sep.fimball.model.handler.HandlerManager;
import sep.fimball.model.physics.PhysicsHandler;
import sep.fimball.model.physics.element.FlipperPhysicsElement;
import sep.fimball.model.physics.element.PhysicsElement;
import sep.fimball.model.physics.element.PlungerPhysicsElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by alexcekay on 12/20/16.
 */
public class ElementFactory
{
    public static GeneratedElements generateElements(ReadOnlyListProperty<PlacedElement> placedElements, PhysicsHandler<GameElement> physicsHandler, HandlerManager handlerManager)
    {
        BallGameElement ballGameElement = null;
        List<GameElement> gameElements = new ArrayList<>();
        List<PhysicsElement<GameElement>> physicsElements = new ArrayList<>();

        for (PlacedElement element : placedElements)
        {
            switch (element.getBaseElement().getType())
            {
                case RAMP:
                case NORMAL:
                    GameElement normalGameElement = new GameElement(element, false);
                    PhysicsElement<GameElement> normalPhysicsElement = new PhysicsElement<>(normalGameElement, normalGameElement.positionProperty().get(),
                            normalGameElement.rotationProperty().get(), normalGameElement.getPlacedElement().getBaseElement().getPhysics());
                    gameElements.add(normalGameElement);
                    physicsElements.add(normalPhysicsElement);
                    break;
                case BALL:
                    // PhysicsElement der Kugel wird später hinzugefügt, da nur eine Kugel im Spielfeld existieren darf.
                    ballGameElement = new BallGameElement(element, false);
                    gameElements.add(ballGameElement);
                    break;
                case PLUNGER:
                    PlungerGameElement plungerGameElement = new PlungerGameElement(element, false);
                    PlungerPhysicsElement<GameElement> plungerPhysicsElement = new PlungerPhysicsElement<>(physicsHandler, plungerGameElement,
                            plungerGameElement.positionProperty().get(), plungerGameElement.rotationProperty().get(), plungerGameElement.getPlacedElement().getBaseElement().getPhysics());
                    plungerGameElement.setPhysicsElement(plungerPhysicsElement);
                    gameElements.add(plungerGameElement);
                    physicsElements.add(plungerPhysicsElement);
                    handlerManager.addHandler(new Handler(plungerGameElement));
                    break;
                case LEFT_FLIPPER:
                case RIGHT_FLIPPER:
                    boolean left = element.getBaseElement().getType() == BaseElementType.LEFT_FLIPPER;
                    FlipperGameElement flipperGameElement = new FlipperGameElement(element, false, left);
                    FlipperPhysicsElement<GameElement> leftFlipperPhysicsElement = new FlipperPhysicsElement<>(physicsHandler, flipperGameElement,
                            flipperGameElement.positionProperty().get(), flipperGameElement.getPlacedElement().getBaseElement().getPhysics(), left);
                    flipperGameElement.setPhysicsElement(leftFlipperPhysicsElement);
                    gameElements.add(flipperGameElement);
                    physicsElements.add(leftFlipperPhysicsElement);
                    handlerManager.addHandler(new Handler(flipperGameElement));
                    break;
                case LIGHT:
                    gameElements.add(new GameElement(element, false));
                    break;
                default:
                    throw new IllegalArgumentException("At least one given PlacedElement does not have a correct BaseElementType");
            }
        }
        return new GeneratedElements(gameElements, physicsElements, Optional.ofNullable(ballGameElement));
    }

    public static class GeneratedElements
    {
        private List<GameElement> gameElements;
        private List<PhysicsElement<GameElement>> physicsElements;
        private Optional<BallGameElement> ballGameElement;

        public GeneratedElements(List<GameElement> gameElement, List<PhysicsElement<GameElement>> physicsElement, Optional<BallGameElement> ballGameElement)
        {
            this.gameElements = gameElement;
            this.physicsElements = physicsElement;
            this.ballGameElement = ballGameElement;
        }

        public List<GameElement> getGameElements()
        {
            return gameElements;
        }

        public List<PhysicsElement<GameElement>> getPhysicsElements()
        {
            return physicsElements;
        }

        public Optional<BallGameElement> getBallGameElement()
        {
            return ballGameElement;
        }
    }
}
