package com.socket.app;

import com.socket.models.TicTacToe;
import com.socket.models.pojo.Move;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class GameController {

    public Pane root;
    public TicTacToe ticTacToe = new TicTacToe();

    public void initialize() {
        for (int i = 0; i < ticTacToe.getROWS(); i++) {
            for (int j = 0; j < ticTacToe.getCOLUMNS(); j++) {
                Tile tile = new Tile(ticTacToe, new Move(i, j), root);
                tile.setTranslateX(j * 30);
                tile.setTranslateY(i * 30);

                root.getChildren().add(tile);
            }
        }
    }


}
