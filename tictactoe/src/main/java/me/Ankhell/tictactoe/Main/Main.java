package tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static boolean isXTurn = true;

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
//            String inputString = reader.readLine();
//            String inputString = "XO_OO_XXX";
            String inputString = "_________";
            char[] cells = inputString.toCharArray();
            char[][] cells2D;
            printField(cells);
            while (!isGameOver(cells)) {
                cells = makeTurn(reader, cells);
            }

        } catch (IOException e) {
            System.out.println("Wrong input!");
            System.exit(1);
        }
    }

    static class Coordinates {
        static int x = -1;
        static int y;

        static void refresh() {
            x = -1;
            y = -1;
        }

        static boolean getCoordFromInputString(String input) {
            Coordinates coordinates = new Coordinates();
            String[] strings = input.split("\\s++");
            try {
                x = Integer.parseInt(strings[1]);
                y = Integer.parseInt(strings[0]);
                if (x < 1 || y < 1 || x > 3 || y > 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    x = -1;
                    return false;
                }
            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
                x = -1;
                return false;
            }
            return true;
        }

        static String getCoord() {
            return "(" + x + ", " + y + ")";
        }
    }

    public static char[] makeTurn(BufferedReader reader, char[] cells) throws IOException {
        while (Coordinates.x == -1) {
            System.out.print("Enter the coordinates: ");
            if (Coordinates.getCoordFromInputString(reader.readLine())) {
                if (convertFlatArrayTo2DAraay(cells)[3 - Coordinates.x][Coordinates.y - 1] != '_') {
                    System.out.println("This cell is occupied! Choose another one!");
                    Coordinates.x = -1;
                }
            }
        }
        char[][] cells2D = convertFlatArrayTo2DAraay(cells);
        cells2D[3 - Coordinates.x][Coordinates.y - 1] = isXTurn ? 'X' : 'O';
        isXTurn = !isXTurn;
        cells = convert2DArrayToFlatArray(cells2D);
        printField(cells);
        Coordinates.refresh();
        return cells;
    }

    public static boolean isGameOver(char[] cells) {
        if (isPossibleByDiff(cells)) {
            switch (checkWin(cells)) {
                case 'I':
                    System.out.println("Impossible");
                    break;
                case 'X':
                    System.out.println("X wins");
                    break;
                case 'O':
                    System.out.println("O wins");
                    break;
                case 'Z':
                    if (isDraw(cells)) {
                        System.out.println("Draw");
                    } else {
//                        System.out.println("Game not finished");
                        return false;
                    }
                    break;
            }
        } else {
            System.out.println("Impossible");
        }
        return true;
    }

    public static void printField(char[] field) {
        System.out.println("---------");
        System.out.print("| ");
        for (int i = 0; i < field.length; i++) {
            System.out.print(field[i]);
            System.out.print((i + 1) % 3 == 0 ? i == field.length - 1 ? " |\n" : " |\n| " : " ");
        }
        System.out.println("---------");
    }

    public static char checkWin(char[] cells) {
        char winner = 'Z';
        for (int i = 0; i < 3; i++) {
            if (cells[i*3] == cells[i*3 + 1] && cells[i*3] == cells[i*3 + 2]) {
                if (cells[i*3] != '_') {
                    if (winner == 'Z') {
                        winner = cells[i*3];
                    } else {
                        winner = 'I';
                    }
                }
            }
            if (cells[i] == cells[i + 3] && cells[i] == cells[i + 6]) {
                if (cells[i] != '_') {
                    if (winner == 'Z') {
                        winner = cells[i];
                    } else {
                        winner = 'I';
                    }
                }
            }
        }
        if (cells[0] == cells[4] && cells[0] == cells[8]) {
            if (cells[0] != '_') {
                if (winner == 'Z') {
                    winner = cells[0];
                } else {
                    winner = 'I';
                }
            }
        }
        if (cells[2] == cells[4] && cells[2] == cells[6]) {
            if (cells[2] != '_') {
                if (winner == 'Z') {
                    winner = cells[2];
                } else {
                    winner = 'I';
                }
            }
        }
        return winner;
    }

    public static boolean isDraw(char[] cells) {
        for (char cell : cells) {
            if (cell == '_') {
                return false;
            }
        }
        return true;
    }

    public static boolean isPossibleByDiff(char[] cells) {
        return getDiff(cells) <= 1;
    }

    public static Integer getDiff(char[] cells) {
        int sum = 0;
        for (char cell : cells) {
            switch (cell) {
                case 'X':
                    sum++;
                    break;
                case 'O':
                    sum--;
                    break;
            }
        }
        return Math.abs(sum);
    }

    public static char[][] convertFlatArrayTo2DAraay(char[] cells) {
        char[][] result = new char[cells.length / 3][cells.length / 3];
        for (int i = 0; i < cells.length / 3; i++) {
            result[i] = new char[]{cells[i * 3], cells[i * 3 + 1], cells[i * 3 + 2]};
        }
        return result;
    }

    public static char[] convert2DArrayToFlatArray(char[][] cells2D) {
        char[] result = new char[cells2D.length * cells2D[0].length];
        int i = 0;
        for (char[] charline : cells2D) {
            for (char chr : charline) {
                result[i] = chr;
                i++;
            }
        }
        return result;
    }
}
