package com.socket.app;

import com.socket.app.networkClient.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Ivan Berezovsky
 * @author Margarita Malanukha
 */

//todo 4. Приведение в нормальный вид game.fxml и entry.fxml.
//todo 5. Прикручивание сокетов.
//todo 6. Тестирование.
//todo 7. Снять видеоролик.

public class TicTacToeApp extends Application {
    public static Client client;
    public Stage window;
    public Scene entryScene;
    public Scene gameScene;
    public Scene endScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;

        entryScene = createScene("/fxml/entry.fxml");
        gameScene = createScene("/fxml/game.fxml");
        endScene = createScene("/fxml/end.fxml");

        window.setScene(entryScene);
        window.show();
    }

    public Scene createScene(String pathToFxml) {
        Parent gameRoot = null;
        try {
            gameRoot = FXMLLoader.load(getClass().getResource(pathToFxml));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert gameRoot != null;
        return new Scene(gameRoot, 1800, 1080);
    }

    public Stage createStage(String pathToFxml){
        Scene game = createScene(pathToFxml);
        Stage stage = new Stage();
        stage.setScene(game);
        stage.setFullScreen(true);
        return stage;
    }

    public static void main(String[] args) {
        client = new Client();
        client.start();
        launch(args);
    }
}
