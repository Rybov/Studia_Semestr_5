package org.openjfx;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PrimaryController {

    @FXML
    private Button exitButton;
    @FXML
    private void switchToCharacters() throws IOException {
        App.setRoot("characters");
    }
    @FXML
    private void switchToEquipments() throws IOException {
        App.setRoot("equipments");
    }
    @FXML
    private void switchToItems() throws IOException {
        App.setRoot("items");
    }
    @FXML
    private void switchToUsers() throws IOException {
        App.setRoot("users");
    }
    @FXML
    private void switchToMaps() throws IOException {
        App.setRoot("maps");
    }
    @FXML
    private void switchToNPCs() throws IOException {
        App.setRoot("NPCs");
    }
    @FXML
    private void switchToQuests() throws IOException {
        App.setRoot("quests");
    }
    @FXML
    private void switchToCharQuests() throws IOException {
        App.setRoot("charactersQuests");
    }
    @FXML
    private void switchToServers() throws IOException {
        App.setRoot("servers");
    }
    @FXML
    private void switchToPets() throws IOException {
        App.setRoot("pets");
    }
    @FXML
    private void switchToCharPets() throws IOException {
        App.setRoot("charPets");
    }


    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }
}
