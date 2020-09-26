package org.openjfx;

import com.pattern.database.tables.Items;
import com.pattern.database.tables.Maps;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static org.openjfx.App.base;

public class MapsController extends SecondaryController implements Initializable {

    int mapId = -1;
    private final String nameAlreadyUsedText = "Name already used";
    private final String notSelectedMapText = "Please select map first";

    private ArrayList<Maps> originalMaps;

    private ObservableList<Maps> mapsObservableList;

    @FXML
    private TableView<Maps> mapsTable;
    @FXML
    private TableColumn<Maps, String> nameColumn, fileColumn;
    @FXML
    private TextField nameField, fileField, nameField1, fileField1,
                    filterNameField, filterFileField;
    @FXML
    private Label warningLabel, warningLabel1, warningLabel2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        hideWarnings();

        initializeMapsTable();
        originalMaps = base.getMaps();
        fillMapTable();
    }

    private void initializeMapsTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("mapName"));
        fileColumn.setCellValueFactory(new PropertyValueFactory<>("mapFile"));
    }

    private void hideWarnings() {
        warningLabel.setVisible(false);
        warningLabel1.setVisible(false);
        warningLabel2.setVisible(false);
    }

    private ArrayList<Maps>  filterMaps(ArrayList<Maps> maps) {
        ArrayList<Maps> filteredMaps = new ArrayList<>();

        String name = filterNameField.getText();
        String file = filterFileField.getText();
        for(Maps map: maps) {
            if (map.getMapName().contains(name))
                if (map.getMapFile().contains(file)) {
                    filteredMaps.add(map);
                }
        }

        return filteredMaps;
    }

    @FXML
    public void fillMapTable() {
        ArrayList<Maps> maps = filterMaps(originalMaps);
        mapsObservableList = FXCollections.observableArrayList(maps);
        mapsTable.setItems(mapsObservableList);
    }

    private void fillModifyFields(Maps map) {
        nameField1.setText(map.getMapName());
        fileField1.setText(map.getMapFile());
    }

    @FXML
    public void selectMap() {
        if (mapsTable.getSelectionModel().getSelectedItem() != null) {
            Maps selectedMap = mapsTable.getSelectionModel().getSelectedItem();
            mapId = selectedMap.getIdMap();
            fillModifyFields(selectedMap);
        }
    }

    @FXML
    public void addMap() {
        String name = getNewName();
        if (name.equals("-1")) {
            warningLabel2.setText(nameAlreadyUsedText);
            warningLabel2.setVisible(true);
            return;
        }

        warningLabel2.setVisible(false);
        String file = fileField.getText();
        base.insertMaps(name, file);
        clear();
    }

    @FXML
    public void modifyMap() {
        if (mapId == -1) {
            warningLabel.setText(notSelectedMapText);
            warningLabel.setVisible(true);
            return;
        }
        warningLabel.setVisible(false);
        String name = getModifyName();
        if (name.equals("-1")) {
            warningLabel.setText(nameAlreadyUsedText);
            warningLabel.setVisible(true);
            return;
        }
        String file = fileField1.getText();
        base.updateMaps(mapId, name, file);
        clear();

    }

    @FXML
    public void deleteMap() {
        if (mapId != -1) {
            warningLabel1.setVisible(false);
            base.deleteMaps(mapId);
            clear();
        } else {
            warningLabel1.setVisible(true);
        }
    }

    private String getNewName() {
        String name = nameField.getText();
        for(Maps map: originalMaps) {
            if (map.getMapName().equals(name)){
                name = "-1";
                break;
            }
        }
        return name;
    }

    private String getModifyName() {
        String name = nameField1.getText();
        for(Maps map: originalMaps) {
            if (map.getMapName().equals(name)){
                if (map.getIdMap() == mapId) { break; }
                name = "-1";
                break;
            }
        }
        return name;
    }
    private void clear()
    {
        nameField.clear();
        fileField.clear();
        nameField1.clear();
        fileField1.clear();
        filterNameField.clear();
        filterFileField.clear();
        fillMapTable();


    }
}
