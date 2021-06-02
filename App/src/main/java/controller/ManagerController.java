package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Manager;
import model.Show;
import model.Spectator;
import service.MasterService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ManagerController {
    private MasterService service = null;
    private Manager manager;
    private Stage managerStage;
    private ObservableList<Show> modelShows = FXCollections.observableArrayList();


    public void setService(Stage managerStage, MasterService service, Manager manager){
        this.manager = manager;
        this.managerStage = managerStage;
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
    TextField rowTextField;
    @FXML
    TextField nameTextField;
    @FXML
    TextField seatTextField;
    @FXML
    TextField priceTextField;

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

    public void handleAddButton(ActionEvent actionEvent) {
        try {
            LocalDate dateFormat;
            String name = nameTextField.getText();
            int rows = Integer.parseInt(rowTextField.getText());
            int seats = Integer.parseInt(seatTextField.getText());
            double price = Double.parseDouble(priceTextField.getText());
            dateFormat = showDatePicker.getValue();
            String date = dateFormat.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Show addedShow = this.service.addShow(name, rows, seats, price, date);
            if(addedShow == null)
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "The show was not added!!!");
            else {
                initModel(date);
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "The show was added!!!");
            }
        }
        catch(Exception ex){
            MessageAlert.showErrorMessage(null, "Invalid data!!!");
        }

    }

    public void handleDeleteButton(ActionEvent actionEvent) {
        Show selectedShow = showTableView.getSelectionModel().getSelectedItem();
        Show deleted = this.service.deleteShow(selectedShow);
        if(deleted == null)
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "The show was not deleted!!!");
        else {
            LocalDate dateFormat;
            dateFormat = showDatePicker.getValue();
            if(dateFormat == null)
                initModel("");
            else {
                String date = dateFormat.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                initModel(date);
            }
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "The show was deleted!!!");
        }
    }
}
