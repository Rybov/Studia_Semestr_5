package org.openjfx;

import com.pattern.database.tables.Items;
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
public class ItemsController extends SecondaryController implements Initializable {
    private ArrayList<Items> originalItems;
    /*public ArrayList<Items> items = new ArrayList<>() {
        {

            add(new Items(0, "Moonlight sword", "Sword", 100, 0));
            add(new Items(1,"Simply armor", "armor", 10, 20));
        }
    };*/
    private ObservableList<Items> itemsObservableList;
    int itemId = -1;

    private final String incorrectInputText = "Attack and Defence must be Integer type!";
    private final String notSelectedItemText = "Please select item first";
    private final String nameAlreadyUsedText = "Name already used";
    @FXML
    private TableView<Items> itemsTable;
    @FXML
    private TableColumn<Items, String> nameColumn, typeColumn, attackColumn, defenceColumn;

    @FXML
    private Label warningLabel, warningLabel1, warningLabel2;
    @FXML
    private TextField nameField, attackField, defenceField,
                        nameField1, attackField1, defenceField1,
                        filterNameField, filterTypeField, filterAttackField, filterDefenceField;
    @FXML
    private ChoiceBox<String> typeField, typeField1;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hideWarnings();

        initializeItemsTable();

        originalItems = base.getItems();
        fillItemsTable();

        setChoiceBoxes();
    }


    private void initializeItemsTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        attackColumn.setCellValueFactory(new PropertyValueFactory<>("attack"));
        defenceColumn.setCellValueFactory(new PropertyValueFactory<>("defence"));
    }

    private void hideWarnings() {
        warningLabel.setVisible(false);
        warningLabel1.setVisible(false);
        warningLabel2.setVisible(false);
    }

    private void setChoiceBoxes() {
        typeField.setItems(FXCollections.observableArrayList(typesOfItems));
        typeField1.setItems(FXCollections.observableArrayList(typesOfItems));
    }

    private ArrayList<Items> filterItems(ArrayList<Items> items) {
        ArrayList<Items> filteredItems = new ArrayList<>();
        String name = filterNameField.getText();
        String type = filterTypeField.getText();
        String attack = filterAttackField.getText();
        String defence = filterDefenceField.getText();
        for(Items item: items) {
            if (item.getName().contains(name))
                if (item.getType().contains(type))
                    if (Integer.toString(item.getAttack()).contains(attack))
                        if (Integer.toString(item.getDefence()).contains(defence)){
                            filteredItems.add(item);
                        }
        }
        return filteredItems;
    }

    @FXML
    public void fillItemsTable() {
        ArrayList<Items> items = filterItems(originalItems);
        itemsObservableList = FXCollections.observableArrayList(items);
        itemsTable.setItems(itemsObservableList);
    }

    private void fillModifyFields(Items item) {
        nameField1.setText(item.getName());
        typeField1.setValue(item.getType());
        attackField1.setText(Integer.toString(item.getAttack()));
        defenceField1.setText(Integer.toString(item.getDefence()));
    }

    @FXML
    public void itemSelected() {
        if (itemsTable.getSelectionModel().getSelectedItem() != null) {
            Items selectedItem = itemsTable.getSelectionModel().getSelectedItem();
            itemId = selectedItem.getIdItem();
            fillModifyFields(selectedItem);
        }
    }

    @FXML
    public void addItem() {
        String name = getNewName();
        if (name.equals("-1")) {
            warningLabel.setText(nameAlreadyUsedText);
            warningLabel.setVisible(true);
            return;
        }
        String type = getStringFromChoiceBox(typeField);
        int attack = getIntFromTextField(attackField);
        int defence = getIntFromTextField(defenceField);
        if (attack != -1 && defence != -1) {
            warningLabel.setText(incorrectInputText);
            warningLabel.setVisible(false);
            base.insertItems(name, type, attack, defence);
            clear();
        } else {
            warningLabel.setVisible(true);
        }

    }

    @FXML
    public void modifyItem() {
        if (itemId == -1) {
            warningLabel1.setText(notSelectedItemText);
            warningLabel1.setVisible(true);
            return;
        }

        String name = getModifyName();
        if ( name.equals("-1")) {
            warningLabel1.setText(nameAlreadyUsedText);
            warningLabel1.setVisible(true);
            return;
        }
        String type = getStringFromChoiceBox(typeField1);
        int attack = getIntFromTextField(attackField1);
        int defence = getIntFromTextField(defenceField1);
        if (attack != -1 && defence != -1) {

                warningLabel1.setVisible(false);
                base.updateItems(itemId, name, type, attack, defence);
                clear();
            } else {
                warningLabel1.setVisible(true);
                warningLabel1.setText(incorrectInputText);
            }

    }

    @FXML
    public void deleteItem() {
      if (itemId != -1) {
          warningLabel2.setVisible(false);
          base.deleteItems(itemId);
          clear();
      } else {
          warningLabel2.setVisible(true);
      }
    }

    private String getNewName() {
        String name = nameField.getText();
        for(Items item: originalItems) {
            if (item.getName().equals(name)){
                name = "-1";
                break;
            }
        }
        return name;
    }

    private String getModifyName() {
        String name = nameField.getText();
        for(Items item: originalItems) {
            if (item.getName().equals(name)){
                if (item.getIdItem() == itemId) { break; }
                name = "-1";
                break;
            }
        }
        return name;
    }
    private void clear()
    {
        nameField.clear();
        attackField.clear();
        defenceField.clear();
        nameField1.clear();
        attackField1.clear();
        defenceField1.clear();
        filterNameField.clear();
        filterTypeField.clear();
        filterAttackField.clear();
        filterDefenceField.clear();
        fillItemsTable();
    }

}
