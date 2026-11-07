package need.clarity.clickgui;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface GuiStore {
    boolean isEnabled(String arg0);

    void setEnabled(String arg0, boolean arg1);

    Object value(String arg0, String arg1);

    void setValue(String arg0, String arg1, Object arg2);

    void resetModule(String arg0);

    String bind(String arg0);

    String bindMode(String arg0);

    void setBind(String arg0, String arg1, String arg2);

    Object gui(String arg0);

    void setGui(String arg0, Object arg1);

    Map theme();

    String themeName();

    void applyTheme(String arg0, List arg1);

    boolean themeColorsEnabled();

    void setThemeColorsEnabled(boolean arg0);

    List profiles();

    String activeProfile();

    void loadProfile(String arg0);

    void saveProfile(String arg0);

    void deleteProfile(String arg0);

    List bindSets();

    void loadBindSet(String arg0);

    void saveBindSet(String arg0);

    void deleteBindSet(String arg0);

    Map serverProfiles();

    void mapServer(String arg0, String arg1);

    void unmapServer(String arg0);

    String currentServer();

    List customSkins();

    List customCapes();

    String exportProfile(String arg0);

    String importProfile(Path arg0, boolean arg1);

    List configFiles();

    void openConfigFolder(Consumer arg0);

    String configFolderPath();

    void setClipboard(String arg0);

    boolean pickConfigFile(Consumer arg0);

    boolean webMode();

    void switchToWebGui();

    void openWebGui();

    String clipboard();

}
