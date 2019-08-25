package eu.theritual.wrathofbahrott.media;

import eu.theritual.wrathofbahrott.dataoperator.gameenums.GameSprite;
import eu.theritual.wrathofbahrott.viewoperator.sprites.AnimatedSprite;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class SpritesOperator {
    public static AnimatedSprite getSprite(GameSprite sprite, double width, double height) {
        Map<GameSprite, AnimatedSprite> spritesList = new HashMap<>();
        ArrayList<Image> bah = new ArrayList<>();
        bah.add(MediaOperator.getImage("sprites/bahrott/bahrot0", width, height));
        bah.add(MediaOperator.getImage("sprites/bahrott/bahrot1", width, height));
        bah.add(MediaOperator.getImage("sprites/bahrott/bahrot2", width, height));
        bah.add(MediaOperator.getImage("sprites/bahrott/bahrot3", width, height));
        AnimatedSprite bahrot = new AnimatedSprite(bah, 0.2);
        spritesList.put(GameSprite.BAHROTT, bahrot);
        return spritesList.get(sprite);
    }
}
