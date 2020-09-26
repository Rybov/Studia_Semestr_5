package org.openjfx;

import com.pattern.database.tables.Maps;
import com.pattern.database.tables.Pets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static org.openjfx.App.base;

public class PetsController extends SecondaryController implements Initializable {

    private ArrayList<Pets> originalPets;
    private ObservableList<Pets> petsObservableList;

    private int petId = -1;

    private final String wrongInputText= "Attack and defence must be integer type";
    private final String notSelectedPetText = "Please select pet first";
    private final String nameAlreadyUsedText = "Name already used";
    @FXML
    TableView<Pets> petsTable;
    @FXML
    TableColumn<Pets, Integer> petIdColumn, petAttackColumn, petDefenceColumn;
    @FXML
    TableColumn<Pets, String> petNameColumn;

    @FXML
    Label addWarningLabel, modifyWarningLabel, deleteWarningLabel;

    @FXML
    TextField addNameField, addAttackField, addDefenceField,
                modifyNameField, modifyAttackField, modifyDefenceField,
                filterNameField, filterAttackField, filterDefenceField,
                filterIdField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializePetsTable();
        originalPets = base.getPets();
        fillPetsTable();

        hideWarnings();
    }


    private void initializePetsTable() {
        petIdColumn.setCellValueFactory(new PropertyValueFactory<>("idPet"));
        petNameColumn.setCellValueFactory(new PropertyValueFactory<>("petName"));
        petAttackColumn.setCellValueFactory(new PropertyValueFactory<>("attack"));
        petDefenceColumn.setCellValueFactory(new PropertyValueFactory<>("defence"));
    }


    private ArrayList<Pets> filterPets(ArrayList<Pets> pets) {
        ArrayList<Pets> filteredPets = new ArrayList<>();
        String id = filterIdField.getText();
        String name = filterNameField.getText();
        String attack = filterAttackField.getText();
        String defence = filterDefenceField.getText();
        for(Pets pet : pets) {
            if (Integer.toString(pet.getIdPet()).contains(id))
                if (pet.getPetName().contains(name))
                    if (Integer.toString(pet.getAttack()).contains(attack))
                        if (Integer.toString(pet.getDefence()).contains(defence)){
                            filteredPets.add(pet);
                        }
        }
        return filteredPets;
    }

    @FXML
    public void fillPetsTable() {
        ArrayList<Pets> pets = filterPets(originalPets);
        petsObservableList = FXCollections.observableArrayList(pets);
        petsTable.setItems(petsObservableList);
    }


    private void clearFields()
    {

            addNameField.setText("");
            addAttackField.setText("");
            addDefenceField.setText("");
            modifyNameField.setText("");
            modifyAttackField.setText("");
            modifyDefenceField.setText("");
            filterAttackField.clear();
            filterDefenceField.clear();
            filterIdField.clear();
            filterNameField.clear();
    }


    private void hideWarnings() {
        addWarningLabel.setVisible(false);
        modifyWarningLabel.setVisible(false);
        deleteWarningLabel.setVisible(false);
    }


    private void fillModifyFields(Pets pet) {
        modifyNameField.setText(pet.getPetName());
        modifyAttackField.setText(Integer.toString(pet.getAttack()));
        modifyDefenceField.setText(Integer.toString(pet.getDefence()));
    }

    @FXML
    public void petSelected() {
        if (petsTable.getSelectionModel().getSelectedItem() != null) {
            Pets selectedPet = petsTable.getSelectionModel().getSelectedItem();
            petId = selectedPet.getIdPet();
            fillModifyFields(selectedPet);
        }
    }

    @FXML
    public void addPet() {

        String name = getNewName();
        if (name.equals("-1")) {
            addWarningLabel.setText(nameAlreadyUsedText);
            addWarningLabel.setVisible(true);
            return;
        }
        int attack = getIntFromTextField(addAttackField);
        int defence = getIntFromTextField(addDefenceField);

        if (badInput(attack,defence,addWarningLabel)) {
            return;
        }

        addWarningLabel.setVisible(false);
        base.insertPet(name,attack,defence);
        clearFields();
        fillPetsTable();
    }

    @FXML
    public void modifyPet() {

        if (petId == -1) {
            modifyWarningLabel.setText(notSelectedPetText);
            modifyWarningLabel.setVisible(true);
            return;
        }
        String name = getModifyName();
        if (name.equals("-1")) {
            modifyWarningLabel.setText(nameAlreadyUsedText);
            modifyWarningLabel.setVisible(true);
            return;
        }
        int attack = getIntFromTextField(modifyAttackField);
        int defence = getIntFromTextField(modifyDefenceField);

        if (badInput(attack,defence,modifyWarningLabel)) {
            modifyWarningLabel.setText(wrongInputText);
            return;
        }

        modifyWarningLabel.setVisible(false);
        base.updatePet(petId, name,attack,defence);
        clearFields();
        fillPetsTable();
    }

    @FXML
    public void deletePet() {

        if (petId == -1) {
            deleteWarningLabel.setVisible(true);
            return;
        }

        deleteWarningLabel.setVisible(false);
        base.deletePets(petId);
        clearFields();
        fillPetsTable();
    }


    private boolean badInput(int attack, int defence, Label warningLabel) {
        if (attack == -1 || defence == -1) {
            warningLabel.setText(wrongInputText);
            warningLabel.setVisible(true);
            return true;
        } else
        {
            return false;
        }
    }

    private String getNewName() {
        String name = addNameField.getText();
        for(Pets pet: originalPets) {
            if (pet.getPetName().equals(name)){
                name = "-1";
                break;
            }
        }
        return name;
    }

    private String getModifyName() {
        String name = modifyNameField.getText();
        for(Pets pet: originalPets) {
            if (pet.getPetName().equals(name)){
                if (pet.getIdPet() == petId) { break; }
                name = "-1";
                break;
            }
        }
        return name;
    }


}
