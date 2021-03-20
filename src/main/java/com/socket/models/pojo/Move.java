package com.socket.models.pojo;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Move {

    private final int x;
    private final int y;

    public static String serializeToString(Move move) {
        return move.x + " " + move.y;
    }

    public static Move deserializeFromString(String str) {
        String [] strings = str.split(" ");

        int x = Integer.parseInt(strings[0]);
        int y = Integer.parseInt(strings[1]);

        return new Move(x, y);
    }

}
