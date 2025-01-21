package agh.ics.oop.presenter;

import agh.ics.oop.model.mapElements.*;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class WorldElementBox extends VBox {
    private final int cellSize;
    private static final Map<String, Image> imageCache = new HashMap<>();
    private static final double MAIN_IMAGE_SCALE = 0.8;
    private static final double ENERGY_BAR_SCALE = 0.3;
    private static final double PREFERRED_FIELD_OPACITY = 0.1;

    private static Image getImage(String imagePath) {
        return imageCache.computeIfAbsent(imagePath, Image::new);
    }

    public WorldElementBox(Object element, int size) {
        this.cellSize = size;
        initializeBox();

        switch (element) {
            case GenotypeCell genotypeCell -> updateGenotypeCell(genotypeCell);
            case PrefferdCell prefferdCell -> updatePreferredCell(prefferdCell);
            case EmptyCell emptyCell -> updateEmptyCell(emptyCell);
            case Animal animal -> updateAnimal(animal, false);
            case WorldElement worldElement -> updateImage(worldElement);
            case null, default -> throw new IllegalArgumentException("Unsupported element type: " + element.getClass());
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
        switch (element) {
            case Animal animal -> updateAnimal(animal, false);
            case Grass grass -> updateGrass(grass);
            case OwlBear owlBear -> updateOwlBear(owlBear);
            case EmptyCell emptyCell -> updateEmptyCell(emptyCell);
            case PrefferdCell prefferdCell -> updatePreferredCell(prefferdCell);
            default -> throw new IllegalArgumentException("Unsupported element type: " + element.getClass());
        }
    }

    public void updateAnimal(Animal animal, boolean isTracked) {
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

    private void updateOwlBear(OwlBear owlBear) {
        this.getChildren().clear();
        ImageView imageView = createImageView(owlBear.getImageResource(), cellSize * MAIN_IMAGE_SCALE);
        this.getChildren().add(imageView);
    }

    private void updateGrass(Grass grass) {
        this.getChildren().clear();
        ImageView imageView = createImageView(grass.getImageResource(), cellSize * MAIN_IMAGE_SCALE);
        GridPane.setHalignment(imageView, javafx.geometry.HPos.CENTER);
        GridPane.setValignment(imageView, javafx.geometry.VPos.CENTER);
        this.getChildren().add(imageView);
    }

    public void updateImageTrackedDown(Animal animal) {
        updateAnimal(animal, true);
    }

    private void updateEmptyCell(EmptyCell element) {
        this.getChildren().clear();
        ImageView imageView = createImageView(element.getImageResource(), cellSize * MAIN_IMAGE_SCALE);
        this.getChildren().add(imageView);
    }

    private void updatePreferredCell(PrefferdCell element) {
        this.getChildren().clear();
        ImageView imageView = createImageView(element.getImageResource(), cellSize * MAIN_IMAGE_SCALE);
        imageView.setOpacity(PREFERRED_FIELD_OPACITY);
        this.getChildren().add(imageView);
    }

    private void updateGenotypeCell(GenotypeCell element) {
        this.getChildren().clear();
        ImageView imageView = createImageView(element.getImageResource(), cellSize);
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

    private boolean isPreferred = false;

    public void setPreferred(boolean preferred) {
        this.isPreferred = preferred;
        if (preferred) {
            this.setStyle("-fx-border-color: orange; -fx-border-width: 3; -fx-border-radius: 4;");
        } else {
            this.setStyle("");
        }
    }
}