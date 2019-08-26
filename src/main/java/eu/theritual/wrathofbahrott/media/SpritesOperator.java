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

        double characterAnimationSpeed = 0.11;

        ArrayList<Image> bah = new ArrayList<>();
        bah.add(MediaOperator.getImage("sprites/bahrott/bahrot0", width, height));
        bah.add(MediaOperator.getImage("sprites/bahrott/bahrot1", width, height));
        bah.add(MediaOperator.getImage("sprites/bahrott/bahrot2", width, height));
        bah.add(MediaOperator.getImage("sprites/bahrott/bahrot3", width, height));
        AnimatedSprite bahrot = new AnimatedSprite(bah, 0.2);

        ArrayList<Image> witch_r = new ArrayList<>();
        witch_r.add(MediaOperator.getImage("sprites/witch/witch_rw1", width, height));
        witch_r.add(MediaOperator.getImage("sprites/witch/witch_rs", width, height));
        witch_r.add(MediaOperator.getImage("sprites/witch/witch_rw2", width, height));
        AnimatedSprite anim_witch_r = new AnimatedSprite(witch_r, characterAnimationSpeed);

        ArrayList<Image> witch_l = new ArrayList<>();
        witch_l.add(MediaOperator.getImage("sprites/witch/witch_lw1", width, height));
        witch_l.add(MediaOperator.getImage("sprites/witch/witch_ls", width, height));
        witch_l.add(MediaOperator.getImage("sprites/witch/witch_lw2", width, height));
        AnimatedSprite anim_witch_l = new AnimatedSprite(witch_l, characterAnimationSpeed);

        ArrayList<Image> witch_t = new ArrayList<>();
        witch_t.add(MediaOperator.getImage("sprites/witch/witch_tw1", width, height));
        witch_t.add(MediaOperator.getImage("sprites/witch/witch_ts", width, height));
        witch_t.add(MediaOperator.getImage("sprites/witch/witch_tw2", width, height));
        AnimatedSprite anim_witch_t = new AnimatedSprite(witch_t, characterAnimationSpeed);

        ArrayList<Image> witch_b = new ArrayList<>();
        witch_b.add(MediaOperator.getImage("sprites/witch/witch_bw1", width, height));
        witch_b.add(MediaOperator.getImage("sprites/witch/witch_bs", width, height));
        witch_b.add(MediaOperator.getImage("sprites/witch/witch_bw2", width, height));
        AnimatedSprite anim_witch_b = new AnimatedSprite(witch_b, characterAnimationSpeed);

        spritesList.put(GameSprite.BAHROTT, bahrot);
        spritesList.put(GameSprite.WITCH_R, anim_witch_r);
        spritesList.put(GameSprite.WITCH_L, anim_witch_l);
        spritesList.put(GameSprite.WITCH_T, anim_witch_t);
        spritesList.put(GameSprite.WITCH_B, anim_witch_b);
        return spritesList.get(sprite);
    }
}
