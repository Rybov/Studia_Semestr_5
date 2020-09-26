package org.openjfx;

import com.pattern.database.tables.Servers;
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
public class ServersController extends SecondaryController implements Initializable {
    int serverid1 = -1;
    private ArrayList<Servers> servers = base.getServers();
    private ObservableList<Servers> serversObservableList = FXCollections.observableArrayList(servers);
    @FXML
    private TableView<Servers> servertable;
    @FXML
    private TableColumn<Servers, Integer> serverid;
    @FXML
    private TableColumn<Servers, String> servername;
    @FXML
    private TableColumn<Servers, Integer> serversize;
    @FXML
    private TextField n, a, n1, a1, fid, fn, fa;
    @FXML
    private Label wl1;
    @FXML
    private Label wl2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        servername.setCellValueFactory(new PropertyValueFactory<>("name"));
        serverid.setCellValueFactory(new PropertyValueFactory<>("idServer"));
        serversize.setCellValueFactory(new PropertyValueFactory<>("amountOfCharacter"));
        wl1.setVisible(false);
        wl2.setVisible(false);
        servertable.setItems((serversObservableList));
    }

    @FXML
    private void selectServer() {
        if (servertable.getSelectionModel().getSelectedItem() != null) {
            serverid1 = servertable.getSelectionModel().getSelectedItem().getIdServer();
            n1.setText(servertable.getSelectionModel().getSelectedItem().getName());
            a1.setText(Integer.toString(servertable.getSelectionModel().getSelectedItem().getAmountOfCharacter()));
        }
    }

    @FXML
    private void addServer() {
        String name = n.getText();
        String amount = a.getText();
        if (name.isEmpty() || amount.isEmpty()) {
            wl2.setVisible(true);
            wl2.setText("Field is empty");
        } else {
            if (check(name) == -1) {
                wl2.setVisible(true);
                wl2.setText("Name is already taken");
            } else {
                wl2.setVisible(false);
                wl2.setText("Error");
                base.insertServers(name, Integer.parseInt(amount));
                fillServerTable();
                clear();
            }
        }
    }

    private int check(String name) {
        ArrayList<Servers> ser2 = base.getServers();
        for (Servers s : ser2) {
            if (s.getName().equals(name))
                return -1;
        }
        return 1;
    }

    private int check2(String name, Integer id) {
        ArrayList<Servers> ser2 = base.getServers();
        for (Servers s : ser2) {
            if (s.getName().equals(name) && s.getIdServer() != id)
                return -1;
        }
        return 1;
    }

    @FXML
    private void modifyServer() {
        if (serverid1 != -1) {
            String name = n1.getText();
            String amount = a1.getText();
            if (name.isEmpty() || amount.isEmpty()) {
                wl1.setVisible(true);
                wl1.setText("Field is empty");
            } else {
                if (check2(name, serverid1) == -1) {
                    wl2.setVisible(true);
                    wl2.setText("Name is already taken");
                } else {
                    wl2.setVisible(false);
                    wl2.setText("Error");
                    base.updateServers(serverid1, name, Integer.parseInt(amount));
                    fillServerTable();
                    clear();
                }
            }
        } else {
            wl1.setVisible(true);
            wl1.setText("Server is not selected");
        }


    }

    @FXML
    public void deleteServer() {
        if (serverid1 != -1) {
            wl1.setVisible(false);
            wl1.setText("Error");
            base.deleteServers(serverid1);
            fillServerTable();
            clear();
        } else {
            wl1.setVisible(true);
            wl1.setText("Cannot delete");

        }
    }

    @FXML
    private void fillServerTable() {
        base.loadServers();
        servers = base.getServers();
        String id = fid.getText();
        String name = fn.getText();
        String amount = fa.getText();
        ArrayList<Servers> servers2 = new ArrayList<Servers>();
        for (Servers x : servers) {
            if (Integer.toString(x.getIdServer()).contains(id) || id.isEmpty())
                if (x.getName().contains(name) || name.isEmpty())
                    if (Integer.toString(x.getAmountOfCharacter()).contains(amount) || amount.isEmpty())
                        servers2.add(x);
        }
        serversObservableList = FXCollections.observableArrayList(servers2);
        servertable.setItems(serversObservableList);
        servertable.getSelectionModel().clearSelection();
        serverid1 = -1;
    }

    @FXML
    private void clear(){
        fid.clear();
        fn.clear();
        fa.clear();
        n.clear();
        a.clear();
        n1.clear();
        a1.clear();
        fillServerTable();
    }


}
