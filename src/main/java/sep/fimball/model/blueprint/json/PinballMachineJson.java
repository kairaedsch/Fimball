package sep.fimball.model.blueprint.json;

import sep.fimball.general.data.Highscore;

/**
 * Created by marc on 16.11.16.
 */
public class PinballMachineJson {
    public int tableId;
    public String name;
    public HighscoreJson[] highscores;

    public static class HighscoreJson {
        public long score;
        public String playerName;
    }
}
