package sep.fimball.model.blueprint.pinballmachine;

import sep.fimball.general.data.Highscore;

import java.util.ArrayList;
import java.util.Optional;

import static sep.fimball.model.blueprint.json.JsonUtil.nullCheck;

class PinballMachineFactory
{
    static Optional<PinballMachine> createPinballMachine(Optional<PinballMachineJson> pinballMachineJsonOptional, String pinballMachineId, PinballMachineManager pinballMachineManager)
    {
        try
        {
            if(!pinballMachineJsonOptional.isPresent()) throw new IllegalArgumentException();
            PinballMachineJson pinballMachineJson = pinballMachineJsonOptional.get();

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

    static PinballMachineJson createPlacedElementListJson(PinballMachine pinballMachine)
    {
        PinballMachineJson pinballMachineJson = new PinballMachineJson();
        pinballMachineJson.name = pinballMachine.nameProperty().getValue();

        pinballMachineJson.highscores = new PinballMachineJson.HighscoreJson[pinballMachine.highscoreListProperty().size()];
        int counter = 0;
        for (Highscore highscore : pinballMachine.highscoreListProperty())
        {
            PinballMachineJson.HighscoreJson highscoreJson = new PinballMachineJson.HighscoreJson();
            highscoreJson.score = highscore.scoreProperty().getValue();
            highscoreJson.playerName = highscore.playerNameProperty().getValue();
            pinballMachineJson.highscores[counter] = highscoreJson;
            counter++;
        }

        return pinballMachineJson;
    }
}
