package agh.ics.oop.presenter;
import agh.ics.oop.model.WorldElement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import java.util.Objects;
public class WorldElementBox extends VBox {
    private static final int IMAGE_HEIGHT = 20;
    private static final int  IMAGE_WIDTH = 20;
    private String lastImage;
    private String lastLabel;
    private void updateImage(WorldElement element) {
        String currImage = element.getImageResource();
        if (! Objects.equals(currImage, lastImage)) {
            ImageView imageView = new ImageView(currImage);
            imageView.setFitHeight(IMAGE_HEIGHT);
            imageView.setFitWidth(IMAGE_WIDTH);
            this.getChildren().add(imageView);
            lastImage = currImage;
        }
    }
    public WorldElementBox(WorldElement element) {
        updateImage(element);
        this.setAlignment(Pos.CENTER);
    }
}