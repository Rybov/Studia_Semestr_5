package login;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class ErrorDisplay {
    private Label label;

    public void setString(String string) {
        this.string = string;
    }

    private String string;

    public ErrorDisplay(Label label) {
        this.label = label;
    }

    public void displayErrorMessage(){
        label.setText(string);
        FadeTransition f = new FadeTransition(new Duration(5000),label);
        f.setFromValue(1);
        f.setToValue(0);
        f.playFromStart();
    }
}
