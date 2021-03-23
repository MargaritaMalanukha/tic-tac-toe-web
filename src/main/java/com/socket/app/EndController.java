package com.socket.app;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class EndController {
    public Button toMenuButton;
    public TicTacToeApp ticTacToeApp = new TicTacToeApp();
    public String pathToMainFxml = "/fxml/entry.fxml";

    public void handleToMenuButton() {
        Stage stage = (Stage) toMenuButton.getScene().getWindow();
        stage.setScene(ticTacToeApp.createScene(pathToMainFxml));
    }
}
