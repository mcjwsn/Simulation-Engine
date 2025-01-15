package agh.ics.oop.presenter;
import agh.ics.oop.model.WorldElement;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class WorldElementBox extends VBox {
    private static final int IMAGE_HEIGHT = 20;
    private static final int IMAGE_WIDTH = 20;
    private String lastImage;
    private static final Map<String, Image> imageCache = new HashMap<>();

    // Cache'ujemy Image zamiast ImageView
    public static Image getImage(String imagePath) {
        return imageCache.computeIfAbsent(imagePath, Image::new);
    }

    private void updateImage(WorldElement element) {
        String currImage = element.getImageResource();
        if (!Objects.equals(currImage, lastImage)) {
            Image image = getImage(currImage);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(IMAGE_WIDTH);
            imageView.setFitHeight(IMAGE_HEIGHT);
            this.getChildren().add(imageView);
            lastImage = currImage;
        }
    }

    public WorldElementBox(WorldElement element) {
        updateImage(element);
        this.setAlignment(Pos.CENTER);
    }
}