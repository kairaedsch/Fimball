package sep.fimball.general.data;

/**
 * Wird für die Aktion die beim Klicken des OK Buttons des QuestionViewModel ausgeführt werden soll verwendet.
 */
@FunctionalInterface
public interface Action
{
    /**
     * Die Funktion die beim Klicken des OK Buttons ausgeführt werden soll.
     */
    void perform();
}
