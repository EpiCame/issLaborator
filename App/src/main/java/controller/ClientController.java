package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Show;
import model.Spectator;
import model.User;
import service.MasterService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ClientController {
    private MasterService service = null;
    private Spectator spectator;
    private Stage clientStage;
    private ObservableList<Show> modelShows = FXCollections.observableArrayList();


    public void setService(Stage clientStage, MasterService service, Spectator spectator){
        this.spectator = spectator;
        this.clientStage = clientStage;
        this.service = service;
        initModel("");
    }

    @FXML
    TableView<Show> showTableView;
    @FXML
    TableColumn<Show, String> nameTableColumn;
    @FXML
    TableColumn<Show, Integer> seatsTableColumn;
    @FXML
    TableColumn<Show, Double> priceTableColumn;
    @FXML
    DatePicker showDatePicker;
    @FXML
    Button configurationButton;

    @FXML
    public void initialize(){
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<Show,String>("name"));
        seatsTableColumn.setCellValueFactory(new PropertyValueFactory<Show,Integer>("nrSeats"));
        priceTableColumn.setCellValueFactory(new PropertyValueFactory<Show,Double>("price"));
        showTableView.setItems(modelShows);
    }

    private void initModel(String date){
        if(date.equals(""))
            modelShows.setAll(this.service.getAllShows());
        else
            modelShows.setAll(this.service.getShowsByDate(date));
    }


    public void handleDatePick(ActionEvent actionEvent) {
        LocalDate dateFormat;
        if(showDatePicker.getValue() != null) {
            dateFormat = showDatePicker.getValue();
            String date = dateFormat.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            initModel(date);
        }
        else
            initModel("");
    }

    public void handleConfigButton(ActionEvent actionEvent) {
        //luam show-ul selectat
        Show show = showTableView.getSelectionModel().getSelectedItem();
        if(show != null){
            showConfigWindow(spectator, show);
        }
        else{
            MessageAlert.showMessage(null, Alert.AlertType.WARNING, "Warning", "You have to select a show!!!");
        }
    }

    private void showConfigWindow(User spectator, Show show){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/configView.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage configStage = new Stage();
            configStage.setTitle("Configuration window for " + show.getName());
            configStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            configStage.setScene(scene);
            ConfigController configController = loader.getController();
            configController.setService(configStage, service, (Spectator) spectator, show);
            configStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
