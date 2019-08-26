package eu.theritual.wrathofbahrott.viewoperator.controllers.GameObjects;

import eu.theritual.wrathofbahrott.dataoperator.gameenums.CharacterClass;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;


public class ClassSelectorImage {
    private ImageView image;
    private CharacterClass characterClass;
    private Glow glow;
    private GaussianBlur blur;

    public ClassSelectorImage(ImageView img, CharacterClass characterClass) {
        image = img;
        glow = new Glow();
        glow.setLevel(0.8);
        blur = new GaussianBlur();
        blur.setRadius(7);
        img.setEffect(blur);
        this.characterClass = characterClass;
    }

    public ImageView getImage() {
        return image;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public void selected(boolean isSelected) {
        if (isSelected) {
            image.setEffect(glow);
        } else {
            image.setEffect(blur);
        }
    }
}
