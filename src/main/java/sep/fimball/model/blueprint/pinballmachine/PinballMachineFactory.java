package sep.fimball.model.blueprint.pinballmachine;

import sep.fimball.general.data.Highscore;

import java.util.ArrayList;
import java.util.Optional;

import static sep.fimball.model.blueprint.json.JsonUtil.nullCheck;

/**
 * Die PinballMachineFactory kümmert sich um die Umwandlung zwischen PinballMachine und PinballMachineJson.
 */
class PinballMachineFactory
{
    /**
     * Wandelt eine PinballMachineJson in eine PinballMachine um.
     *
     * @param pinballMachineJsonOptional Die PinballMachineJson aus der die PinballMachine erstellt werden soll.
     * @param pinballMachineId           Die Id der zu erzeugenden PinballMachine.
     * @param pinballMachineManager      Der zun der erzeugenden PinballMachine gehörige PinballMachineManager.
     * @return Eine PinballMachine aus der {@code pinballMachineJsonOptional}.
     */
    static Optional<PinballMachine> createPinballMachine(Optional<PinballMachineJson> pinballMachineJsonOptional, String pinballMachineId, PinballMachineManager pinballMachineManager)
    {
        try
        {
            if (!pinballMachineJsonOptional.isPresent()) throw new IllegalArgumentException();
            PinballMachineJson pinballMachineJson = pinballMachineJsonOptional.get();

            // Lade alle Highscores
            nullCheck(pinballMachineJson.highscores);
            ArrayList<Highscore> highscores = new ArrayList<>();
            for (PinballMachineJson.HighscoreJson highscoreJson : pinballMachineJson.highscores)
            {
                nullCheck(highscoreJson);
                highscores.add(new Highscore(highscoreJson.score, highscoreJson.playerName));
            }

            PinballMachine pinballMachine = new PinballMachine(pinballMachineJson.name, pinballMachineId, highscores, pinballMachineManager);

            System.out.println("Machine      \"" + pinballMachineId + "\" loaded");
            return Optional.of(pinballMachine);
        }
        catch (IllegalArgumentException e)
        {
            System.err.println("Machine      \"" + pinballMachineId + "\" not loaded");
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * Wandelt eine PinballMachine in eine PinballMachineJson um.
     *
     * @param pinballMachine Die PinballMachine aus der die PinballMachineJson erstellt werden soll.
     * @return Eine PinballMachineJson aus der {@code pinballMachine}.
     */
    static PinballMachineJson createPinballMachineJson(PinballMachine pinballMachine)
    {
        PinballMachineJson pinballMachineJson = new PinballMachineJson();
        pinballMachineJson.name = pinballMachine.nameProperty().getValue();

        // Wandelt alle Highscores um
        pinballMachineJson.highscores = new PinballMachineJson.HighscoreJson[pinballMachine.highscoreListProperty().size()];
        int pos = 0;
        for (Highscore highscore : pinballMachine.highscoreListProperty())
        {
            PinballMachineJson.HighscoreJson highscoreJson = new PinballMachineJson.HighscoreJson();
            highscoreJson.score = highscore.scoreProperty().getValue();
            highscoreJson.playerName = highscore.playerNameProperty().getValue();
            pinballMachineJson.highscores[pos] = highscoreJson;
            pos++;
        }

        return pinballMachineJson;
    }
}
