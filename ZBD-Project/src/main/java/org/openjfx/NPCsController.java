package org.openjfx;

import com.pattern.database.tables.Maps;
import com.pattern.database.tables.Npcs;
import com.pattern.database.tables.Quests;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static org.openjfx.App.base;

public class NPCsController extends SecondaryController implements Initializable {

    ArrayList<Npcs> originalNpcs;
    ObservableList<Npcs> npcsObservableList;
    private int npcId = -1;
    private final String nameAlreadyUsedText = "Name already used";
    private final String incorrectInputText = "Incorrect input";
    private final String notSelectedNPCText = "Please select NPC first";

    @FXML
    private TableView<Npcs> npcsTable;
    @FXML
    private TableColumn<Npcs, String> npcNameColumn;
    @FXML
    private TableColumn<Npcs, Integer> npcIdColumn, npcMapColumn,
                                        npcXColumn, npcYColumn, npcZColumn;

    @FXML
    private TableView<Quests> questsTable;
    @FXML
    private TableColumn<Quests, String> questsNameColumn;
    @FXML
    private Label addWarningLabel, modifyWarningLabel, deleteWarningLabel;
    @FXML
    private TextField addNameField, addXField, addYField, addZField,
                    modifyNameField, modifyXField, modifyYField, modifyZField,
                    filterNameField, filterXField, filterYField, filterZField,
                    filterIdField, filterMapField;
    @FXML
    private ChoiceBox<String> addMapField, modifyMapField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeNPCTable();
        initializeQuestsTable();

        originalNpcs = base.getNpcs();
        fillNPCTable();

        setChoiceBoxes();

