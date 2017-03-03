package sample;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable, UICallback{

    @FXML
    ListView listViewQueues;
    @FXML
    ListView listViewTopics;
    @FXML
    Button buttonAddQueue;
    @FXML
    Button buttonAddTopic;
    @FXML
    Button buttonDelete;
    @FXML
    Button buttonRefresh;

    Manager manager;
    String selectedDestination;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        manager = new Manager(this);

        refreshUI();

        listViewQueues.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedDestination = newSelection.toString();
            }
        });

        listViewTopics.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedDestination = newSelection.toString();
            }
        });

        buttonAddQueue.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TextInputDialog dialog = new TextInputDialog("");
                dialog.setTitle("Nova fila");
                //dialog.setHeaderText("Nome do tópico");
                dialog.setContentText("Digite o nome da nova fila: ");

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()){
                    manager.createQueue(result.get());
                }

            }
        });

        buttonAddTopic.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TextInputDialog dialog = new TextInputDialog("");
                dialog.setTitle("Novo tópico");
                //dialog.setHeaderText("Nome do tópico");
                dialog.setContentText("Digite o nome do novo tópico: ");

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()){
                    manager.createTopic(result.get());
                }
            }
        });

        buttonDelete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (selectedDestination != null){
                    manager.deleteDest(selectedDestination);
                }
            }
        });

        buttonRefresh.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                refreshUI();
            }
        });

    }

    @Override
    public void refreshUI(){
        ObservableList<String> itemsQue = FXCollections.observableArrayList (
                manager.listQueues());
        listViewQueues.setItems(itemsQue);

        ObservableList<String> itemsTop = FXCollections.observableArrayList (
                manager.listTopics());
        listViewTopics.setItems(itemsTop);
    }
}
