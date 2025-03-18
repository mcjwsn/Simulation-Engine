// Now, let's reimplement the WorldElementBox
package agh.ics.oop.presenter;

import agh.ics.oop.model.mapElements.*;
        import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class WorldElementBox extends VBox implements WorldElementVisitor {
    private final int cellSize;
    private static final Map<String, Image> imageCache = new HashMap<>();
    private static final double MAIN_IMAGE_SCALE = 0.8;
    private static final double ENERGY_BAR_SCALE = 0.3;
    private static final double PREFERRED_FIELD_OPACITY = 0.1;
    private boolean isPreferred = false;

    private static Image getImage(String imagePath) {
        return imageCache.computeIfAbsent(imagePath, Image::new);
    }

    public WorldElementBox(Object element, int size) {
        this.cellSize = size;
        initializeBox();

        if (element instanceof WorldElement worldElement) {
            worldElement.accept(this);
        } else if (element instanceof GenotypeCell genotypeCell) {
            visit(genotypeCell);
        } else {
            throw new IllegalArgumentException("Unsupported element type: " + element.getClass());
        }
    }

    private void initializeBox() {
        this.setMinSize(cellSize, cellSize);
        this.setMaxSize(cellSize, cellSize);
        this.setPrefSize(cellSize, cellSize);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(2);
    }

    public void updateImage(WorldElement element) {
        this.getChildren().clear();
        element.accept(this);
    }

    public void updateImageTrackedDown(Animal animal) {
        this.getChildren().clear();
        animal.acceptAsTracked(this);
    }

    @Override
    public void visit(Animal animal, boolean isTracked) {
        this.getChildren().clear();

        VBox container = new VBox(2);
        container.setAlignment(Pos.CENTER);

        String imageResource = isTracked ? animal.getTrackedDownAnimalImageResource() : animal.getImageResource();
        ImageView mainImage = createImageView(imageResource, cellSize * MAIN_IMAGE_SCALE);
        container.getChildren().add(mainImage);

        ImageView energyBar = createImageView(animal.getEnergyLevelResource(), cellSize * ENERGY_BAR_SCALE);
        energyBar.setPreserveRatio(false);
        energyBar.setFitHeight(cellSize * ENERGY_BAR_SCALE * 0.3);
        container.getChildren().add(energyBar);

        container.setAlignment(Pos.CENTER);
        this.getChildren().add(container);
    }

    @Override
    public void visit(Grass grass) {
        this.getChildren().clear();
        ImageView imageView = createImageView(grass.getImageResource(), cellSize * MAIN_IMAGE_SCALE);
        GridPane.setHalignment(imageView, javafx.geometry.HPos.CENTER);
        GridPane.setValignment(imageView, javafx.geometry.VPos.CENTER);
        this.getChildren().add(imageView);
    }

    @Override
    public void visit(OwlBear owlBear) {
        this.getChildren().clear();
        ImageView imageView = createImageView(owlBear.getImageResource(), cellSize * MAIN_IMAGE_SCALE);
        this.getChildren().add(imageView);
    }

    @Override
    public void visit(EmptyCell emptyCell) {
        this.getChildren().clear();
        ImageView imageView = createImageView(emptyCell.getImageResource(), cellSize * MAIN_IMAGE_SCALE);
        this.getChildren().add(imageView);
    }

    @Override
    public void visit(PrefferdCell prefferdCell) {
        this.getChildren().clear();
        ImageView imageView = createImageView(prefferdCell.getImageResource(), cellSize * MAIN_IMAGE_SCALE);
        imageView.setOpacity(PREFERRED_FIELD_OPACITY);
        this.getChildren().add(imageView);
    }

    @Override
    public void visit(GenotypeCell genotypeCell) {
        this.getChildren().clear();
        ImageView imageView = createImageView(genotypeCell.getImageResource(), cellSize);
        this.getChildren().add(imageView);
    }

    private ImageView createImageView(String resourcePath, double size) {
        Image image = getImage(resourcePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    public void setPreferred(boolean preferred) {
        this.isPreferred = preferred;
        if (preferred) {
            this.setStyle("-fx-border-color: orange; -fx-border-width: 3; -fx-border-radius: 4;");
        } else {
            this.setStyle("");
        }
    }
}