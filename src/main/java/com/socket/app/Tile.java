package com.socket.app;

import com.socket.models.TicTacToe;
import com.socket.models.exceptions.NonEmptyCellException;
import com.socket.models.pojo.Control;
import com.socket.models.pojo.Move;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Getter;

@Getter
public class Tile extends StackPane {
    private Text text = new Text();
    private final TicTacToe ticTacToe;
    private final Move move;
    private final String pathToEndFxml = "/fxml/end.fxml";
    private Stage stage;

    public Tile(TicTacToe ticTacToe, Move move, Pane root) {
        this.ticTacToe = ticTacToe;
        this.move = move;

        Rectangle border = new Rectangle(30, 30);
        border.setFill(null);
        border.setStroke(Color.BLACK);

        text.setFont(Font.font(18));

        setAlignment(Pos.CENTER);
        getChildren().addAll(border, text);

        setOnMouseClicked(event -> {
            TicTacToeApp.client.MakeMove();
            if (text.getText().equals("")) {
                text.setText(ticTacToe.getCurrentPlayerSign());
                Control control = ticTacToe.analyze(move);
                if (!control.isGameContinue()) {
                    stage = (Stage) root.getScene().getWindow();
                    TicTacToeApp ticTacToeApp = new TicTacToeApp();
                    stage.setScene(ticTacToeApp.createScene(pathToEndFxml));
                }
            }
        });
    }
}
