package com.socket.models;

import com.socket.models.exceptions.NonEmptyCellException;
import com.socket.models.pojo.Control;
import com.socket.models.pojo.Move;
import lombok.Getter;

import java.util.Arrays;

/**
 * Данный класс является ядром программы tic-tac-toe (крестики-нолики).
 * Обращение к нему происходит исключительно через создание объекта класса, вызов функции analyze(move) и геттеры.
 * Также для дебага может использоваться функция printCurrentPosition().
 *
 * Функция analyze(move) предполагает, что ход будет указан с учетом утерянной единицы
 * (строки и столбики должны начинаться с 0).
 *
 * @author Margarita Malanukha
 * @author Ivan Berezovsky
 */

@Getter
public class TicTacToe {

    //доска для игры
    public char [][] currentPosition;
    //количество строк в игре
    private final int ROWS = 30;
    //количество колонок в игре
    private final int COLUMNS = 30;

    //true - ход первого игрока, false - второго
    private boolean sideToMove = true;

    //знаки, которые могут встречаться в игре
    private final char firstPlayerSign = 'X';
    private final char secondPlayerSign = 'O';
    private final char defaultSign = ' ';

    //условие для победы одного из игроков
    private final int signsInRow = 5;

    //сообщение для передачи во view
    private String message;

    public TicTacToe() {
        currentPosition = new char[ROWS][COLUMNS];
        init(currentPosition);
    }


    public Control analyze(Move move) {
        try {
            makeMove(move);
        } catch (NonEmptyCellException e) {
            return new Control(true, e.getMessage());
        }
        if (gameEnds(move)) {
            return new Control(false, message);
        }
        return new Control(true, message);
    }

    private boolean gameEnds(Move move) {
        //количество знаков одного игрока с учетом начальной клетки
        int step = signsInRow - 1;
        int [] checks = new int[8];
        //проверка главной диагонали выше точки
        checks[0] = checkLine(move, new Move(move.getX() - step, move.getY() - step));
        //проверка побочной диагонали выше точки
        checks[1] = checkLine(move, new Move(move.getX() - step, move.getY() + step));
        //проверка главной диагонали ниже точки
        checks[2] = checkLine(move, new Move(move.getX() + step, move.getY() + step));
        //проверка побочной диагонали ниже точки
        checks[3] = checkLine(move, new Move(move.getX() + step, move.getY() - step));

        //проверка верхней вертикали
        checks[4] = checkLine(move, new Move(move.getX() - step, move.getY()));
        //проверка правой горизонтали
        checks[5] = checkLine(move, new Move(move.getX(), move.getY() + step));
        //проверка нижней вертикали
        checks[6] = checkLine(move, new Move(move.getX() + step, move.getY()));
        //проверка левой горизонтали
        checks[7] = checkLine(move, new Move(move.getX(), move.getY() - step));

        //после хода игрока (который предполагаемо выиграл)
        //в функции makeMove() переменная sideToMove переводится на другого игрока,
        //поэтому нам нужно найти знак противоположного игрока
        char oppositePlayerSign = !sideToMove ? firstPlayerSign : secondPlayerSign;

        //проверка на победу, если ход заполнил точку, которая находилась в начале линии
        if (Arrays.stream(checks).anyMatch(e -> e == signsInRow)) {
            message = "Game is won by " + oppositePlayerSign + " player. Congratulations!";
            return true;
        }

        //проверка на победу, если ход заполнил точку, которая находилась в середине линии
        //* ниже происходит сложение верхних и нижних (или боковых) линий, которые находятся напротив
        //** отнятие единицы важно, потому что каждая функция checkLine учитывает начальный элемент
        if (checks[0] + checks[2] - 1 >= signsInRow ||
                checks[1] + checks[3] - 1 >= signsInRow ||
                checks[4] + checks[6] - 1 >= signsInRow ||
                checks[5] + checks[7] - 1 >= signsInRow) {
            message = "Game is won by " + oppositePlayerSign + " player. Congratulations!";
            return true;
        }

        //в случае, если предыдущие проверки не сработали, но ходов у игроков больше нет
        if (noFieldsLeft()) {
            message = "Game is drawn due to lack of fields.";
            return true;
        }

        char playerToMove = sideToMove ? firstPlayerSign : secondPlayerSign;
        message = playerToMove + " to move!";

        return false;
    }

    private void makeMove(Move move) throws NonEmptyCellException {
        int x = move.getX();
        int y = move.getY();

        //если поле занято другим знаком, то вынести на уровень выше исключение
        if (currentPosition[x][y] != defaultSign) {
            throw new NonEmptyCellException(move);
        }
        //иначе вынести знак ходящей сейчас стороны на поле
        char sign = sideToMove ? firstPlayerSign : secondPlayerSign;
        currentPosition[x][y] = sign;

        //и передаем очередь хода
        sideToMove = !sideToMove;
    }

    private int checkLine(Move initialCell, Move newGivenCell) {
        //знак, с которого начинаем отсчет.
        char sign = currentPosition[initialCell.getX()][initialCell.getY()];
        //проверка, не выходит ли новая точка за пределы массива и пересоздание точки.
        Move finalCell = correctCell(newGivenCell);

        int x = initialCell.getX();
        int y = initialCell.getY();
        int counter = 1; //счетчик включает в себя стартовый элемент
        while (true) {
            //к каждой из координат прибавляется\отнимается\не меняется в зависимости
            //от того, к какой координате "идет" координата
            x = newIndex(x, finalCell.getX());
            y = newIndex(y, finalCell.getY());

            //если точка - не нужный нам знак, то возвращаем кол-во найденных знаков до неё.
            if (currentPosition[x][y] != sign) {
                return counter;
            }

            counter++;

            //если мы дошли до финальной
            if (finalCell.equals(new Move(x, y))) {
                break;
            }
        }
        return counter;
    }

    private boolean noFieldsLeft() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (currentPosition[i][j] == defaultSign) return false;
            }
        }
        return true;
    }

    private int newIndex(int oldIndex, int direction) {
        if (direction > oldIndex) return ++oldIndex;
        if (direction < oldIndex) return --oldIndex;
        return oldIndex;
    }

    private Move correctCell(Move newGivenCell) {
        int x = Math.max(newGivenCell.getX(), 0);
        int y = Math.max(newGivenCell.getY(), 0);
        x = Math.min(x, getROWS() - 1);
        y = Math.min(y, getCOLUMNS() - 1);
        return new Move(x, y);
    }

    private void init(char [][] currentPosition) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                currentPosition[i][j] = defaultSign;
            }
        }
    }

    public void printCurrentPosition() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                System.out.print(currentPosition[i][j] + " ");
            }
            System.out.println();
        }
    }

}
