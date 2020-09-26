package org.openjfx;

import com.pattern.database.Database;
import com.pattern.database.tables.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static Database base;

    public final static String[] typesOfItems = {"Armor", "Sword", "Helmet", "Gloves", "Pants"};
    public final static String[] classes = {"Warrior", "Demon", "Ninja", "Wizard"};
    public final static String[] genders = {"Female", "Male"};
    public final static String[] wearedLevels = {"10%", "20%","30%","40%","50%","60%","70%","80%","90%","100%"};

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        base = new Database();
        launch();
        base.disconnect();

    }

}