        hideWarnings();
    }

    private void initializeNPCTable() {
        npcIdColumn.setCellValueFactory(new PropertyValueFactory<>("idNpc"));
        npcNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        npcMapColumn.setCellValueFactory(new PropertyValueFactory<>("idMap"));
        npcXColumn.setCellValueFactory(new PropertyValueFactory<>("xPosition"));
        npcYColumn.setCellValueFactory(new PropertyValueFactory<>("yPosition"));
        npcZColumn.setCellValueFactory(new PropertyValueFactory<>("zPosition"));
    }

    private void initializeQuestsTable() {

        questsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private ArrayList<Npcs> filterNPC(ArrayList<Npcs> npcs) {
        ArrayList<Npcs> filteredNPCs = new ArrayList<>();
        String id = filterIdField.getText();
        String name = filterNameField.getText();
        System.out.println(name);
        String map = filterMapField.getText();
        String x = filterXField.getText();
        String y = filterYField.getText();
        String z = filterZField.getText();
        for(Npcs npc: npcs) {
            if (Integer.toString(npc.getIdNpc()).contains(id))
                if (npc.getName().contains(name))
                    if (Integer.toString(npc.getIdMap()).contains(map))
                        if (Integer.toString(npc.getXPosition()).contains(x))
                            if (Integer.toString(npc.getYPosition()).contains(y))
                                if (Integer.toString(npc.getZPosition()).contains(z)) {
                                    filteredNPCs.add(npc);
                                }
        }
        return filteredNPCs;
    }

    @FXML
    public void fillNPCTable() {
        ArrayList<Npcs> npcs = filterNPC(originalNpcs);
        npcsObservableList = FXCollections.observableArrayList(npcs);
        npcsTable.setItems(npcsObservableList);
    }

    private void fillQuestsTable() {
        ArrayList<Quests> allQuests = base.getQuests();
        ArrayList<Quests> quests = new ArrayList<>();

        for (Quests quest : allQuests) {
            if (quest.getIdNpc() == npcId) {
                quests.add(quest);
            }
        }
        ObservableList<Quests> questsObservableList = FXCollections.observableArrayList(quests);
        questsTable.setItems(questsObservableList);
    }

    private void setChoiceBoxes() {
        ArrayList<Maps> maps = base.getMaps();
        String id;
        String name;
        for (Maps map : maps) {
            id = Integer.toString(map.getIdMap());
            name = map.getMapName();
            addMapField.getItems().add(id + " " + name);
            modifyMapField.getItems().add(id + " " + name);
        }
    }

    private void hideWarnings() {
        addWarningLabel.setVisible(false);
        modifyWarningLabel.setVisible(false);
        deleteWarningLabel.setVisible(false);
    }

    private void clearFields(String where) {
        if (where.equals("add")) {
            addMapField.setValue(null);
            addNameField.setText("");
            addXField.setText("");
            addYField.setText("");
            addZField.setText("");
        }
        if (where.equals("modify")) {
            modifyMapField.setValue(null);
            modifyNameField.setText("");
            modifyXField.setText("");
            modifyYField.setText("");
            modifyZField.setText("");
        }
    }

    private void fillModifyFields(Npcs npc) {
        modifyNameField.setText(npc.getName());
        modifyMapField.setValue(Integer.toString(npc.getIdMap()));
        modifyXField.setText(Integer.toString(npc.getXPosition()));
        modifyYField.setText(Integer.toString(npc.getYPosition()));
        modifyZField.setText(Integer.toString(npc.getZPosition()));
    }

    @FXML
    public void npcSelected() {
        if (npcsTable.getSelectionModel().getSelectedItem() != null) {
            npcId = npcsTable.getSelectionModel().getSelectedItem().getIdNpc();
            fillModifyFields(npcsTable.getSelectionModel().getSelectedItem());
            fillQuestsTable();
        }
    }

    @FXML
    public void addNPC() {

        String name = getNewName();
        if (name.equals("-1")) {
            addWarningLabel.setText(nameAlreadyUsedText);
            addWarningLabel.setVisible(true);
            return;
        }
        String map[] = getStringFromChoiceBox(addMapField).split(" ");
        int mapId = Integer.parseInt(map[0]);
        if (mapId == -1) {
            addWarningLabel.setText(incorrectInputText);
            addWarningLabel.setVisible(true);
            return;
        }
        int x = getIntFromTextField(addXField);
        int y = getIntFromTextField(addYField);
        int z = getIntFromTextField(addZField);
        if (x == -1 || y == -1 || z == -1) {
            addWarningLabel.setText(incorrectInputText);
            addWarningLabel.setVisible(true);
            return;
        }

        addWarningLabel.setVisible(false);

        base.insertNPC(name,mapId,x,y,z);
        clearFields("add");
        fillNPCTable();
    }

    @FXML
    public void modifyNPC() {

        if (npcId == -1) {
            modifyWarningLabel.setText(notSelectedNPCText);
            modifyWarningLabel.setVisible(true);
            return;
        }
        String name = getModifyName();
        if (name.equals("-1")) {
            modifyWarningLabel.setText(nameAlreadyUsedText);
            modifyWarningLabel.setVisible(true);
            return;
        }
        String map[] = getStringFromChoiceBox(modifyMapField).split(" ");
        int mapId = Integer.parseInt(map[0]);
        if (mapId == -1) {
            modifyWarningLabel.setText(incorrectInputText);
            modifyWarningLabel.setVisible(true);
            return;
        }
        int x = getIntFromTextField(modifyXField);
        int y = getIntFromTextField(modifyYField);
        int z = getIntFromTextField(modifyZField);
        if (x == -1 || y == -1 || z == -1) {
            modifyWarningLabel.setText(incorrectInputText);
            modifyWarningLabel.setVisible(true);
            return;
        }

        modifyWarningLabel.setVisible(false);

        base.updateNPC(npcId ,name,mapId,x,y,z);
        clearFields("modify");
        fillNPCTable();
    }

    @FXML
    public void deleteNPC() {
        if (npcId == -1) {
            deleteWarningLabel.setVisible(true);
            return;
        }
        base.deleteNPCS(npcId);
        fillNPCTable();
    }

    //input
    private String getNewName() {
        String name = addNameField.getText();
        for(Npcs npc: originalNpcs) {
            if (name.equals(npc.getName())) {
                name = "-1";
            }
        }
        return name;
    }

    private String getModifyName() {
        String name = modifyNameField.getText();
        for(Npcs npc: originalNpcs) {
            if (name.equals(npc.getName())) {
                if (npc.getIdNpc() == npcId) { break; }
                name = "-1";
            }
        }
        return name;
    }
}
