package com.socket.app;

import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GameEntryController {
    public TicTacToeApp ticTacToeApp = new TicTacToeApp();
    public String pathToGameFxml = "/fxml/game.fxml";
    public Button playButton;
    public Button exitButton;

    public void handlePlayButton() {
        Stage stage = (Stage) playButton.getScene().getWindow();
        stage.setScene(ticTacToeApp.createScene(pathToGameFxml));
    }

    public void exitButtonOnClick() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
