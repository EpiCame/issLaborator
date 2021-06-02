package controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Reservation;
import model.Show;
import model.Spectator;
import service.MasterService;

import java.util.ArrayList;
import java.util.List;

public class ConfigController {

    private Stage configStage;
    private Show show;
    private Spectator spectator;
    private MasterService service = null;
    private ObservableList<Reservation> modelReservation = FXCollections.observableArrayList();
    private ObservableList<Integer> modelRow = FXCollections.observableArrayList();
    private ObservableList<Integer> modelSeat = FXCollections.observableArrayList();

    @FXML
    TableView<Reservation> seatsTableView;
    @FXML
    TableColumn<Reservation, Integer> rowColumn;
    @FXML
    TableColumn<Reservation, Integer> seatColumn;
    @FXML
    Button viewButton;
    @FXML
    Button makeButton;
    @FXML
    ComboBox<Integer> rowCombo;
    @FXML
    ComboBox<Integer> seatCombo;

    @FXML
    public void initialize(){
        rowColumn.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("row"));
        seatColumn.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("seat"));
        seatsTableView.setItems(modelReservation);
        rowCombo.setItems(modelRow);
        seatCombo.setItems(modelSeat);
    }

    public void handleViewButton(ActionEvent actionEvent) {
        Reservation reservation = seatsTableView.getSelectionModel().getSelectedItem();
        if(reservation != null){
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Reservation info", "Reservation made by: " + reservation.getSpectator().getUsername());
        }
        else {
            MessageAlert.showMessage(null, Alert.AlertType.WARNING, "Warning", "You have to select a reservation!!!");
        }
    }

    public void handleMakeButton(ActionEvent actionEvent) {
        int row = rowCombo.getValue();
        int seat = seatCombo.getValue();
        Reservation res = this.service.findReservationByRowSeat(row, seat, show);
        if( res == null){
            Reservation reservation = new Reservation(spectator, show, row, seat);
            Reservation added = this.service.addReservation(reservation);
            if(added != null){
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Added", "Reservation added!!!");
                initmodel();
            }
            else{
                MessageAlert.showErrorMessage(null, "Reservation not added!!!");
            }
        }
        else{
            MessageAlert.showErrorMessage(null, "This seat is already reserved!!!");
        }
    }

    public void setService(Stage configStage, MasterService service, Spectator spectator, Show show) {
        this.configStage = configStage;
        this.service = service;
        this.spectator = spectator;
        this.show = show;
        for(int i = 1 ; i <= show.getNrRows(); ++i){
            modelRow.add(i);
        }
        for(int i = 1 ; i <= show.getNrSeatsPerRow(); ++i){
            modelSeat.add(i);
        }
        initmodel();
    }

    private void initmodel(){
        List<Reservation> list  = this.service.findReservationByShow(this.show);
        if(list != null)
            modelReservation.setAll(list);
    }
}
