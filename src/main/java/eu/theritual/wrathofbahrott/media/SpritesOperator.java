package eu.theritual.wrathofbahrott.media;

import eu.theritual.wrathofbahrott.viewoperator.sprites.AnimatedSprite;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public final class SpritesOperator {
    public static AnimatedSprite getSprite(int index, double width, double height) {
        List<AnimatedSprite> spritesList = new ArrayList<>();
        ArrayList<Image> bah = new ArrayList<>();
        bah.add(MediaOperator.getImage("sprites/bahrott/bahrot0", width, height));
        bah.add(MediaOperator.getImage("sprites/bahrott/bahrot1", width, height));
        bah.add(MediaOperator.getImage("sprites/bahrott/bahrot2", width, height));
        bah.add(MediaOperator.getImage("sprites/bahrott/bahrot3", width, height));
        AnimatedSprite bahrot = new AnimatedSprite(bah, 0.2);
        spritesList.add(bahrot);
        return spritesList.get(index);
    }
}
