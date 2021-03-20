package com.socket.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Ivan Berezovsky
 * @author Margarita Malanukha
 */

//todo 1. Написать GameController и game.fxml
//todo 2. Написать Tile для обработки каждого хода
//todo 3. Тестирование
//todo 4. Приведение в нормальный вид game.fxml и entry.fxml.
//todo 5. Прикручивание сокетов.
//todo 6. Тестирование.
//todo 7. Снять видеоролик.

public class TicTacToeApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent gameEntryRoot = FXMLLoader.load(getClass().getResource("/fxml/entry.fxml"));
        Scene gameEntry = new Scene(gameEntryRoot, 600, 400);
        //stage.setScene(gameEntry);
        Parent gameRoot = FXMLLoader.load(getClass().getResource("/fxml/game.fxml"));
        Scene game = new Scene(gameRoot, 1800, 1080);
        stage.setScene(game);
        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
