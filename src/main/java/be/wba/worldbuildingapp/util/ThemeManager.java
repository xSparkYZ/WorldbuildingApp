package be.wba.worldbuildingapp.util;

import javafx.scene.Scene;

public class ThemeManager {
    private static boolean darkMode = false;

    private static final String LIGHT_THEME = "/css/light-theme.css";
    private static final String DARK_THEME = "/css/dark-theme.css";

    public static void applyTheme(Scene scene) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add(darkMode ? DARK_THEME : LIGHT_THEME);
    }

    public static void toggleDarkMode(Scene scene, boolean enableDark) {
        darkMode = enableDark;
        applyTheme(scene);
    }

    public static boolean isDarkMode() {
        return darkMode;
    }
}
