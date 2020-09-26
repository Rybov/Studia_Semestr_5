package org.openjfx;

import com.pattern.database.tables.*;
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
import java.nio.charset.IllegalCharsetNameException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static org.openjfx.App.base;

public class UsersController extends SecondaryController implements Initializable {

    int userid1=-1;
    private ArrayList<Users> users = base.getUsers();
    private ObservableList<Users> usersObservableList = FXCollections.observableArrayList(users);
    @FXML
    private TableView<Users> usertable;
    @FXML
    private TableColumn<Users,Integer> userid;
    @FXML
    private TableColumn<Users,String> useremail, userlog,userpass;
    @FXML
    private TextField em,l,p,em1,l1,p1,fid,fem,fl,fp;
    @FXML
    private Label warningLabel1;
    @FXML
    private Label warningLabel2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeUsersTable();
        hideWarnings();
        usertable.setItems(usersObservableList);

    }

    private void initializeUsersTable() {
        userid.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        useremail.setCellValueFactory(new PropertyValueFactory<>("email"));
        userlog.setCellValueFactory(new PropertyValueFactory<>("login"));
        userpass.setCellValueFactory(new PropertyValueFactory<>("pass"));
    }

    private void hideWarnings() {
        warningLabel1.setVisible(false);
        warningLabel2.setVisible(false);
    }

    private void fillModifyFields(Users user) {
        userid1 = user.getIdUser();
        em1.setText(user.getEmail());
        l1.setText(user.getLogin());
        p1.setText(user.getPass());
    }

    @FXML
    public void selectUser() {
        if (usertable.getSelectionModel().getSelectedItem() != null) {
            Users selectedUser = usertable.getSelectionModel().getSelectedItem();
            fillModifyFields(selectedUser);
        }
    }

    @FXML
    public void addUser() {
        String email = em.getText();
        String login = l.getText();
        String pass = p.getText();
        if(email.isEmpty() || login.isEmpty() || pass.isEmpty())
        {
            warningLabel2.setVisible(true);
            warningLabel2.setText("Field is empty");
        }
        else
        {
            if(check(email,login)==-1)
            {
                warningLabel2.setVisible(true);
                warningLabel2.setText("Login or Email is already taken");
            }
            else
            {
                warningLabel2.setVisible(false);
                warningLabel2.setText("Error");
                base.insertUsers(email,login,pass);
                fillUserTable();
                clear();
            }
        }
    }

    private int check(String e,String l) {
        boolean exist = false;
        for (Users user : users)
        {
            if(user.getLogin().equals(l) || user.getEmail().equals(e))
            {
                exist=true;
                break;
            }
        }
        if(exist)
            return -1;
        else
            return 1;
    }

    private int check2(String e, String l, Integer id) {
        boolean exist= false;
        for (Users x : users)
        {
            if((x.getLogin().equals(l) || x.getEmail().equals(e)) && x.getIdUser()!=id)
            {
                exist=true;
                break;
            }
        }
        if(exist)
            return -1;
        else
            return 1;
    }

    @FXML
    public void modifyUser() {
        if (userid1 != -1) {
            String email = em1.getText();
            String login = l1.getText();
            String pass = p1.getText();
            if(email.isEmpty() || login.isEmpty() || pass.isEmpty())
            {
                warningLabel1.setVisible(true);
                warningLabel1.setText("Field is empty");
            }
            else
            {
                if(check2(email,login,userid1)==-1)
                {
                    warningLabel1.setVisible(true);
                    warningLabel1.setText("Login or Email is already taken");
                }
                else
                {
                    warningLabel1.setVisible(false);
                    warningLabel1.setText("Error");
                    base.updateUsers(userid1,email,login,pass);
                    fillUserTable();
                    clear();
                }
            }
        } else {
            warningLabel1.setVisible(true);
            warningLabel1.setText("User is not selected");

        }
    }

    @FXML
    public void deleteUser() {
        if (userid1 != -1) {
            warningLabel1.setVisible(false);
            warningLabel1.setText("Error");
            base.deleteUsers(userid1);
            fillUserTable();
            clear();
        } else {
            warningLabel1.setVisible(true);
            warningLabel1.setText("Cannot delete");

        }
    }
    @FXML
    private void fillUserTable() {
        users = base.getUsers();
        String id = fid.getText();
        String email = fem.getText();
        String login = fl.getText();
        String pass = fp.getText();
        ArrayList<Users> users2=new ArrayList<Users>();
        for (Users x : users)
        {
            if(Integer.toString(x.getIdUser()).contains(id) || id.isEmpty())
                if(x.getEmail().contains(email) || email.isEmpty())
                    if(x.getLogin().contains(login) || login.isEmpty())
                        if(x.getPass().contains(pass) || pass.isEmpty())
                            users2.add(x);
        }
        usersObservableList = FXCollections.observableArrayList(users2);
        usertable.setItems(usersObservableList);
        usertable.getSelectionModel().clearSelection();
        userid1=-1;
    }

    @FXML
    private void clear(){
        fid.clear();
        fem.clear();
        fl.clear();
        fp.clear();
        em1.clear();
        l1.clear();
        p1.clear();
        em.clear();
        l.clear();
        p.clear();
        fillUserTable();
    }
}
