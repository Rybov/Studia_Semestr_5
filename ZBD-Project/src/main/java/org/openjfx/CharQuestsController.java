package org.openjfx;

import com.pattern.database.tables.CharQuests;
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

import static org.openjfx.App.*;

public class CharQuestsController extends SecondaryController implements Initializable {


    private int questId = -1;
    ArrayList<CharQuests> originalQuests;
    ObservableList<CharQuests> questsObservableList;

    private final String[] possiblesStatuses = {"Not started", "During", "Done"};
    private final String questAlreadyAddedText = "Character already has this quest";
    private final String incorrectInputText = "Incorrect input";
    private final String notSelectedQuestText = "Please select quest first";
    @FXML
    private TableView<CharQuests> questsTable;
    @FXML
    private TableColumn<CharQuests, Integer> idcolumn,questColumn, characterColumn;
    @FXML
    private TableColumn<CharQuests, String> statusColumn;
    @FXML
    private TextField addQuestField, addCharacterField,
            modifyQuestField, modifyCharacterField,
            filterQuestField, filterCharacterField,
            filterStatusField, filteridQuestField;
    @FXML
    private ChoiceBox<String> addStatusField, modifyStatusField;
    @FXML
    private Label addWarningLabel, modifyWarningLabel, deleteWarningLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeQuestTable();
        originalQuests = base.getCharQuests();
        fillQuestTable();
        setChoiceBoxes();
        hideWarnings();
    }

    private void initializeQuestTable() {

        idcolumn.setCellValueFactory(new PropertyValueFactory<>("idCharQuest"));
        questColumn.setCellValueFactory(new PropertyValueFactory<>("idQuest"));
        characterColumn.setCellValueFactory(new PropertyValueFactory<>("idChar"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

    }

    private void setChoiceBoxes() {

        addStatusField.getItems().addAll(possiblesStatuses);
        modifyStatusField.getItems().addAll(possiblesStatuses);
    }

    private void hideWarnings() {
        addWarningLabel.setVisible(false);
        modifyWarningLabel.setVisible(false);
        deleteWarningLabel.setVisible(false);
    }

    private ArrayList<CharQuests> filterQuests(ArrayList<CharQuests> quests) {
        ArrayList<CharQuests> filteredQuests = new ArrayList<>();
        String idCharQuest = filteridQuestField.getText();
        String idQuest = filterQuestField.getText();
        String idCharacter = filterCharacterField.getText();
        String status = filterStatusField.getText();
        for (CharQuests quest : quests) {
            if (Integer.toString(quest.getIdCharQuest()).contains(idCharQuest))
                if (Integer.toString(quest.getIdQuest()).contains(idQuest))
                    if (Integer.toString(quest.getIdChar()).contains(idCharacter))
                        if (quest.getStatus().contains(status)) {
                            filteredQuests.add(quest);
                        }
        }

        return filteredQuests;
    }

    @FXML
    public void fillQuestTable() {
        base.loadChar_Quests();
        base.loadCharacters();
        base.loadQuests();
        originalQuests = base.getCharQuests();
        ArrayList<CharQuests> quests = filterQuests(originalQuests);
        questsObservableList = FXCollections.observableArrayList(quests);
        questsTable.setItems(questsObservableList);
    }

    private void fillModifyFields(CharQuests quest) {
        modifyQuestField.setText(Integer.toString(quest.getIdQuest()));
        modifyCharacterField.setText(Integer.toString(quest.getIdChar()));
        modifyStatusField.setValue(quest.getStatus());
    }

    @FXML
    public void questSelected() {
        if (questsTable.getSelectionModel().getSelectedItem() != null) {
            CharQuests selectedQuest = questsTable.getSelectionModel().getSelectedItem();
            questId = selectedQuest.getIdCharQuest();
            fillModifyFields(selectedQuest);
        }
    }

    @FXML
    public void addQuest() {

        int quest = getQuest(addQuestField);
        int character = getCharacter(addCharacterField);
        String status = getStringFromChoiceBox(addStatusField);

        if (quest == -1 || character == -1 || status.equals("-1")) {
            addWarningLabel.setText(incorrectInputText);
            addWarningLabel.setVisible(true);
            return;
        }

        if (!PairOfParametersExists(quest, character)) {
            addWarningLabel.setText(questAlreadyAddedText);
            addWarningLabel.setVisible(true);
            return;
        }

        addWarningLabel.setVisible(false);
        base.insertCharQuest(quest, character, status);
        clear();
    }

    @FXML
    public void modifyQuest() {
        if (questId == -1) {
            modifyWarningLabel.setText(notSelectedQuestText);
            modifyWarningLabel.setVisible(true);
            return;
        }

        int quest = getQuest(modifyQuestField);
        int character = getCharacter(modifyCharacterField);
        String status = getStringFromChoiceBox(modifyStatusField);

        if (quest == -1 || character == -1 || status.equals("-1")) {
            addWarningLabel.setText(incorrectInputText);
            addWarningLabel.setVisible(true);
            return;
        }
        base.updateCharQuest(questId, quest, character, status);
        clear();

    }

    @FXML
    public void deleteQuest() {
        if (questId == -1) {
            deleteWarningLabel.setVisible(true);
            return;
        }
        deleteWarningLabel.setVisible(false);
        base.deleteCharQuest(questId);
        clear();
    }

    private boolean PairOfParametersExists(int addedQuest, int addedCharacter) {


        for (CharQuests quest : originalQuests) {
            if (quest.getIdQuest() == addedQuest &&
                    quest.getIdChar() == addedCharacter) {
                return true;
            }
        }

        return false;
    }

    private int getQuest(TextField questField) {
        int addedQuest = getIntFromTextField(questField);
        for (CharQuests quest : originalQuests) {
            if (quest.getIdQuest() == addedQuest) {
                return addedQuest;
            }
        }
        return -1;
    }

    private int getCharacter(TextField characterField) {
        int addedCharacter = getIntFromTextField(characterField);
        for (CharQuests quest : originalQuests) {
            if (quest.getIdChar() == addedCharacter) {
                return addedCharacter;
            }
        }
        return -1;
    }

    private void clear() {
        filteridQuestField.clear();
        addQuestField.clear();
        addCharacterField.clear();
        modifyQuestField.clear();
        modifyCharacterField.clear();
        filterQuestField.clear();
        filterCharacterField.clear();
        filterStatusField.clear();
        fillQuestTable();
    }

}
