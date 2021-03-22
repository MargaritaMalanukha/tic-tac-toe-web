package com.socket.app;

import com.socket.models.TicTacToe;
import com.socket.models.pojo.Move;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class GameController {

    public Pane root;
    public TicTacToe ticTacToe = new TicTacToe();
    public static Tile [][] tiles = new Tile[30][30];

    public void initialize() {
        for (int i = 0; i < ticTacToe.getROWS(); i++) {
            for (int j = 0; j < ticTacToe.getCOLUMNS(); j++) {
                tiles[i][j] = new Tile(ticTacToe, new Move(i, j), root);
                tiles[i][j].setTranslateX(j * 30);
                tiles[i][j].setTranslateY(i * 30);

                root.getChildren().add(tiles[i][j]);
            }
        }
    }


}
