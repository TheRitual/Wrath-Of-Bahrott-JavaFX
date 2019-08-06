package eu.theritual.wrathofbahrott.dataoperator;

public class GameOptions {
    private Keys keys;
    private boolean fullScreen;
    private int musicVolume;
    private int soundVolume;

    public GameOptions() {
        this(new Keys(), true, 100, 100);
    }

    public GameOptions(Keys keys, boolean fullScreen, int musicVolume, int soundVolume) {
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

    public int getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(int musicVolume) {
        this.musicVolume = musicVolume;
    }

    public int getSoundVolume() {
        return soundVolume;
    }

    public void setSoundVolume(int soundVolume) {
        this.soundVolume = soundVolume;
    }
}
