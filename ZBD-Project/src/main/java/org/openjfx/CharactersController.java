package org.openjfx;

import com.pattern.database.tables.*;
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
import static org.openjfx.App.*;

public class CharactersController extends SecondaryController implements Initializable {
    public ArrayList<Characters> originalCharacters;

    private ObservableList<Characters> charactersObservableList;

    private final String chooseCharText = "Please, select character first";
    private final String choosePetText = "Please, select pet first";
    private final String chooseItemText = "Please, select item first";
    private final String changeNameText = "Name already used";
    private final String thereIsNoUserText = "User ID is incorrect";
    private final String incorrectInput = "Incorrect input";
    private int charId = -1;
    private int charPetId = -1;
    private int charItemId = -1;
    private String whatToDo = "";

//---------------------------FXML Objects

    @FXML
    private TableView<Characters> characterTable;
    @FXML
    private TableColumn<Characters, Integer> idChar, userID, lvlChar, expChar,
                                                serverChar, attackChar, defenceChar, mapChar;
    @FXML
    private TableColumn<Characters, String> nameChar, classChar, genderChar;

    @FXML
    private TableView<CharPet> petsTable;
    @FXML
    private TableColumn<CharPet, Integer> idPetColumn, lvlPet, attackPet, defencePet;
    @FXML
    private TableColumn<CharPet, String> namePet;

    @FXML
    private TableView<Equipments> eqTable;
    @FXML
    private TableColumn<Equipments, Integer> nameEq, lvlEq, attackEq, defenceEq, wearedEq;

    @FXML
    private Button confirmButton;
    @FXML
    private Label itemIdLabel, petIdLabel, warningLabel, wearedLabel, nameLabel, lvlLabel, attackLabel,
                    defenceLabel, classLabel, userIDLabel, genderLabel, expLabel, serverLabel, mapLabel;
    @FXML
    private TextField nameField, lvlField, attackField, defenceField, userIDField, expField,
                    filterIdField, filterNameField, filterUserIDField, filterClassField,
                    filterGenderField, filterLvlField, filterExpField, filterServerField,
                    filterAttackField, filterDefenceField, filterMapField;

    @FXML
    private ChoiceBox<String> itemIdField, petIdField, wearedField, classField, genderField,
                                serverField, mapField;
    //----------------------------------------

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeCharTable();

        initializePetTable();

        initializeEqTable();

        setChoiceBoxes();

        originalCharacters = base.getCharacters();

        fillCharTable();

