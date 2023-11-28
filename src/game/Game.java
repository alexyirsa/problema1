package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Game {

    private static final String EMPTY = "⬜";
    private static final String OBSTACLE = "⬛";
    private static final String CAR = "🚙";
    private static final String PLAYER_CAR = "🚗";

    private String[][] board;
    private int player;

    public Game() {
        initializeBoard();
        player = 0;
    }

    private void initializeBoard() {
        board = new String[][]{
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, OBSTACLE, OBSTACLE, OBSTACLE, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, OBSTACLE, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, OBSTACLE, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, OBSTACLE, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, OBSTACLE, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, OBSTACLE, EMPTY},
                {EMPTY, EMPTY, EMPTY, OBSTACLE, OBSTACLE, OBSTACLE, EMPTY, EMPTY}
        };
    }

    public void play() {
        boolean playing = true;
        while (playing) {
            printBoard();
            try {
                moveCars();
                handlePlayerInput();
                playing = checkCollision();
                Thread.sleep(1000);
            } catch (InterruptedException | IOException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Perdiste!");
    }

    private void printBoard() {
        for (String[] row : board) {
            for (String elem : row)
                System.out.print(" " + elem + " ");
            System.out.println("");
        }
    }

    private void moveCars() {
        for (int i = board.length - 1; i > 0; i--) {
            board[i] = board[i - 1].clone();
        }
        for (int i = 0; i < board[0].length; i++) {
            board[0][i] = EMPTY;
        }
        generateRandomCar();
    }

    private void generateRandomCar() {
        Random random = new Random();
        int newCar = random.nextInt(8);
        board[0][newCar] = CAR;
    }

    private void handlePlayerInput() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String key = br.readLine();

        if ("q".equals(key)) {
            System.exit(0);
        } else if ("a".equals(key) && player > 0) {
            movePlayer(-1);
        } else if ("d".equals(key) && player < 7) {
            movePlayer(1);
        }
        board[7][player] = PLAYER_CAR;
    }

    private void movePlayer(int direction) {
        board[7][player] = EMPTY;
        player += direction;
    }

    private boolean checkCollision() {
        return board[7][player].equals(CAR) || board[6][player].equals(CAR);
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
