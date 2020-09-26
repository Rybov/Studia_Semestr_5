package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SecondaryController {
    @FXML
    private void back() throws IOException {
        App.setRoot("primary");
    }

    protected String getStringFromChoiceBox(ChoiceBox<String> choiceBox) {
        String result = "-1";
        result = choiceBox.getValue();
        if (result == null) {
            result = "-1";
        }

        return result;
    }

    protected int getIntFromChoiceBox(ChoiceBox<String> choiceBox) {
        int result;
        try {
            result = Integer.parseInt(getStringFromChoiceBox(choiceBox));
        } catch (Exception e) {
            result = -1;
        }
        return result;
    }

    protected int getIntFromTextField(TextField textField) {
        int result = 0;
        try {
            result = Integer.parseInt(textField.getText());
        } catch (Exception e) {
            result = -1;
        }
        if (result < 1) {
            result = -1;
        }
        return result;
    }

}
