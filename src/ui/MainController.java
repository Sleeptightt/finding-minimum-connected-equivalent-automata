package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainController {

    @FXML
    private TextField initialState;

    @FXML
    private TextField states;

    @FXML
    private TextField inputAlphabet;

    @FXML
    private TextField outputAlphabet;

    @FXML
    private ChoiceBox<String> typeAutomata;

    @FXML
    private Button btnConfirm;

    @FXML
    private TableView<?> tableAutomata;

    @FXML
    private void initialize() {
    	
    }
    @FXML
    void fillTable(ActionEvent event) {

    }

}
