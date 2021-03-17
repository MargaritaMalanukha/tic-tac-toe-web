package com.socket.models.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Control {

    private final boolean isGameContinue;
    private final String message;

}
