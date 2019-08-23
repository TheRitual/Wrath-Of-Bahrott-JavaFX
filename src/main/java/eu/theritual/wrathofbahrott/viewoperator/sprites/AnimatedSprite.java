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

    public AnimatedSprite(Image firstImage, double duration) {
        frames = new ArrayList<>();
        frames.add(firstImage);
        this.duration = duration;
    }

    public void addImage(Image image) {
        frames.add(image);
    }

    public void removeImage(int index) {
        frames.remove(index);
    }

    public Image getFrame(double time) {
        int index = (int) ((time % (frames.size() * duration)) / duration);
        return frames.get(index);
    }
}
