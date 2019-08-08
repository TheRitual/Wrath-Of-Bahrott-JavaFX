package eu.theritual.wrathofbahrott.dataoperator;

public class GameOptions {
    private Keys keys;
    private boolean fullScreen;
    private double musicVolume;
    private double soundVolume;

    GameOptions() {
        this(new Keys(), true, 100, 100);
    }

    private GameOptions(Keys keys, boolean fullScreen, double musicVolume, double soundVolume) {
        this.keys = keys;
        this.fullScreen = fullScreen;
        this.musicVolume = musicVolume;
        this.soundVolume = soundVolume;
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

    public double getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(double musicVolume) {
        this.musicVolume = musicVolume > 100 ? 100 : musicVolume < 0 ? 0 : musicVolume;
    }

    public double getSoundVolume() {
        return soundVolume;
    }

    public void setSoundVolume(double soundVolume) {
        this.soundVolume = soundVolume;
    }
}
