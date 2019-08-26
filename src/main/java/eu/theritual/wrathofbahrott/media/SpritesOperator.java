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
        addSpriteCharacterSet(GameSprite.WITCH_T, GameSprite.WITCH_R, GameSprite.WITCH_B, GameSprite.WITCH_L, witchList);

        List<AnimatedSprite> nunList = createAnimateCharacterSprites("characters/nun", width, height, characterAnimationSpeed);
        addSpriteCharacterSet(GameSprite.NUN_T, GameSprite.NUN_R, GameSprite.NUN_B, GameSprite.NUN_L, nunList);

        List<AnimatedSprite> workerList = createAnimateCharacterSprites("characters/worker", width, height, characterAnimationSpeed);
        addSpriteCharacterSet(GameSprite.WORKER_T, GameSprite.WORKER_R, GameSprite.WORKER_B, GameSprite.WORKER_L, workerList);

        List<AnimatedSprite> courierList = createAnimateCharacterSprites("characters/courier", width, height, characterAnimationSpeed);
        addSpriteCharacterSet(GameSprite.COURIER_T, GameSprite.COURIER_R, GameSprite.COURIER_B, GameSprite.COURIER_L, courierList);
    }

    public AnimatedSprite getSprite(GameSprite sprite) {
        return spritesList.get(sprite);
    }

    private void addSpriteCharacterSet(GameSprite top, GameSprite right, GameSprite bottom, GameSprite left, List<AnimatedSprite> animatedList) {
        spritesList.put(top, animatedList.get(0));
        spritesList.put(right, animatedList.get(1));
        spritesList.put(bottom, animatedList.get(2));
        spritesList.put(left, animatedList.get(3));
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
