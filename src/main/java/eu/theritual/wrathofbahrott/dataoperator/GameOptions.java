package eu.theritual.wrathofbahrott.dataoperator;

public class GameOptions {
    private Keys keys;
    private boolean fullScreen;
    private boolean maximized;
    private double musicVolume;
    private double screenWidth;
    private double screenHeight;


    public GameOptions() {
        this(new Keys(), true, 100);
    }

    private GameOptions(Keys keys, boolean fullScreen, double musicVolume) {
        this.keys = keys;
        this.fullScreen = fullScreen;
        this.musicVolume = musicVolume;
    }

    public Keys getKeys() {
        return keys;
    }

    public void setKeys(Keys keys) {
        this.keys = keys;
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    public boolean isMaximized() {
        return maximized;
    }

    public void setMaximized(boolean setMaximized) {
        this.maximized = setMaximized;
    }

    public double getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(double musicVolume) {
        this.musicVolume = musicVolume > 100 ? 100 : musicVolume < 0 ? 0 : musicVolume;
    }

    public double getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(double screenWidth) {
        this.screenWidth = screenWidth;
    }

    public double getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(double screenHeight) {
        this.screenHeight = screenHeight;
    }
}
