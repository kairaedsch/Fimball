package sep.fimball.view;

/**
 * Created by kaira on 02.11.2016.
 */
public enum FxControllerType
{
    MAIN_MENU_WINDOW("mainmenu/mainMenuWindow.fxml"), MAIN_MENU_PREVIEW("mainmenu/mainMenuPreview.fxml");

    private String fxmlPath;

    FxControllerType(String fxmlPath)
    {
        this.fxmlPath = fxmlPath;
    }

    public String getFxmlPath()
    {
        return fxmlPath;
    }
}
