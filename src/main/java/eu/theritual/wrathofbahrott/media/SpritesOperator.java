package eu.theritual.wrathofbahrott.media;

import eu.theritual.wrathofbahrott.dataoperator.gameenums.GameSprite;
import eu.theritual.wrathofbahrott.viewoperator.gameboard.GameBoardMap;
import eu.theritual.wrathofbahrott.viewoperator.sprites.AnimatedSprite;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SpritesOperator {
    private Map<GameSprite, AnimatedSprite> spritesList;
    private GameBoardMap gbm;

    public SpritesOperator(double characterAnimationSpeed, GameBoardMap gbm) {
        spritesList = new HashMap<>();
        this.gbm = gbm;
        double width = (gbm.getTileWidth() - 1) * 16;
        double height = (gbm.getTileHeight() - 1) * 16;

        ArrayList<Image> bah = new ArrayList<>();
        bah.add(MediaOperator.getImage("sprites/bahrott/bahrot0", gbm.getBahrottSize(), gbm.getBahrottSize()));
        bah.add(MediaOperator.getImage("sprites/bahrott/bahrot1", gbm.getBahrottSize(), gbm.getBahrottSize()));
        bah.add(MediaOperator.getImage("sprites/bahrott/bahrot2", gbm.getBahrottSize(), gbm.getBahrottSize()));
        bah.add(MediaOperator.getImage("sprites/bahrott/bahrot3", gbm.getBahrottSize(), gbm.getBahrottSize()));
        AnimatedSprite bahrot = new AnimatedSprite(bah, 0.2);

        spritesList.put(GameSprite.BAHROTT, bahrot);

        List<AnimatedSprite> witchList = createAnimateCharacterSprites("characters/witch", width, height, characterAnimationSpeed);
        spritesList.put(GameSprite.WITCH_T, witchList.get(0));
        spritesList.put(GameSprite.WITCH_R, witchList.get(1));
        spritesList.put(GameSprite.WITCH_B, witchList.get(2));
        spritesList.put(GameSprite.WITCH_L, witchList.get(3));

        List<AnimatedSprite> nunList = createAnimateCharacterSprites("characters/nun", width, height, characterAnimationSpeed);
        spritesList.put(GameSprite.NUN_T, nunList.get(0));
        spritesList.put(GameSprite.NUN_R, nunList.get(1));
        spritesList.put(GameSprite.NUN_B, nunList.get(2));
        spritesList.put(GameSprite.NUN_L, nunList.get(3));
    }

    public AnimatedSprite getSprite(GameSprite sprite) {
        return spritesList.get(sprite);
    }

    private List<AnimatedSprite> createAnimateCharacterSprites(String prefixPath, double width, double height, double characterAnimationSpeed) {
        ArrayList<AnimatedSprite> spriteList = new ArrayList<>();

        char[] direction = new char[]{'t', 'r', 'b', 'l'}; //top, right, bottom, left

        for (char d : direction) {
            ArrayList<Image> sprite_direction = new ArrayList<>();
            sprite_direction.add(MediaOperator.getImage("sprites/" + prefixPath + "_" + d + "w1", width, height));
            sprite_direction.add(MediaOperator.getImage("sprites/" + prefixPath + "_" + d + "s", width, height));
            sprite_direction.add(MediaOperator.getImage("sprites/" + prefixPath + "_" + d + "w2", width, height));
            sprite_direction.add(MediaOperator.getImage("sprites/" + prefixPath + "_" + d + "s", width, height));
            spriteList.add(new AnimatedSprite(sprite_direction, characterAnimationSpeed));
        }

        return spriteList;
    }

}