        hideFields();

    }


    private void initializeCharTable() {
        idChar.setCellValueFactory(new PropertyValueFactory<>("idChar"));
        nameChar.setCellValueFactory(new PropertyValueFactory<>("name"));
        userID.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        classChar.setCellValueFactory(new PropertyValueFactory<>("clas"));
        genderChar.setCellValueFactory(new PropertyValueFactory<>("gender"));
        lvlChar.setCellValueFactory(new PropertyValueFactory<>("charlvl"));
        expChar.setCellValueFactory(new PropertyValueFactory<>("experience"));
        serverChar.setCellValueFactory(new PropertyValueFactory<>("idServer"));
        attackChar.setCellValueFactory(new PropertyValueFactory<>("attack"));
        defenceChar.setCellValueFactory(new PropertyValueFactory<>("defence"));
        mapChar.setCellValueFactory(new PropertyValueFactory<>("idMap"));
    }


    private void initializePetTable() {
        idPetColumn.setCellValueFactory(new PropertyValueFactory<>("idPet"));
        namePet.setCellValueFactory(new PropertyValueFactory<>("Char_Pet_name"));
        lvlPet.setCellValueFactory(new PropertyValueFactory<>("petLevel"));
        attackPet.setCellValueFactory(new PropertyValueFactory<>("bonusAttack"));
        defencePet.setCellValueFactory(new PropertyValueFactory<>("bonusDefence"));
    }


    private void initializeEqTable() {
        nameEq.setCellValueFactory(new PropertyValueFactory<>("Eq_name"));
        lvlEq.setCellValueFactory(new PropertyValueFactory<>("levelOfUpgrade"));
        attackEq.setCellValueFactory(new PropertyValueFactory<>("bonusAttack"));
        defenceEq.setCellValueFactory(new PropertyValueFactory<>("bonusDefence"));
        wearedEq.setCellValueFactory(new PropertyValueFactory<>("weared"));
    }


    private void setChoiceBoxes() {
        wearedField.getItems().addAll(wearedLevels);

        classField.getItems().addAll(classes);

        genderField.getItems().addAll(genders);

        setMapField();
        setServerField();
        setPetIdField();
        setItemIdField();
    }


    private void setMapField() {
        ArrayList<Maps> maps = base.getMaps();
        for (Maps map : maps) {
            mapField.getItems().add(Integer.toString(map.getIdMap()));
        }
    }


    private void setServerField() {
        ArrayList<Servers> servers = base.getServers();
        for (Servers server : servers) {
            serverField.getItems().add(Integer.toString(server.getIdServer()));
        }
    }


    private void setPetIdField() {
        ArrayList<Pets> pets = base.getPets();
        for(Pets pet : pets) {
            petIdField.getItems().add(Integer.toString(pet.getIdPet()));
        }
    }


    private void setItemIdField() {
        ArrayList<Items> items = base.getItems();
        for(Items item : items) {
            itemIdField.getItems().add(item.getIdItem() + " " + item.getName());
        }
    }


    private ArrayList<Characters> filterCharacters(ArrayList<Characters> characters) {
        ArrayList<Characters> filteredCharacters = new ArrayList<>();
        String id = filterIdField.getText();
        String name = filterNameField.getText();
        String userId = filterUserIDField.getText();
        String classChar = filterClassField.getText();
        String gender = filterGenderField.getText();
        String lvl = filterLvlField.getText();
        String exp = filterExpField.getText();
        String server = filterServerField.getText();
        String attack = filterAttackField.getText();
        String defence = filterDefenceField.getText();
        String mapId = filterMapField.getText();
        for (Characters character : characters) {
            if (Integer.toString(character.getIdChar()).contains(id))
                if (character.getName().contains(name))
                    if (Integer.toString(character.getIdUser()).contains(userId))
                        if (character.getClas().contains(classChar))
                            if (character.getGender().contains(gender))
                                if (Integer.toString(character.getCharlvl()).contains(lvl))
                                    if (Integer.toString(character.getExperience()).contains(exp))
                                        if (Integer.toString(character.getIdServer()).contains(server))
                                            if (Integer.toString(character.getAttack()).contains(attack))
                                                if (Integer.toString(character.getDefence()).contains(defence))
                                                    if (Integer.toString(character.getIdMap()).contains(mapId)) {
                                                        filteredCharacters.add(character);
                                                    }
        }
        return filteredCharacters;
    }

    @FXML
    private void fillCharTable() {
        ArrayList<Characters> characters = filterCharacters(originalCharacters);
        charactersObservableList = FXCollections.observableArrayList(characters);
        characterTable.setItems(charactersObservableList);
    }


    private void fillPetsTable() {
        ArrayList<CharPet> pets = base.getCharPets(charId);
        ObservableList<CharPet> petsObservableList = FXCollections.observableArrayList(pets);
        petsTable.setItems(petsObservableList);
    }


    private void fillEqTable() {
        ArrayList<Equipments> eq = base.getEquipments(charId);
        ObservableList<Equipments> eqObservableList = FXCollections.observableArrayList(eq);
        eqTable.setItems(eqObservableList);
    }


    private void hideFields() {

        itemIdLabel.setVisible(false);
        petIdLabel.setVisible(false);
        confirmButton.setVisible(false);
        nameLabel.setVisible(false);
        lvlLabel.setVisible(false);
        attackLabel.setVisible(false);
        defenceLabel.setVisible(false);
        classLabel.setVisible(false);
        userIDLabel.setVisible(false);
        genderLabel.setVisible(false);
        expLabel.setVisible(false);
        serverLabel.setVisible(false);
        mapLabel.setVisible(false);
        wearedLabel.setVisible(false);

        nameField.setVisible(false);
        lvlField.setVisible(false);
        attackField.setVisible(false);
        defenceField.setVisible(false);
        userIDField.setVisible(false);
        expField.setVisible(false);

        classField.setVisible(false);
        wearedField.setVisible(false);
        genderField.setVisible(false);
        serverField.setVisible(false);
        mapField.setVisible(false);
        petIdField.setVisible(false);
        itemIdField.setVisible(false);

        warningLabel.setVisible(false);
    }


    private void showCharFields() {
        hideFields();
        confirmButton.setVisible(true);
        nameLabel.setVisible(true);
        lvlLabel.setVisible(true);
        attackLabel.setVisible(true);
        defenceLabel.setVisible(true);
        classLabel.setVisible(true);
        ;
        userIDLabel.setVisible(true);
        ;
        genderLabel.setVisible(true);
        ;
        expLabel.setVisible(true);
        ;
        serverLabel.setVisible(true);
        ;
        mapLabel.setVisible(true);

        nameField.setVisible(true);
        lvlField.setVisible(true);
        attackField.setVisible(true);
        defenceField.setVisible(true);
        userIDField.setVisible(true);
        expField.setVisible(true);

        classField.setVisible(true);
        genderField.setVisible(true);
        serverField.setVisible(true);
        mapField.setVisible(true);

    }


    private void showPetFields() {
        hideFields();
        confirmButton.setVisible(true);
        nameLabel.setVisible(true);
        lvlLabel.setVisible(true);
        attackLabel.setVisible(true);
        defenceLabel.setVisible(true);
        petIdLabel.setVisible(true);

        nameField.setVisible(true);
        lvlField.setVisible(true);
        attackField.setVisible(true);
        defenceField.setVisible(true);
        petIdField.setVisible(true);
    }


    private void showItemFields() {
        hideFields();
        confirmButton.setVisible(true);
        itemIdLabel.setVisible(true);
        lvlLabel.setVisible(true);
        attackLabel.setVisible(true);
        defenceLabel.setVisible(true);
        wearedLabel.setVisible(true);

        itemIdField.setVisible(true);
        lvlField.setVisible(true);
        attackField.setVisible(true);
        defenceField.setVisible(true);
        wearedField.setVisible(true);
    }


    private void showWarning(String text) {
        warningLabel.setVisible(true);
        warningLabel.setText(text);
    }

    @FXML
    private void characterSelected() {
        if (characterTable.getSelectionModel().getSelectedItem() != null) {
            charId = characterTable.getSelectionModel().getSelectedItem().getIdChar();
            charPetId = -1;
            charItemId = -1;
            try {
                fillPetsTable();
                fillEqTable();
            } catch (Exception e) {
                System.out.println("Probably there is no connection to database");
            }
        }

    }

    @FXML
    private void petSelected() {
        if (petsTable.getSelectionModel().getSelectedItem() != null) {
            charPetId = petsTable.getSelectionModel().getSelectedItem().getIdCharPet();
        }
    }

    @FXML
    private void itemSelected() {
        if (eqTable.getSelectionModel().getSelectedItem() != null) {
            charItemId = eqTable.getSelectionModel().getSelectedItem().getIdCharItem();
        }
    }

    @FXML
    private void addChar() {
        hideFields();
        showCharFields();
        nameField.setText("");
        lvlField.setText("");
        attackField.setText("");
        defenceField.setText("");
        classField.setValue(null);
        userIDField.setText("");
        genderField.setValue(null);
        expField.setText("");
        serverField.setValue(null);
        mapField.setValue(null);
        whatToDo = "addChar";
    }

    @FXML
    private void addPet() {
        hideFields();
        if (charId != -1) { //jesli wybrano postac, nie mozna dodac jesli nie wiemy komu go przypisac
            warningLabel.setVisible(false);
            showPetFields();
            nameField.setText("");
            lvlField.setText("");
            attackField.setText("");
            defenceField.setText("");
            whatToDo = "addPet";
        } else {
            showWarning(chooseCharText);
        }
    }

    @FXML
    private void addItem() {
        hideFields();
        if (charId != -1) { //jesli wybrano postac, nie mozna dodac jesli nie wiemy komu go przypisac
            showItemFields();
            nameField.setText("");
            lvlField.setText("");
            attackField.setText("");
            defenceField.setText("");
            wearedField.setValue(null);
            whatToDo = "addItem";
        } else {
            showWarning(chooseCharText);
        }
    }

    @FXML
    private void modifyChar() {
        hideFields();
        if (charId != -1) { //jesli cos jest wybrane
            showCharFields();
            Characters character = base.getCharById(charId);
            nameField.setText(character.getName());
            lvlField.setText(Integer.toString(character.getCharlvl()));
            attackField.setText(Integer.toString(character.getAttack()));
            defenceField.setText(Integer.toString(character.getDefence()));
            classField.setValue(character.getClas());
            userIDField.setText(Integer.toString(character.getIdUser()));
            genderField.setValue(character.getGender());
            expField.setText(Integer.toString(character.getExperience()));
            serverField.setValue(Integer.toString(character.getIdServer()));
            mapField.setValue(Integer.toString(character.getIdMap()));
            whatToDo = "modifyChar";
        } else {
            showWarning(chooseCharText);
        }

    }

    @FXML
    private void modifyPet() {
        hideFields();
        if (charPetId != -1) {
            showPetFields();
            CharPet pet = base.getCharPetById(charPetId);
            nameField.setText(Integer.toString(pet.getIdCharPet()));
            lvlField.setText(Integer.toString(pet.getPetLevel()));
            attackField.setText(Integer.toString(pet.getBonusAttack()));
            defenceField.setText(Integer.toString(pet.getBonusDefence()));
            whatToDo = "modifyPet";
        } else {
            showWarning(choosePetText);
        }

    }

    @FXML
    private void modifyItem() {
        hideFields();
        if (charItemId != -1) {
            showItemFields();
            Equipments item = base.getEqById(charItemId);
            nameField.setText(Integer.toString(item.getIdCharItem()));
            lvlField.setText(Integer.toString(item.getLevelOfUpgrade()));
            attackField.setText(Integer.toString(item.getBonusAttack()));
            defenceField.setText(Integer.toString(item.getBonusDefence()));
            whatToDo = "modifyItem";
        } else {
            showWarning(chooseItemText);
        }

    }

    @FXML
    private void deleteChar() {
        if (charId != -1) {
            base.deleteChar(charId);
            try {
                charId=-1;
                fillCharTable();
                fillEqTable();
                fillPetsTable();
            } catch (Exception e) {
                System.out.println("Something went wrong :/");
            }
        } else {
            showWarning(chooseCharText);
        }
    }

    @FXML
    private void deletePet() {
        if (charPetId != -1) {
            base.deleteCharPet(charPetId);
            try {
                fillPetsTable();
            } catch (Exception e) {
                System.out.println("Something went wrong :/");
            }
        } else {
            showWarning(choosePetText);
        }

    }

    @FXML
    private void deleteItem() {
        if (charItemId != -1) {
            base.deleteEq(charItemId);
            try {
                fillEqTable();
            } catch (Exception e) {
                System.out.println("Something went wrong :/");
            }
        } else {
            showWarning(chooseItemText);
        }
    }

    @FXML
    private void confirmChange() {

        String name = getName();
        if (name.equals("-1") &&
                (!whatToDo.equals("addItem") &&
                        !whatToDo.equals("modifyItem"))) {
            showWarning(changeNameText);
            return;
        }
        int lvl = getIntFromTextField(lvlField);
        int attack = getIntFromTextField(attackField);
        int defence = getIntFromTextField(defenceField);
        int exp = getIntFromTextField(expField);

        String classChar = getStringFromChoiceBox(classField);

        int userID = getUserID();
        if (userID == -1 &&
                (whatToDo.equals("addChar")
                    || whatToDo.equals("modifyChar"))) {
            showWarning(thereIsNoUserText);
            return;
        }
        String gender = getStringFromChoiceBox(genderField);

        int server = getIntFromChoiceBox(serverField);
        int map = getIntFromChoiceBox(mapField);
        int weared = getWeared();

        int petId = getIntFromChoiceBox(petIdField);

        String item[] = getStringFromChoiceBox(itemIdField).split(" ");
        int itemId = Integer.parseInt(item[0]);
        String itemName = getItemName(item);

        switch (whatToDo) {
            case "addChar":
                if (checkCharData(name, userID, classChar, gender,
                                lvl, exp, server, attack,
                                defence, map)) {
                    base.insertCharacters(name, userID, classChar,
                                        gender, lvl, exp, server,
                                        attack, defence, map);
                    fillCharTable();
                    hideFields();
                } else {
                    showWarning(incorrectInput);
                }
                break;
            case "addPet":
                if (checkPetData(petId, name, lvl , attack, defence)) {
                    base.insertCharPet(petId, charId, name, lvl, 0, attack, defence);
                    fillPetsTable();
                    hideFields();
                } else {
                    showWarning(incorrectInput);
                }
                break;
            case "addItem":
                if (checkEqData(itemId, lvl, attack, defence, weared)) {
                    base.insertEq(charId, itemId, itemName, lvl, attack, defence, weared);
                    fillEqTable();
                    hideFields();
                } else {
                    showWarning(incorrectInput);
                }
                break;
            case "modifyChar":
                if (checkCharData(name, userID, classChar, gender,
                        lvl, exp, server, attack,
                        defence, map)) {

                    base.updateCharacters(charId, name, userID,
                            classChar, gender, lvl, exp,
                            server, attack, defence, map);
                    fillCharTable();
                    hideFields();
                } else {
                    showWarning(incorrectInput);
                }
                break;
            case "modifyPet":
                if (checkPetData(petId, name, lvl, attack, defence)) {
                    base.updateCHAR_PET(charPetId, petId, charId, name, lvl, 0, attack, defence);
                    fillPetsTable();
                    hideFields();
                } else {
                    showWarning(incorrectInput);
                }
                break;
            case "modifyItem":
                if (checkEqData(itemId, lvl, attack, defence, weared)) {
                    base.updateEq(charItemId, charId, itemId, itemName, lvl, attack, defence, weared);
                    fillEqTable();
                    hideFields();
                } else {
                    showWarning(incorrectInput);
                }
                break;

        }
    }



    //input methods

    private String getName() {
        String name = nameField.getText();
        int server = 1;
        try {
            server = Integer.parseInt(serverField.getValue());
        } catch (Exception e) {
            return "-1";
        }
        for (Characters character : originalCharacters) {
            if (character.getName().equals(name) &&
                    character.getIdServer() == server) {
                if (whatToDo.equals("modifyChar") &&
                        character.getIdChar() == charId ) {
                    break;
                }
                name = "-1";
                break;
            }
        }
        return name;
    }

    private int getUserID() {
        int userID = getIntFromTextField(userIDField);
        boolean userExist = false;
        if (userID != -1) {
            ArrayList<Users> users = base.getUsers();
            for (Users user : users) {
                if (user.getIdUser() == userID) {
                    userExist = true;
                    break;
                }
            }
        }
        if (!userExist) userID = -1;
        return userID;
    }

    private int getWeared() {
        String little_string = getStringFromChoiceBox(wearedField);
        if (little_string.equals("-1")) return -1;
        little_string = little_string.substring(0, little_string.length() - 1); //utnij ostatni znak (%)
        int weared = Integer.parseInt(little_string);
        return weared;
    }

    private String getItemName(String[] text) {
        String result = "";
        for(int i = 1; i<text.length; i++) {
            result = result + text[i] + " ";
        }
        try {
            result = result.substring(0, result.length() - 1); //utnij ostatni znak (" ")
        }   catch (Exception e) {
            return "-1";
        }
        return result;
    }

    //--checking method
    private boolean checkCharData(String name, int userID,
                                  String classChar, String gender,
                                  int lvl, int exp, int server,
                                  int attack, int defence, int map) {

        if (name.equals("-1")) return false;
        if (userID == -1) return false;
        if (classChar.equals("-1")) return false;
        if (gender.equals("-1")) return false;
        if (lvl == -1) return false;
        if (exp == -1) return false;
        if (server == -1) return false;
        if (attack == -1) return false;
        if (defence == -1) return false;
        if (map == -1) return false;
        return true;
    }


    private  boolean checkPetData(int petId, String name, int lvl, int attack, int defence) {

        if (name.equals("-1")) return false;
        if (lvl == -1) return false;
        if (attack == -1) return false;
        if (defence == -1) return false;
        if (petId == -1) return false;
        return true;
    }


    private boolean checkEqData(int itemId,int lvl,int attack,int defence,int weared) {

        if (itemId == -1) return false;
        if (lvl == -1) return false;
        if (attack == -1) return false;
        if (defence == -1) return false;
        if (weared == -1) return false;
        return true;
    }
}
