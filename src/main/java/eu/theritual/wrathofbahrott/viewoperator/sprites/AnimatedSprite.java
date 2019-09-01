package eu.theritual.wrathofbahrott.viewoperator.sprites;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class AnimatedSprite {
    private List<Image> frames;
    private double duration;

    public AnimatedSprite(ArrayList<Image> imageSet, double duration) {
        frames = imageSet;
        this.duration = duration;
    }

    Image getFrame(double time) {
        int index = (int) ((time % (frames.size() * duration)) / duration);
        return frames.get(index);
    }
}
