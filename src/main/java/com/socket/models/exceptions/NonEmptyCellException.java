package com.socket.models.exceptions;

import com.socket.models.pojo.Move;

public class NonEmptyCellException extends Exception {

    public NonEmptyCellException(Move move) {
        super("Move (" + move.getX() + ", " + move.getY() + ") is impossible "
                + " because this field is already taken!");
    }

}
