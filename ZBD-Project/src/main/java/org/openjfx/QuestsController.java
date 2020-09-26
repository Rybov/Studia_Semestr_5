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

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static org.openjfx.App.base;

public class QuestsController extends SecondaryController implements Initializable {

    private ArrayList<Quests> originalQuests;
    private ObservableList<Quests> questsObservableList;
    private int questId;

    private final String notSelectedQuestText = "Please select quest first!";
    private final String incorrectInputText = "Incorrect input";
    @FXML
    private TableView<Quests> questsTable;
    @FXML
    private TableColumn<Quests, String> nameQuestColumn, fileQuestColumn;
    @FXML
    private TableColumn<Quests, Integer> idQuestColumn, lvlQuestColumn, NPCQuestColumn;
    @FXML
    private Label addWarningLabel, modifyWarningLabel, deleteWarningLabel;
    @FXML
    private TextField addNameField, addFileField, addLvlField,
                        modifyNameField, modifyFileField, modifyLvlField,
                        filterNameField, filterFileField, filterLvlField,
                        filterIdField, filterNPCField;
    @FXML
    private ChoiceBox<String> addNPCField, modifyNPCField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeQuestsTable();

        originalQuests = base.getQuests();
        fillQuestsTable();

        setChoiceBoxes();

        handleWarnings();
    }

    private void initializeQuestsTable() {
        idQuestColumn.setCellValueFactory(new PropertyValueFactory<>("idQuest"));
        lvlQuestColumn.setCellValueFactory(new PropertyValueFactory<>("minLevel"));
        NPCQuestColumn.setCellValueFactory(new PropertyValueFactory<>("idNpc"));
        nameQuestColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        fileQuestColumn.setCellValueFactory(new PropertyValueFactory<>("txtFile"));

    }

    private ArrayList<Quests> filterQuests(ArrayList<Quests> quests) {
        ArrayList<Quests> filteredQuests = new ArrayList<>();
        String id = filterIdField.getText();
        String name = filterNameField.getText();
        String file = filterFileField.getText();
        String lvl = filterLvlField.getText();
        String npc = filterNPCField.getText();
        for ( Quests quest : quests) {
            if (Integer.toString(quest.getIdQuest()).contains(id))
                if (quest.getName().contains(name))
                    if (quest.getTxtFile().contains(file))
                        if (Integer.toString(quest.getMinLevel()).contains(lvl))
                            if (Integer.toString(quest.getIdNpc()).contains(npc)) {
                                filteredQuests.add(quest);
                            }
        }
        return filteredQuests;
    }

    @FXML
    private void fillQuestsTable() {
        ArrayList<Quests> quests = filterQuests(originalQuests);
        questsObservableList = FXCollections.observableArrayList(quests);
        questsTable.setItems(questsObservableList);
    }

    private void handleWarnings() {
        setWarnings();
        hideWarnings();
    }

    private void setWarnings() {
        addWarningLabel.setText(incorrectInputText);
        modifyWarningLabel.setText(incorrectInputText);
        deleteWarningLabel.setText(notSelectedQuestText);
    }

    private void hideWarnings() {
        addWarningLabel.setVisible(false);
        modifyWarningLabel.setVisible(false);
        deleteWarningLabel.setVisible(false);
    }

    private void setChoiceBoxes() {
        ArrayList<Npcs> npcs = base.getNpcs();
        String id;
        String name;
        for (Npcs npc : npcs) {
            id = Integer.toString(npc.getIdNpc());
            name = npc.getName();
            addNPCField.getItems().add(id + " " + name);
            modifyNPCField.getItems().add(id + " " + name);
        }
    }

    @FXML
    private void questSelected() {
        if (questsTable.getSelectionModel().getSelectedItem() != null) {
            questId = questsTable.getSelectionModel().getSelectedItem().getIdQuest();
            modifyFileField.setText(questsTable.getSelectionModel().getSelectedItem().getTxtFile());
            modifyNameField.setText(questsTable.getSelectionModel().getSelectedItem().getName());
            modifyLvlField.setText(Integer.toString(questsTable.getSelectionModel().getSelectedItem().getMinLevel()));
        }
    }

    @FXML
    private void addQuest() {

        String name = getNewName();
        if (name.equals("-1")) {
            addWarningLabel.setVisible(true);
            return; }

        String file = addFileField.getText();

        int lvl = getIntFromTextField(addLvlField);
        if (lvl == -1) {
            addWarningLabel.setVisible(true);
            return;
        }

        String npc[] = getStringFromChoiceBox(addNPCField).split(" ");
        int npcId = Integer.parseInt(npc[0]);

        addWarningLabel.setVisible(false);

        base.insertQuests(name,file,lvl, npcId);
        fillQuestsTable();
        clear();
    }

    @FXML
    private void modifyQuest() {
        if (questId == -1) {
            modifyWarningLabel.setText(notSelectedQuestText);
            modifyWarningLabel.setVisible(true);
            return;
        }

        modifyWarningLabel.setText(incorrectInputText);

        String name = getModifyName();
        if (name.equals("-1")) {
            modifyWarningLabel.setVisible(true);
            return; }

        String file = modifyFileField.getText();

        int lvl = getIntFromTextField(modifyLvlField);
        if (lvl == -1) {
            modifyWarningLabel.setVisible(true);
            return;
        }

        String npc[] = getStringFromChoiceBox(modifyNPCField).split(" ");
        int npcId = Integer.parseInt(npc[0]);

        modifyWarningLabel.setVisible(false);

        base.updateQuests(questId, name,file,lvl, npcId);
        clear();
    }

    @FXML
    private void deleteQuest() {
        if (questId == -1) {    //quest not selected
            deleteWarningLabel.setVisible(true);
            return;
        }
        deleteWarningLabel.setVisible(false);
        base.deleteQuests(questId);
        clear();
    }


    private String getNewName() {
        String name = addNameField.getText(); //pobierz nazwe
        for(Quests quest : originalQuests) {    //dla kazdego questa w bazie
            if (quest.getName().equals(name)) { //sprawdz czy taki quest juz istnieje
                name = "-1";
            }
        }
        return name;
    }

    private String getModifyName() {
        String name = modifyNameField.getText(); //pobierz nazwe
        for(Quests quest : originalQuests) {    //dla kazdego questa w bazie
            if (quest.getName().equals(name)) { //sprawdz czy taki quest juz istnieje
                if (quest.getIdQuest() == questId) {
                    break;
                }
                name = "-1";
            }
        }
        return name;
    }
    private void clear()
    {
        modifyLvlField.clear();
        modifyNameField.clear();
        modifyFileField.clear();
        modifyNPCField.valueProperty().set(null);
        addNPCField.valueProperty().set(null);
        addNameField.clear();
        addFileField.clear();
        addLvlField.clear();
        filterNameField.clear();
        filterFileField.clear();
        filterLvlField.clear();
        filterIdField.clear();
        filterNPCField.clear();
        fillQuestsTable();
    }
}
