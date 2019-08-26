package eu.theritual.wrathofbahrott.dataoperator;

public class GameOptions {
    private boolean fullScreen;
    private double musicVolume;
    private double screenWidth;
    private double screenHeight;


    public GameOptions() {
        this(true, 100);
    }

    private GameOptions(boolean fullScreen, double musicVolume) {
        this.fullScreen = fullScreen;
        this.musicVolume = musicVolume;
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
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

    public void setScreenHeight(double screenHeight) {
        this.screenHeight = screenHeight;
    }
}
