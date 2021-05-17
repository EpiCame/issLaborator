package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Show;
import model.Spectator;
import service.MasterService;

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
}
