package agh.ics.oop.presenter;
import agh.ics.oop.model.*;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class WorldElementBox extends VBox {
    private String lastImage;
    private String lastEnergyLevel;
    private static final Map<String, Image> imageCache = new HashMap<>();

    // Cache'ujemy Image zamiast ImageView
    public static Image getImage(String imagePath) {
        return imageCache.computeIfAbsent(imagePath, Image::new);
    }

    public void updateImageGenotype(popGenotypeCell element, Integer size)
    {
        String currImage = element.getImageResource();
        this.getChildren().clear();
        Image image = getImage(currImage);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(size*1.1);
        imageView.setFitHeight(size*1.1);
        this.getChildren().add(imageView);
    }

    public void updateImage(EmptyCell element, Integer size)
    {
        String currImage = element.getImageResource();
        Image image = getImage(currImage);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth((int)size);
        imageView.setFitHeight((int)size);
        this.getChildren().add(imageView);
    }

    public void updateImage(PrefferdCell element, Integer size)
    {
        String currImage = element.getImageResource();
        Image image = getImage(currImage);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth((int)size);
        imageView.setFitHeight((int)size);

        imageView.setOpacity(0.5);
        this.getChildren().add(imageView);
    }

    public void updateImage(Animal element, Integer size) {
        String currImage = element.getImageResource();

        if (!Objects.equals(currImage, lastImage)) {
            this.getChildren().clear();
            Image image = getImage(currImage);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth((int)size);
            imageView.setFitHeight((int)size);
            imageView.setStyle("-fx-border-width: 2;");

            this.getChildren().add(imageView);
            if (element instanceof Animal)
            {
                addEnergyLevel((Animal) element,size);
            }
            lastImage = currImage; // Update the last image
        }
    }

    public void updateImage(WorldElement element, Integer size) {
        String currImage = element.getImageResource();

        if (!Objects.equals(currImage, lastImage)) {
            this.getChildren().clear();
            Image image = getImage(currImage);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth((int)(size));
            imageView.setFitHeight((int)(size));
            imageView.setStyle("-fx-border-width: 2;");

            this.getChildren().add(imageView);
            if (element instanceof Animal)
            {
                addEnergyLevel((Animal) element,size);
            }
            lastImage = currImage; // Update the last image
        }
    }

    public void updateImageTrackedDown(Animal element,Integer size) {
        String currImage = element.getTrackedDownAnimalImageResource();
        if (!Objects.equals(currImage, lastImage)) {
            this.getChildren().clear();
            Image image = getImage(currImage);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(size);
            imageView.setFitHeight(size);
            imageView.setStyle("-fx-border-width: 2;");
            this.getChildren().add(imageView);
            addEnergyLevel(element,size);
            lastImage = currImage;
        }
    }

    private void addEnergyLevel(Animal animal,Integer size) {
        String energyLevel = animal.getEnergyLevelResource();

        if (!Objects.equals(energyLevel, lastEnergyLevel)) {
            Image image = getImage(energyLevel);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth((int)(0.65*size));
            imageView.setFitHeight((int)(0.1*size));
            imageView.setStyle("-fx-border-width: 2;");
            this.getChildren().add(imageView);
            lastImage = energyLevel; // Update the last image
        }
    }

    public WorldElementBox(WorldElement element, Integer size) {
        updateImage(element,size);
        this.setAlignment(Pos.CENTER);
    }
    public WorldElementBox(PrefferdCell element, Integer size) {
        updateImage(element,size);
        this.setAlignment(Pos.CENTER);
    }
    public WorldElementBox(EmptyCell element, Integer size) {
        updateImage(element,size);
        this.setAlignment(Pos.CENTER);
    }
    public WorldElementBox(Animal animal, Integer size) {
        updateImage(animal,size);
        this.setAlignment(Pos.CENTER);
    }
    public WorldElementBox(popGenotypeCell cell, Integer size) {
        updateImageGenotype(cell,size);
        this.setAlignment(Pos.CENTER);
    }
    private boolean isPreferred = false;

    public void setPreferred(boolean preferred) {
        this.isPreferred = preferred;
        if (preferred) {
            this.setStyle("-fx-border-color: yellow; -fx-border-width: 1; -fx-border-radius: 4;");
        } else {
            this.setStyle("");
        }
    }
}