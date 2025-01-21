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

    public WorldElementBox(WorldElement element, int size) {
        this.cellSize = size;
        initializeBox();
        updateImage(element);
    }

    public WorldElementBox(PrefferdCell element, int size) {
        this.cellSize = size;
        initializeBox();
        updatePreferredCell(element);
    }

    public WorldElementBox(EmptyCell element, int size) {
        this.cellSize = size;
        initializeBox();
        updateEmptyCell(element);
    }

    public WorldElementBox(Animal animal, int size) {
        this.cellSize = size;
        initializeBox();
        updateAnimal(animal, false);
    }

    public WorldElementBox(GenotypeCell cell, int size) {
        this.cellSize = size;
        initializeBox();
        updateGenotypeCell(cell);
    }

    // Inicjalizacja podstawowych właściwości boxa
    private void initializeBox() {
        this.setMinSize(cellSize, cellSize);
        this.setMaxSize(cellSize, cellSize);
        this.setPrefSize(cellSize, cellSize);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(2);
    }

    // Metody aktualizacji obrazów
    public void updateImage(WorldElement element) {
        this.getChildren().clear();
        if (element instanceof Animal) {
            updateAnimal((Animal) element, false);
        } else if (element instanceof Grass) {
            updateGrass((Grass) element);
        } else if (element instanceof OwlBear) {
            updateOwlBear((OwlBear) element);
        } else if (element instanceof EmptyCell) {
            updateEmptyCell((EmptyCell) element);
        } else if (element instanceof PrefferdCell) {
            updatePreferredCell((PrefferdCell) element);
        }
    }

    public void updateAnimal(Animal animal, boolean isTracked) {
        this.getChildren().clear();

        // Kontener dla obrazu zwierzęcia i paska energii
        VBox container = new VBox(2);
        container.setAlignment(Pos.CENTER);

        // Główny obraz zwierzęcia
        String imageResource = isTracked ? animal.getTrackedDownAnimalImageResource() : animal.getImageResource();
        ImageView mainImage = createImageView(imageResource, cellSize * MAIN_IMAGE_SCALE);
        container.getChildren().add(mainImage);

        // Dodaj pasek energii tylko jeśli to nie jest OwlBear
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

    // Metoda pomocnicza do tworzenia ImageView
    private ImageView createImageView(String resourcePath, double size) {
        Image image = getImage(resourcePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    // Obsługa preferowanych pól
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