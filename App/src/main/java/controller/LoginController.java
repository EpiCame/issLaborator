package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Manager;
import model.Spectator;
import model.User;
import service.MasterService;

import java.io.IOException;

public class LoginController {
    private MasterService service = null;
    private Stage loginStage;
    private String theater;

    @FXML
    Button loginButton;
    @FXML
    Button signupButton;
    @FXML
    TextField usernameTextField;
    @FXML
    TextField passwordTextField;

    public void setService(Stage loginStage, MasterService service){
        this.service = service;
        this.loginStage = loginStage;
        this.theater = service.getTheater();
    }


    public void handleLogin(ActionEvent actionEvent) {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        usernameTextField.setText("");
        passwordTextField.setText("");
        if(username.equals("") || password.equals(""))
            MessageAlert.showMessage(null, Alert.AlertType.WARNING, "Warning", "Invalid username or password!!!");
        else {
            User user = this.service.findUser(username);
            if(user == null || !user.getPassword().equals(password))
                MessageAlert.showErrorMessage(null, "Login Failed!!!");
            else{
                loginStage.hide();
                if(user instanceof Spectator)
                    showClientWindow(user, loginStage);
                else
                    showManagerWindow(user, loginStage);
            }
        }

    }

    private void showManagerWindow(User manager, Stage loginStage){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/managerView.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage managerStage = new Stage();
            managerStage.setTitle("Manager window of " + manager.getUsername());
            managerStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            managerStage.setScene(scene);
            ManagerController managerController = loader.getController();
            managerController.setService(managerStage, service, (Manager) manager);
            managerStage.show();
            managerStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    try {
                        loginStage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showClientWindow(User spectator, Stage loginStage){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/clientView.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage clientStage = new Stage();
            clientStage.setTitle("Spectator window of " + spectator.getUsername());
            clientStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            clientStage.setScene(scene);
            ClientController clientController = loader.getController();
            clientController.setService(clientStage, service, (Spectator) spectator);
            clientStage.show();
            clientStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    try {
                        loginStage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleSignup(ActionEvent actionEvent) {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        usernameTextField.setText("");
        passwordTextField.setText("");
        if(username.equals("") || password.equals(""))
            MessageAlert.showMessage(null, Alert.AlertType.WARNING, "Warning", "Invalid username or password!!!");
        else{
            User user = this.service.findUser(username);
            if(user != null)
                MessageAlert.showMessage(null, Alert.AlertType.WARNING, "Warning", "This username is already registered!!!");
            else{
                Spectator spectator = new Spectator(password, this.theater);
                spectator.setUsername(username);
                User added = this.service.addUser(spectator);
                if(added == null){
                    MessageAlert.showErrorMessage(null, "User not added!!!");
                }
                else
                    MessageAlert.showMessage(null , Alert.AlertType.CONFIRMATION, "Succes", "User signed up!!!");
            }
        }
    }
}
