package sep.fimball.model.blueprint.json;

import sep.fimball.general.data.Highscore;

/**
 * Created by marc on 16.11.16.
 */
public class PinballMachineJson {
    int tableId;
    String name;
    HighscoreJson[] highscores;

    public static class HighscoreJson {
        long score;
        String playerName;
    }
}
