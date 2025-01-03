package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.control.Slider;
import java.util.List;



public class SimulationPresenter implements MapChangeListener {
    private int xMin;
    private int yMin;
    private int xMax;
    private int yMax;
    private int currentMapWidth;
    private int currentMapHeight;
    private WorldMap worldMap;

    private final int width = 25;
    private final int height = 25;

    private static final int MAPLIMITHIGHT = 100;
    private static final int MAPLIMITWIDTH = 100;


    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
    }
    @FXML
    private Label infoLabel;
    @FXML
    private TextField textField;
    @FXML
    private Button start;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Spinner<Integer> widthSpinner;
    @FXML
    private Spinner<Integer> heightSpinner;
    @FXML
    private Spinner<String> mapVariantSpinner;
    @FXML
    private Spinner<Integer> grassNumberSpinner;
    @FXML
    private Spinner<Integer> energyAdditionSpinner;
    @FXML
    // uzależnić od liczby zwierzakow i mapy
    private Spinner<Integer> plantRegenerationSpinner;
    @FXML
    private Spinner<Integer> numberOfAnimalsSpinner;
    @FXML
    private Spinner<Integer> startingAnimalEnergySpinner;
    @FXML
    private Spinner<Integer> energuNeededForReproductionSpinner;
    @FXML
    private Spinner<Integer> energyLosingWithReproductionSpinner;
    @FXML
    private Spinner<Integer> minGenMutationsSpinner;
    @FXML
    private Spinner<Integer> maxGenMutationsSpinner;
    @FXML
    private Spinner<Integer> genomLengthSpinner;
    @FXML
    private Spinner<String> mutationTypeSpinner;


    private void drawMap() {
        updateBounds();
        xyLabel();
        columnsFunction();
        rowsFunction();
        addElements();
        mapGrid.setGridLinesVisible(true);
    }
    @FXML
    public void onSimulationStartClicked(){
        try{
        List<MoveDirection> directions = OptionsParser.parse(textField.getText().split(" "));
        AbstractWorldMap map1 = new GrassField(15);
        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(8,5), new Vector2d(4,4), new Vector2d(7,6));
        map1.addObserver(this);
        setWorldMap(map1);
        Simulation simulation1 = new Simulation(directions, positions, map1);
        SimulationEngine engine = new SimulationEngine(List.of(simulation1));
        //engine.runAsync();
        //infoLabel.setText("Simulation started with moves: " + moveLabel);
        engine.runAsync();}
        catch(Exception e){
            infoLabel.setText(e.getMessage());
        }
    }
    public void xyLabel(){
        mapGrid.getColumnConstraints().add(new ColumnConstraints(width));
        mapGrid.getRowConstraints().add(new RowConstraints(height));
        Label label = new Label("y/x");
        mapGrid.add(label, 0, 0);
        GridPane.setHalignment(label, HPos.CENTER);
    }
    public void updateBounds(){
        xMin = worldMap.getCurrentBounds().lowerLeft().getX();
        yMin = worldMap.getCurrentBounds().lowerLeft().getY();
        xMax = worldMap.getCurrentBounds().upperRight().getX();
        yMax = worldMap.getCurrentBounds().upperRight().getY();
        currentMapWidth = xMax - xMin + 1;
        currentMapHeight = yMax - yMin + 1;
    }
    public void columnsFunction(){
        for(int i = 0; i< currentMapWidth; i++){
            Label label = new Label(Integer.toString(i+xMin));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(width));
            mapGrid.add(label, i+1, 0);
        }
    }
    public void rowsFunction(){
        for(int i=0; i<currentMapHeight; i++){
            Label label = new Label(Integer.toString(yMax-i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getRowConstraints().add(new RowConstraints(height));
            mapGrid.add(label, 0, i+1);
        }
    }

    public void addElements(){
        for (int i = xMin; i <= xMax; i++) {
            for (int j = yMax; j >= yMin; j--) {
                Vector2d pos = new Vector2d(i, j);
                if (worldMap.isOccupied(pos)) {
                    mapGrid.add(new Label(worldMap.objectAt(pos).toString()), i - xMin + 1, yMax - j + 1);
                }
                else {
                    mapGrid.add(new Label(" "), i - xMin + 1, yMax - j + 1);
                }
                GridPane.setHalignment(mapGrid.getChildren().getLast(), HPos.CENTER);
            }
        }
    }
    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        setWorldMap(worldMap);
        Platform.runLater(() -> {
            clearGrid();
            drawMap();
            infoLabel.setText(message);
        });
    }
    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().getFirst());
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    @FXML
    public void initialize(){
        SpinnerValueFactory<String> valueFactory =
                new SpinnerValueFactory.ListSpinnerValueFactory<>(javafx.collections.FXCollections.observableArrayList("Kula Ziemska","Dziki Sowoniedziedz"));
        mapVariantSpinner.setValueFactory(valueFactory);

        SpinnerValueFactory<String> valueFactory2 =
                new SpinnerValueFactory.ListSpinnerValueFactory<>(javafx.collections.FXCollections.observableArrayList("Pelna losowosc","Podmianka"));
        mutationTypeSpinner.setValueFactory(valueFactory2);
// należy zmienic tego spinner tez na value factory bo inaczej nie da
        // dynamczinie zmieniac maksymalnej wartosci liczby traw
        //heightSpinner.setValue(10);
        //widthSpinner.setValue(10);

    }

    @FXML
    public void onSubmit() {
        String selectedOption = mapVariantSpinner.getValue();
        //System.out.println("Wybrano" + selectedOption);
        //tutaj funckja ma to zbierać (chyba) nwm czemu nie wypisuje
    }

    private void updateArea(){
        int height = heightSpinner.getValue();
        int width = widthSpinner.getValue();
        int maxArea = height * width;
        // do dokonczenia po zmienia spinnera na valuefactory
        //if ( grassNumberSpinner.getValue() > maxArea){
         //   grassNumberSpinner.setValueFactory(maxArea);
        //}

    }
}
