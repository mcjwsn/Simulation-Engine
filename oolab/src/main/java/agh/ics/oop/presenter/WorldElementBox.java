package agh.ics.oop.presenter;
import agh.ics.oop.model.Animal;
import agh.ics.oop.model.WorldElement;
import agh.ics.oop.model.popGenotypeCell;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class WorldElementBox extends VBox {
    private static final int IMAGE_HEIGHT = 18;
    private static final int IMAGE_WIDTH = 18;
    private static final int ENERGY_IMAGE_HEIGHT = 3;
    private static final int ENERGY_IMAGE_WIDTH = 14;
    private static final int GENOME_IMAGE_HEIGHT = 30;
    private static final int GENOME_IMAGE_WIDTH = 30;
    private String lastImage;
    private String lastEnergyLevel;
    private static final Map<String, Image> imageCache = new HashMap<>();

    // Cache'ujemy Image zamiast ImageView
    public static Image getImage(String imagePath) {
        return imageCache.computeIfAbsent(imagePath, Image::new);
    }

    public void updateImageGenotype(popGenotypeCell element)
    {
        String currImage = element.getImageResource();
        this.getChildren().clear();
        Image image = getImage(currImage);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(GENOME_IMAGE_WIDTH);
        imageView.setFitHeight(GENOME_IMAGE_HEIGHT);
        this.getChildren().add(imageView);
    }


    public void updateImage(WorldElement element) {
        String currImage = element.getImageResource();

        if (!Objects.equals(currImage, lastImage)) {
            this.getChildren().clear();
            Image image = getImage(currImage);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(IMAGE_WIDTH);
            imageView.setFitHeight(IMAGE_HEIGHT);

            this.getChildren().add(imageView);
            if (element instanceof Animal)
            {
                addEnergyLevel((Animal) element);
            }
            lastImage = currImage; // Update the last image
        }
    }

    public void updateImageTrackedDown(Animal element) {
        String currImage = element.getTrackedDownAnimalImageResource();

        if (!Objects.equals(currImage, lastImage)) {
            this.getChildren().clear();
            Image image = getImage(currImage);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(IMAGE_WIDTH);
            imageView.setFitHeight(IMAGE_HEIGHT);
            this.getChildren().add(imageView);
            addEnergyLevel(element);
            lastImage = currImage;
        }
    }

    private void addEnergyLevel(Animal animal) {
        String energyLevel = animal.getEnergyLevelResource();

        if (!Objects.equals(energyLevel, lastEnergyLevel)) {
            Image image = getImage(energyLevel);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(ENERGY_IMAGE_WIDTH);
            imageView.setFitHeight(ENERGY_IMAGE_HEIGHT);
            this.getChildren().add(imageView);
            lastImage = energyLevel; // Update the last image
        }
    }

    public WorldElementBox(WorldElement element) {
        updateImage(element);
        this.setAlignment(Pos.CENTER);
    }
    public WorldElementBox(Animal animal) {
        updateImage(animal);
        this.setAlignment(Pos.CENTER);
    }
    public WorldElementBox(popGenotypeCell cell) {
        updateImageGenotype(cell);
        this.setAlignment(Pos.CENTER);
    }
}